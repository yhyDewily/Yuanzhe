package com.ruoyi.system.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author bestFang666
 * @date 2022/11/4 10:42
 */
public class SM3UtilPlus extends GMBaseUtil {

    /**
     * 计算SM3摘要值
     *
     * @param srcData 原文
     * @return 摘要值，对于 SM3 算法来说是 32 字节
     */
    public static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 验证摘要
     *
     * @param srcData 原文
     * @param sm3Hash 摘要值
     * @return 返回true标识验证成功，false标识验证失败
     */
    public static boolean verify(byte[] srcData, byte[] sm3Hash) {
        byte[] newHash = hash(srcData);
        if (Arrays.equals(newHash, sm3Hash)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算SM3 Mac值
     *
     * @param key     key值，可以是任意长度的字节数组
     * @param srcData 原文
     * @return Mac值，对于HMac-SM3来说是32字节
     */
    public static byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    public static void main(String[] args) {
        String srcData="44313.bjhgvjfuy";
        String hashData="FIXTyCdmgegPG+vQ2vZUCNF5ff45H+hkSwMq81fHYRM=";
        byte [] srcDataByte = srcData.getBytes();
        byte [] hashDataByte = Base64.getDecoder().decode(hashData);
        boolean verify = verify(srcDataByte, hashDataByte);
        System.out.println(verify);

    }
}
