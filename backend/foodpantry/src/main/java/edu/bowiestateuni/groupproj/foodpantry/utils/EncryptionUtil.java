package edu.bowiestateuni.groupproj.foodpantry.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static SecretKeySpec generateKey(String secretKey) {
        return new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    private static IvParameterSpec generateIv(String iv) {
        return new IvParameterSpec(iv.getBytes());
    }

    public static String encryptObject(String input, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(key), generateIv(iv));
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static String decryptObject(String strToDecrypt, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(key), generateIv(iv));
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
            return new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
}
