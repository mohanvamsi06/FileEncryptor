package com.mohanvamsi06.FileEncryptor.encryptor;

/** Interface for encryption and decryption services. */
public interface EncryptionService {
  byte[] encrypt(byte[] data, byte[] key) throws Exception;

  byte[] decrypt(byte[] data, byte[] key) throws Exception;
}
