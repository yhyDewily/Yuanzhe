package com.ruoyi.system.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
/**
 *
 * @author bestFang666
 * @date 2022/11/9 11:35
 */
public class GMBaseUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
