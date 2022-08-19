package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SecretKey;
import com.ruoyi.system.mapper.SecretKeyMapper;
import com.ruoyi.system.service.SecretKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.utils.SM2KeyPair;
import com.ruoyi.system.utils.SM2Util;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 密钥表 服务实现类
 * </p>
 *
 * @author lls
 * @since 2022-08-19
 */
@Service
public class SecretKeyServiceImpl extends ServiceImpl<SecretKeyMapper, SecretKey> implements SecretKeyService {

    private final Base64.Encoder encoder64 = Base64.getEncoder();

    @Override
    public Map<String,String> applyKeyPair(String type, String keyName) {
        // 先根据SM2算法生成密钥
        SM2KeyPair keyPair = SM2Util.generateorSM2KeyPair();

        // 拿到公私钥参数
        ECPublicKeyParameters publicKeyParam = keyPair.getPublicKeyParam();
        ECPrivateKeyParameters privateKeyParam = keyPair.getPrivateKeyParam();

        // 获取到公私钥
        String publicKey = keyPair.convertPublicKeyXToString()+keyPair.convertPublicKeyYToString();
        String privateKey = keyPair.convertPrivatekeyDToString();
        Map<String,String> keyMap = new HashMap<>();
        keyMap.put("publicKey",publicKey);
        keyMap.put("privateKey",privateKey);
        return keyMap;
        // 获取到公钥
//        String publicKey = encoder64.encodeToString(publicKeyParam.getQ().getAffineXCoord().getEncoded())
//                + encoder64.encodeToString(publicKeyParam.getQ().getAffineYCoord().getEncoded());
        // 获取到私钥
//        String privateKey = encoder64.encodeToString(privateKeyParam.getD().toByteArray());


    }
}
