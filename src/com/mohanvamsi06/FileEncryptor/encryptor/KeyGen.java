package com.mohanvamsi06.FileEncryptor.encryptor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeyGen{
    public byte[] KeyGen (int length, String seed){
        try {
            byte[] hash;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(seed.getBytes(StandardCharsets.UTF_8));
            byte[] key = new byte[length];
            System.arraycopy(hash, 0, key, 0, Math.min(key.length, hash.length));
            return key;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }
}