package com.mohanvamsi06.FileEncryptor.test;

import com.mohanvamsi06.FileEncryptor.encryptor.*;

class TestEncryptor{
    public static void main(String[] args) {
        Encryptor enc = new Encryptor();
        String password = "mypassword";
        String file1 = "/home/vamsi/Desktop/TEST/test1.txt";
        String file2 = "/home/vamsi/Desktop/TEST/test2.txt";
        String file3 = "/home/vamsi/Desktop/TEST/test3.txt";
        String file4 = "/home/vamsi/Desktop/TEST/test4.txt";

        System.out.println("Starting Encryption!!");
        System.out.println(enc.EncryptFile(file1, password, "aes", false));
        System.out.println(enc.EncryptFile(file2, password, "aes", true));
        System.out.println(enc.EncryptFile(file3, password, "des", false));
        System.out.println(enc.EncryptFile(file4, password, "blowfish", true));

        Decryptor dec = new Decryptor();
        String file11 = "/home/vamsi/Desktop/TEST/test1.enc";
        String file21 = "/home/vamsi/Desktop/TEST/test2.enc";
        String file31 = "/home/vamsi/Desktop/TEST/test3.enc";
        String file41 = "/home/vamsi/Desktop/TEST/test4.enc";
        System.out.println("Starting Decryption!!");
        System.out.println(dec.DecryptFile(file11, password, true));
        System.out.println(dec.DecryptFile(file21, password, false));
        System.out.println(dec.DecryptFile(file31, password, true));
        System.out.println(dec.DecryptFile(file41, password, false));

        System.out.println("Closing program!!");
    }
}