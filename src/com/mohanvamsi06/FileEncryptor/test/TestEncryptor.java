package com.mohanvamsi06.FileEncryptor.test;

import com.mohanvamsi06.FileEncryptor.encryptor.*;
import java.util.Arrays;

public class TestEncryptor {

    public static void main(String[] args) {
        try {
            // Test data and keys
            String data = "Hello, World!";
            byte[] dataBytes = data.getBytes();

            // 16 bytes for AES and Blowfish, 8 bytes for DES
            byte[] aesKey = "1234567890123456".getBytes();
            byte[] blowfishKey = "1234567890123456".getBytes();
            byte[] desKey = "12345678".getBytes();

            // AES Test
            System.out.println("Testing AES...");
            EncryptionService aes = new AESUtil();
            testEncryptor(aes, dataBytes, aesKey);

            // Blowfish Test
            System.out.println("\nTesting Blowfish...");
            EncryptionService blowfish = new BlowfishUtil();
            testEncryptor(blowfish, dataBytes, blowfishKey);

            // DES Test
            System.out.println("\nTesting DES...");
            EncryptionService des = new DESUtil();
            testEncryptor(des, dataBytes, desKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testEncryptor(EncryptionService encryptor, byte[] data, byte[] key) throws Exception {
        byte[] encryptedData = encryptor.encrypt(data, key);
        System.out.println("Encrypted Data: " + Arrays.toString(encryptedData));

        byte[] decryptedData = encryptor.decrypt(encryptedData, key);
        System.out.println("Decrypted Data: " + new String(decryptedData));

        if (Arrays.equals(data, decryptedData)) {
            System.out.println("Encryption/Decryption successful!");
        } else {
            System.out.println("Encryption/Decryption failed.");
        }
    }
}
