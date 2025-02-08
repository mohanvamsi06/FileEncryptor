package com.mohanvamsi06.FileEncryptor.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Encryptor{
    public int EncryptFile(String filename, String pass, String algo, boolean keepOriginal){
        int indexDot = filename.lastIndexOf(".");
        String extension;
        if (indexDot > 0 && indexDot < filename.length() - 1) {
            extension = filename.substring(indexDot);
        } else {
            extension = ""; 
        }
        int lengthOfExtension = extension.length();
        int keySize;
        EncryptionService encryptor;
        byte algoType;
        switch (algo) {
            case "des" -> {
                keySize = 8;
                encryptor = new DESUtil();
                algoType = 1;
            }
            case "aes" -> {
                keySize = 16;
                encryptor = new AESUtil();
                algoType = 2;
            }
            case "blowfish" -> {
                keySize = 16;
                encryptor = new BlowfishUtil();
                algoType = 3;
            }
            default -> {
                return 101;
            }
        }
        
        KeyGen generator = new KeyGen();
        byte[] key = generator.KeyGen(keySize, pass);

        File file = new File(filename);
        byte[] fileBytes;
        long fileLength = file.length();
        if (fileLength > (Integer.MAX_VALUE - (lengthOfExtension + 2))){
            return 106; // File Size Exceeds Limit
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            fileBytes = new byte[((int) fileLength) + 2 + lengthOfExtension]; 
            fis.read(fileBytes);
            fileBytes[(int) fileLength] = '@';
            fileBytes[(int) fileLength+1] = '@';
            byte[] extensionName = extension.getBytes();
            System.arraycopy(extensionName, 0, fileBytes, (int) fileLength+2, lengthOfExtension);
        } catch (IOException e) {
            return 102; //Error Reading from file
        }

        byte[] encryptedData;
        try {
            encryptedData = encryptor.encrypt(fileBytes, key);
        } catch (Exception ex) {
            return 103; //Internal Error
        }

        String encryptedFilename = filename.substring(0, indexDot) + ".enc";
        File encryptedfile = new File(encryptedFilename);
        try (FileOutputStream fos = new FileOutputStream(encryptedfile)) {
            fos.write(encryptedData); 
            fos.write(algoType);
        } catch (IOException e) {
            return 104; //Error Writing to new File
        }
        if(keepOriginal==false){
            Path path = Paths.get(filename);
            try {
                Files.delete(path);
            } catch (IOException ex) {
                return 105; //Error Deleting Orginal File
            }
        }
        return 0;
    }
}