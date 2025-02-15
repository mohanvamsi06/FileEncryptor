package com.mohanvamsi06.FileEncryptor.encryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
AES encryption and decryption utility.
*/

public class AESUtil implements EncryptionService {
  @Override
  public byte[] encrypt(byte[] data, byte[] key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    return cipher.doFinal(data);
  }

  @Override
  public byte[] decrypt(byte[] data, byte[] key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    return cipher.doFinal(data);
  }
}
