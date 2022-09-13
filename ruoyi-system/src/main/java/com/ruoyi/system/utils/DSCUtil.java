package com.ruoyi.system.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DSCUtil {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        /*
        * 1.明文
        * 2.提供原始密钥：长度64位，8个字节
        *
        * 加密算法：就是通过对比特位进行一些数学运算
        * */
        String clearText = "一直在努力的笨鸟";//明文
        String originKey = "他长的是真鸡儿帅啊";//原始密钥
        
        String cipherText = desEncript(clearText,originKey);//明文-->密文
        System.out.println("明文： " + clearText);
        System.out.println("明文-->密文： " + cipherText);

        String cipherText2 = desDecript(cipherText,originKey);//密文-->明文
        System.out.println("密文-->明文： " + cipherText2);
    }


    /**
     * 用DES算法进行加密
     * @param clearText
     * @param originKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String desEncript(String clearText, String originKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //1.获取加密算法工具类对象  参数：转换的名称，例如 DES/CBC/PKCS5Padding。
        Cipher cipher = Cipher.getInstance("DES");

        /*
        * 2.
        *   public final void init(int opmode,Certificate certificate) throws InvalidKeyException
        *   对工具类对象进行初始化
        *
        * 参数 opmode：加密/解密模式
        *               DECRYPT_MODE 用于将 Cipher 初始化为  解密  模式的常量。
        *               ENCRYPT_MODE 用于将 Cipher 初始化为  加密  模式的常量。
        * 参数 certificate：对原始密钥处理之后的密钥
        *
        * 注意：加密解密模式要选对，我当时写加密的时候选了DECRYPT_MODE，报了一个这个错
        *      Input length must be multiple of 8 when decrypting with padded cipher
        *      莫名其妙，研究好久才发现。
        * */
        SecretKeySpec key = getKey(originKey);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        //3.用加密工具类对象对明文进行加密-->密文
        byte[] doFinal = cipher.doFinal(clearText.getBytes());

        //密文转换成base64编码，解决密文乱码问题
        byte[] encode = Base64.getEncoder().encode(doFinal);
        return new String(encode);
    }

    /**
     * 不论原始密钥originKey多长，我们都要醒程一个8个字节的原始密钥
     * @param originKey
     * @return
     */
    private static SecretKeySpec getKey(String originKey) {
        //byte数组每个元素默认初始值位0
        byte[] buffer = new byte[8];
        //获取用户提供的原始密钥字节数组
        byte[] originKeyBytes = originKey.getBytes();
        //如果originKeyBytes.length > 8,只要8个字节，如果没有超过8个字节，就用默认初始值来填充
        for (int i = 0; i < 8 && i < originKeyBytes.length; i++) {
            buffer[i] = originKeyBytes[i];
        }
        /*
        * public SecretKeySpec(byte[] key, String algorithm)  根据给定的字节数组构造一个密钥
        * 参数 key：密钥的密钥内容。
        * 参数 algorithm：与给定的密钥内容相关联的密钥算法的名称。
        * */
        SecretKeySpec key = new SecretKeySpec(buffer, "DES");
        return key;
    }

    /**
     * 用DES算法进行解密
     * @param cipherText
     * @param originKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String desDecript(String cipherText, String originKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        SecretKeySpec key = getKey(originKey);
        cipher.init(Cipher.DECRYPT_MODE,key);
        //注意：因为密文是用Base64转换后的，所以在doFinal以前要用Base64转一下
        byte[] decode = Base64.getDecoder().decode(cipherText);
        byte[] doFinal = cipher.doFinal(decode);
        return new String(doFinal);
    }

}
