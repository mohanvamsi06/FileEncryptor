package com.mohanvamsi06.FileEncryptor.encryptor;

import java.util.HashMap;
import java.util.Map;

public abstract class Encryptor {
    private static final Map<Integer, String> ERROR_MESSAGES = new HashMap<>();

    static {
        ERROR_MESSAGES.put(101, "Unsupported encryption algorithm.");
        ERROR_MESSAGES.put(102, "Error reading the file.");
        ERROR_MESSAGES.put(103, "Wrong password.");
        ERROR_MESSAGES.put(104, "Error writing to the new file.");
        ERROR_MESSAGES.put(105, "Error deleting the original file.");
        ERROR_MESSAGES.put(106, "Password incorrect or file encrypted using a different application.");
    }

    public static String getErrorMessage(int code) {
        return ERROR_MESSAGES.getOrDefault(code, "Unknown error.");
    }


    public abstract int EncryptFile(String filePath, String password, String algorithm, boolean keepOriginal);

    public abstract int DecryptFile(String filename, String pass, boolean keepOriginal);
}
