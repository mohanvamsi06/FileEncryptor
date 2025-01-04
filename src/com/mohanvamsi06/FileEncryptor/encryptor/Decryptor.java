package com.mohanvamsi06.FileEncryptor.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Decryptor{
    public int DecryptFile(String filename, String pass, String algo, boolean keepOriginal){
        int keySize;
        EncryptionService decryptor;
        switch (algo) {
            case "des" -> {
                keySize = 8;
                decryptor = new DESUtil();
            }
            case "aes" -> {
                keySize = 16;
                decryptor = new AESUtil();
            }
            case "blowfish" -> {
                keySize = 16;
                decryptor = new BlowfishUtil();
            }
            default -> {
                return 101;
            }
        }
        
        KeyGen generator = new KeyGen();
        byte[] key = generator.KeyGen(keySize, pass);

        File file = new File(filename);
        byte[] fileBytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            fileBytes = new byte[(int) file.length()]; 
            fis.read(fileBytes); 
        } catch (IOException e) {
            return 102;
        }

        byte[] decryptedData;
        try {
            decryptedData = decryptor.decrypt(fileBytes, key);
        } catch (Exception ex) {
            return 103;
        }

        String decryptedFilename = filename.substring(0, filename.length() - 4);
        File decryptedfile = new File(decryptedFilename);
        try (FileOutputStream fos = new FileOutputStream(decryptedfile)) {
            fos.write(decryptedData); 
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