package com.ruoyi.system.utils;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

public class SM2Util {

    public static SM2KeyPair generateorSM2KeyPair() {
        // 获取一串SM2的曲线参数，sm2p256v1 是sm2推荐的曲线参数
        X9ECParameters eccParamters = GMNamedCurves.getByName("sm2p256v1");

        // 创建密钥生成器
        AsymmetricCipherKeyPairGenerator keyPair = new ECKeyPairGenerator();

        // 构造Ecc有限域参数
        KeyGenerationParameters keyParam = new ECKeyGenerationParameters(new ECDomainParameters(eccParamters.getCurve(),
                eccParamters.getG(), eccParamters.getN(), eccParamters.getH()), new SecureRandom());
        // 初始化生成器
        keyPair.init(keyParam);
        // 生成密钥对
        AsymmetricCipherKeyPair eccKeyPair = keyPair.generateKeyPair();
        ECPublicKeyParameters ecPublicKeyParameters = (ECPublicKeyParameters) eccKeyPair.getPublic();
        ECPrivateKeyParameters ecPrivateKeyParameters = (ECPrivateKeyParameters) eccKeyPair.getPrivate();

        return new SM2KeyPair(ecPublicKeyParameters, ecPrivateKeyParameters);

    }


}
