package com.mohanvamsi06.FileEncryptor.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Decryptor{
    public int DecryptFile(String filename, String pass, boolean keepOriginal){
        int keySize;
        EncryptionService decryptor;
        byte algo;
        File file = new File(filename);
        byte[] fileBytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            fileBytes = new byte[(int) file.length() - 1]; 
            byte[] algoByte = new byte[1];
            fis.read(fileBytes); 
            fis.read(algoByte);
            algo = algoByte[0];
        } catch (IOException e) {
            return 102;
        }
        
        switch (algo) {
            case 1 -> {
                keySize = 8;
                decryptor = new DESUtil();
            }
            case 2 -> {
                keySize = 16;
                decryptor = new AESUtil();
            }
            case 3 -> {
                keySize = 16;
                decryptor = new BlowfishUtil();
            }
            default -> {
                return 101;
            }
        }
        
        KeyGen generator = new KeyGen();
        byte[] key = generator.KeyGen(keySize, pass);
        byte[] decryptedData;
        try {
            decryptedData = decryptor.decrypt(fileBytes, key);
        } catch (Exception ex) {
            return 103;
        }

        int ind = 0;
        int fileLength = decryptedData.length;
        for (int i = fileLength-1; i>0; i--){
            if (decryptedData[i] == '@' && decryptedData[i-1] == '@'){
                ind = i;
                break;
            }
        }
        int extensionLength = fileLength - ind - 1;
        byte[] extension = new byte[extensionLength];
        for(int i = 0; i< extensionLength; i++){
            extension[i] = decryptedData[ind+1+i];
        }
        String extensionString = new String(extension);
 
        String decryptedFilename = filename.substring(0, filename.length() - 4) + extensionString;
        File decryptedfile = new File(decryptedFilename);
        try (FileOutputStream fos = new FileOutputStream(decryptedfile)) {
            fos.write(decryptedData, 0, ind-1); 
        } catch (IOException e) {
            return 104;
        }
        if(keepOriginal==false){
            Path path = Paths.get(filename);
            try {
                Files.delete(path);
            } catch (IOException ex) {
                return 105;
            }
        }
        return 0;
    }
}