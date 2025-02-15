package com.mohanvamsi06.FileEncryptor.encryptor.impl;

import com.mohanvamsi06.FileEncryptor.encryptor.AESUtil;
import com.mohanvamsi06.FileEncryptor.encryptor.BlowfishUtil;
import com.mohanvamsi06.FileEncryptor.encryptor.DESUtil;
import com.mohanvamsi06.FileEncryptor.encryptor.EncryptionService;
import com.mohanvamsi06.FileEncryptor.encryptor.Encryptor;
import com.mohanvamsi06.FileEncryptor.encryptor.KeyGen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptorImpl extends Encryptor {
  @Override
  public int EncryptFile(String filename, String pass, String algo, boolean keepOriginal) {
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
    byte[] key = generator.KeyGenerator(keySize, pass);

    File file = new File(filename);
    byte[] fileBytes;
    long fileLength = file.length();
    if (fileLength > (Integer.MAX_VALUE - (lengthOfExtension + 2))) {
      return 106; // File Size Exceeds Limit
    }
    try (FileInputStream fis = new FileInputStream(file)) {
      fileBytes = new byte[((int) fileLength) + 2 + lengthOfExtension];
      fis.read(fileBytes);
      fileBytes[(int) fileLength] = '@';
      fileBytes[(int) fileLength + 1] = '@';
      byte[] extensionName = extension.getBytes();
      System.arraycopy(extensionName, 0, fileBytes, (int) fileLength + 2, lengthOfExtension);
    } catch (IOException e) {
      return 102; // Error Reading from file
    }

    byte[] encryptedData;
    try {
      encryptedData = encryptor.encrypt(fileBytes, key);
    } catch (Exception ex) {
      return 103; // Internal Error
    }

    String encryptedFilename = filename.substring(0, indexDot) + ".enc";
    File encryptedfile = new File(encryptedFilename);
    try (FileOutputStream fos = new FileOutputStream(encryptedfile)) {
      fos.write(encryptedData);
      fos.write(algoType);
    } catch (IOException e) {
      return 104; // Error Writing to new File
    }
    if (keepOriginal == false) {
      Path path = Paths.get(filename);
      try {
        Files.delete(path);
      } catch (IOException ex) {
        return 105; // Error Deleting Orginal File
      }
    }
    return 0;
  }

  @Override
  public int DecryptFile(String filename, String pass, boolean keepOriginal) {
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
      return 102; // Error reading the File
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
    byte[] key = generator.KeyGenerator(keySize, pass);
    byte[] decryptedData;
    try {
      decryptedData = decryptor.decrypt(fileBytes, key);
    } catch (Exception ex) {
      return 103; // Error during decryption, (Possible Wrong password)
    }

    int ind = -1;
    int fileLength = decryptedData.length;
    for (int i = fileLength - 1; i > 0; i--) {
      if (decryptedData[i] == '@' && decryptedData[i - 1] == '@') {
        ind = i;
        break;
      }
    }
    if (ind == -1)
      return 106; // Decryption Failed or Password wrong or Encrypted using different application;

    int extensionLength = fileLength - ind - 1;
    byte[] extension = new byte[extensionLength];
    for (int i = 0; i < extensionLength; i++) {
      extension[i] = decryptedData[ind + 1 + i];
    }
    String extensionString = new String(extension);

    String decryptedFilename = filename.substring(0, filename.length() - 4) + extensionString;
    File decryptedfile = new File(decryptedFilename);
    try (FileOutputStream fos = new FileOutputStream(decryptedfile)) {
      fos.write(decryptedData, 0, ind - 1);
    } catch (IOException e) {
      return 104; // Error Writing to new file
    }
    if (keepOriginal == false) {
      Path path = Paths.get(filename);
      try {
        Files.delete(path);
      } catch (IOException ex) {
        return 105; // Error Deleting Original File
      }
    }
    return 0;
  }
}
