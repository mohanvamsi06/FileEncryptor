package com.mohanvamsi06.FileEncryptor.encryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
DES encryption and decryption utility.
*/

public class DESUtil implements EncryptionService {
    @Override
    public byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        SecretKeySpec keySpec = new SecretKeySpec(key, "DES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        SecretKeySpec keySpec = new SecretKeySpec(key, "DES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }
}