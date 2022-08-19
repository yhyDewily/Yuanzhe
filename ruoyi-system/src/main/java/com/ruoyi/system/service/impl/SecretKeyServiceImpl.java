package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.time.LocalDateTime;
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

    // TODO 插入数据库时还有数据需要后续决定为何值
    @Override
    public Map<String,String> applyKeyPair(String type, String keyName) {
        // 先根据SM2算法生成密钥
        SM2KeyPair keyPair = SM2Util.generateorSM2KeyPair();
        LocalDateTime creatTime = LocalDateTime.now();

        // 拿到公私钥参数
        ECPublicKeyParameters publicKeyParam = keyPair.getPublicKeyParam();
        ECPrivateKeyParameters privateKeyParam = keyPair.getPrivateKeyParam();

        // 获取到公私钥
        String publicKey = keyPair.convertPublicKeyXToString()+keyPair.convertPublicKeyYToString();
        String privateKey = keyPair.convertPrivatekeyDToString();
        Map<String,String> keyMap = new HashMap<>();
        keyMap.put("publicKey",publicKey);
        keyMap.put("privateKey",privateKey);
        // 为密钥表插入该密钥信息
        SecretKey secretKey = new SecretKey();
        secretKey.setKeyName(keyName);
        secretKey.setKeySymmetry(false);
        secretKey.setPublicKey(publicKey);
        secretKey.setPrivateKey(privateKey);
        secretKey.setCreateTime(creatTime);
        secretKey.setSerialNumber("123456789");
        secretKey.setKeyType("SM2");
        this.save(secretKey);
        return keyMap;


        // 获取到公钥
//        String publicKey = encoder64.encodeToString(publicKeyParam.getQ().getAffineXCoord().getEncoded())
//                + encoder64.encodeToString(publicKeyParam.getQ().getAffineYCoord().getEncoded());
        // 获取到私钥
//        String privateKey = encoder64.encodeToString(privateKeyParam.getD().toByteArray());


    }
}
