package com.mohanvamsi06.FileEncryptor.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Encryptor{
    public int EncryptFile(String filename, String pass, String algo, boolean keepOriginal){
        int keySize;
        EncryptionService encryptor;
        switch (algo) {
            case "des" -> {
                keySize = 8;
                encryptor = new DESUtil();
            }
            case "aes" -> {
                keySize = 16;
                encryptor = new AESUtil();
            }
            case "blowfish" -> {
                keySize = 16;
                encryptor = new BlowfishUtil();
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

        byte[] encryptedData;
        try {
            encryptedData = encryptor.encrypt(fileBytes, key);
        } catch (Exception ex) {
            return 103;
        }

        String encryptedFilename = filename + ".enc";
        File encryptedfile = new File(encryptedFilename);
        try (FileOutputStream fos = new FileOutputStream(encryptedfile)) {
            fos.write(encryptedData); 
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