package com.binh.source.code;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @date 2017年8月1日
 * 
 * @desc AES
 * 
 */

public class AES {

    private static byte[] encrypt(byte[] text, byte[] key) throws Exception {

        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        return cipher.doFinal(text);

    }

    private static byte[] decrypt(byte[] text, byte[] key) throws Exception {

        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        return cipher.doFinal(text);

    }

    /**
     * 
     * @date 2017年8月1日
     * 
     * @desc 加密
     * 
     * @param text 明文
     * 
     * @param key 密钥
     * 
     */

    public static String encodeAES(String text, String key) throws Exception {

        byte[] keybBytes = DigestUtils.md5(key);

        byte[] passwdBytes = text.getBytes();

        byte[] aesBytyes = encrypt(passwdBytes, keybBytes);

        return new String(Base64.encodeBase64(aesBytyes));

    }

    /**
     * 
     * @date 2017年8月1日
     * 
     * @desc 解密
     * 
     * @param password 密文
     * 
     * @param key 密钥
     * 
     */

    public static String deCodeAES(String password, String key) throws Exception {

        byte[] keybBytes = DigestUtils.md5(key);

        byte[] debase64Bytes = Base64.decodeBase64(password.getBytes());

        return new String(decrypt(debase64Bytes, keybBytes));

    }
    
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(deCodeAES("YKf5AFV+PrI8OuUhAQfvAA==", "PgXwrErN4lO0KLQ3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}