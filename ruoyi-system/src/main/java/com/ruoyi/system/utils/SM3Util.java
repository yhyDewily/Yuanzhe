package com.ruoyi.system.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class SM3Util {
    static{
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] sm3Encode(byte[] input){
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(input, 0, input.length);
        byte[] ret = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(ret, 0);
        return ret;
    }

    public static String dataDigest(byte[] dataByte){
        MessageDigest digest = null;
        BouncyCastleProvider provider = new BouncyCastleProvider();
        try {
            digest = MessageDigest.getInstance("SM3", provider);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return Hex.toHexString(digest.digest(dataByte));
    }
}
