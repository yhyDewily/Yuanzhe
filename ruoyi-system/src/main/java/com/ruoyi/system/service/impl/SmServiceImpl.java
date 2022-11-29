package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.vo.CipherEnvelop;
import com.ruoyi.system.service.ISmService;
import com.ruoyi.system.utils.SM3UtilPlus;
import com.ruoyi.system.utils.SM4Util;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

/**
 * @author bestFang666
 * @date 2022/11/1 16:08
 */
@Service
public class SmServiceImpl implements ISmService {
    /**
     * @param cipherEnvelop SM4 对称密钥和 Iv，以及明文和算法
     * @return 正常密文
     */
    @Override
    public String encryptWithSM4CBCPadding(CipherEnvelop cipherEnvelop) {
        byte[] cipherText = null;
        try {
            cipherText = SM4Util.encrypt_CBC_Padding(cipherEnvelop.getKey().getBytes(), cipherEnvelop.getIv().getBytes(), cipherEnvelop.getText().getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        if (cipherText != null) {
            return Base64.getEncoder().encodeToString(cipherText);
        } else {
            return "";
        }

    }

    /**
     * @param cipherEnvelop SM4 对称密钥和 Iv，以及明文和算法
     * @return 正常明文
     */
    @Override
    public String decryptWithSM4CBCPadding(CipherEnvelop cipherEnvelop) {
        byte[] cipher = Base64.getDecoder().decode(cipherEnvelop.getText().getBytes());
        byte[] text = null;
        try {
            text = SM4Util.decrypt_CBC_Padding(cipherEnvelop.getKey().getBytes(), cipherEnvelop.getIv().getBytes(), cipher);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        if (text != null) {
            return new String(text);
        } else {
            return "";
        }
    }

    /**
     * 使用 SM3 算法来进行摘要
     *
     * @param cipherEnvelop 使用其中的 text（明文）
     * @return 摘要后内容
     */
    @Override
    public String abstarctSM3(CipherEnvelop cipherEnvelop) {
        byte[] abstarctByte = SM3UtilPlus.hash(cipherEnvelop.getText().getBytes());
        String abstractBase64 = null;
        abstractBase64 = Base64.getEncoder().encodeToString(abstarctByte);
        if (abstractBase64 == null) {
            return "";
        } else {
            return abstractBase64;
        }
    }
}
