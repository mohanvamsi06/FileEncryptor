package com.mohanvamsi06.FileEncryptor.encryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
Blowfish encryption and decryption utility.
*/

public class BlowfishUtil implements EncryptionService {
    @Override
    public byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish");
        SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish");
        SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }
}