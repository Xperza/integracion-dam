package com.dam.springboot.backend.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class Encriptar {
	private static final String key = "0123456789abcdef";
    private static final String iv = "0123456789abcdef"; 

    public static String encrypt(int data) throws Exception {
        String dataStr = String.valueOf(data);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(dataStr.getBytes());
        return Base64.encodeBase64String(encrypted);
    }
}
