package com.ruoyi.system.utils;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

public class SM2Util {

    //椭圆曲线ECParameters ASN.1 结构
    private static final X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    //椭圆曲线公钥或私钥的基本域参数。
    private static final ECParameterSpec ecDomainParameters = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    public static SM2KeyPair generateorSM2KeyPair() {
        // 获取一串SM2的曲线参数，sm2p256v1 是sm2推荐的曲线参数
        X9ECParameters eccParamters = GMNamedCurves.getByName("sm2p256v1");

        // 创建密钥生成器
        AsymmetricCipherKeyPairGenerator keyPair = new ECKeyPairGenerator();

        // 构造Ecc有限域参数
        // TODO new SecureRandom("1".getBytes()) 填入随机种子，避免参数重复
        KeyGenerationParameters keyParam = new ECKeyGenerationParameters(new ECDomainParameters(eccParamters.getCurve(),
                eccParamters.getG(), eccParamters.getN(), eccParamters.getH()), new SecureRandom());
        // 初始化生成器
        keyPair.init(keyParam);
        // 生成密钥对
        AsymmetricCipherKeyPair eccKeyPair = keyPair.generateKeyPair();
        ECPublicKeyParameters ecPublicKeyParameters = (ECPublicKeyParameters) eccKeyPair.getPublic();
        ECPrivateKeyParameters ecPrivateKeyParameters = (ECPrivateKeyParameters) eccKeyPair.getPrivate();

        return new SM2KeyPair(ecPublicKeyParameters, ecPrivateKeyParameters, eccParamters);

    }

    /**
     * 获得私钥签名信息
     *
     * @param sm2KeyPair 自定义的SM2的公私钥对信息
     * @return 返回私钥的签名信息
     * @throws OperatorCreationException
     */
    public static ContentSigner getSM2SignerInfo(SM2KeyPair sm2KeyPair) throws OperatorCreationException {

        // 重写ECPrivateKey的方式生成证书签名者信息
        return new JcaContentSignerBuilder("SM3WITHSM2").setProvider(new BouncyCastleProvider())
                .build(new ECPrivateKey() {

                    @Override
                    public ECParameterSpec getParameters() {
                        ECDomainParameters privatekeyInfo = sm2KeyPair.getPrivateKeyParam().getParameters();
                        return new ECParameterSpec(privatekeyInfo.getCurve(), privatekeyInfo.getG(),
                                privatekeyInfo.getN());
                    }

                    @Override
                    public String getAlgorithm() {
                        return "SM3WITHSM2";
                    }

                    @Override
                    public String getFormat() {
                        return sm2KeyPair.convertPrivatekeyDToString();
                    }

                    @Override
                    public byte[] getEncoded() {
                        return sm2KeyPair.convertPrivatekeyDToByte();
                    }

                    @Override
                    public BigInteger getD() {
                        return sm2KeyPair.getPrivateKeyD();
                    }

                });
    }

    /**
     * 获得私钥签名信息
     *
     * @param privateKeyParam 构建ECC椭圆曲线的参数
     * @param privateKeyInfo  PKCS8私钥格式信息
     * @return
     * @throws OperatorCreationException
     * @throws IOException
     */
    public static ContentSigner getSM2SignerInfo(X9ECParameters privateKeyParam, PrivateKeyInfo privateKeyInfo)
            throws OperatorCreationException, IOException {
        byte[] pkcs8PrivateKeyObject = privateKeyInfo.getPrivateKey().getOctets();

        // 钥数据对象转换成ASN1结构
        ASN1Sequence asn1Sequence = ASN1Sequence.getInstance(pkcs8PrivateKeyObject);

        /**
         * 指定获取ASN1结构中的offset为1的数据
         * 但如果不是按照PKCS8要求的数据格式就无法解析了
         */
        byte[] privateInfo = asn1Sequence.getObjectAt(1).toASN1Primitive().getEncoded();

        byte[] newPrivateInfo = new byte[privateInfo.length - 2];

        // 拷贝数组 ,从原数组offset为2的地方一直到数据结束的数据拷贝到新数组中去。
        System.arraycopy(privateInfo, 2, newPrivateInfo, 0, privateInfo.length - 2);

        // 重写ECPrivateKey的方式生成证书签名者信息
        return new JcaContentSignerBuilder("SM3WITHSM2").setProvider(new BouncyCastleProvider())
                .build(new ECPrivateKey() {

                    @Override
                    public ECParameterSpec getParameters() {

                        return new ECParameterSpec(privateKeyParam.getCurve(), privateKeyParam.getG(),
                                privateKeyParam.getN(), privateKeyParam.getN());
                    }

                    @Override
                    public String getAlgorithm() {
                        return "SM3WITHSM2";
                    }

                    @Override
                    public String getFormat() {

                        return Base64.getEncoder().encodeToString(newPrivateInfo);
                    }

                    @Override
                    public byte[] getEncoded() {

                        return newPrivateInfo;
                    }

                    @Override
                    public BigInteger getD() {

                        return new BigInteger(newPrivateInfo);
                    }

                });
    }

    /**
     * 将两个byte数组合并成为一个
     *
     * @param data1 前面的byte数组
     * @param data2 后面的byte数组
     * @return
     */
    public static byte[] mergeBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    /**
     * 采用C1C3C2加密方式加密
     *
     * @param publicKeyHex 十六进制公钥
     * @param data         要加密的数据
     * @return 加密后的数据
     */
    public static String encrypt(String publicKeyHex, String data) {

        return encrypt(getECPublicKeyByPublicKeyHex(publicKeyHex), data, 1);
    }

    /**
     * 对数据进行加密，包含了C1C3C2和C1C2C3两种模式
     *
     * @param publicKey BCECPublicKey SM2公钥对象
     * @param data      加密的数据
     * @param modeType  加密模式，1为C1C3C2，其他数字就为C1C2C3
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(BCECPublicKey publicKey, String data, int modeType) {
        //加密模式
        SM2Engine.Mode mode = SM2Engine.Mode.C1C3C2;

        if (modeType != 1) {
            mode = SM2Engine.Mode.C1C2C3;
        }
        //通过公钥对象获取公钥的基本域参数。
        ECParameterSpec ecParameterSpec = publicKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                ecParameterSpec.getG(), ecParameterSpec.getN());
        //通过公钥值和公钥基本参数创建公钥参数对象
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(publicKey.getQ(), ecDomainParameters);
        //根据加密模式实例化SM2公钥加密引擎
        SM2Engine sm2Engine = new SM2Engine(mode);
        //初始化加密引擎
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        byte[] arrayOfBytes = null;
        try {
            //将明文字符串转换为指定编码的字节串
            byte[] in = data.getBytes("utf-8");
            //通过加密引擎对字节数串行加密
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
        } catch (Exception e) {
            System.out.println("SM2加密时出现异常:" + e.getMessage());
            e.printStackTrace();
        }
        //将加密后的字节串转换为十六进制字符串
        return Hex.toHexString(arrayOfBytes);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyHex SM2十六进制私钥
     * @param cipherData    密文数据
     * @return 解密之后的UTF-8编码的字符串
     */
    public static String decrypt(String privateKeyHex, String cipherData) {
        return decrypt(getBCECPrivateKeyByPrivateKeyHex(privateKeyHex), cipherData, 1);
    }

    /**
     * @param privateKey SM私钥
     * @param cipherData 密文数据
     * @param modeType   解密模式
     * @return 解密之后的UTF-8编码的字符串
     */
    public static String decrypt(BCECPrivateKey privateKey, String cipherData, int modeType) {
        //解密模式
        SM2Engine.Mode mode = SM2Engine.Mode.C1C3C2;
        if (modeType != 1)
            mode = SM2Engine.Mode.C1C2C3;
        //将十六进制字符串密文转换为字节数组（需要与加密一致，加密是：加密后的字节数组转换为了十六进制字符串）
        byte[] cipherDataByte = Hex.decode(cipherData);
        //通过私钥对象获取私钥的基本域参数。
        ECParameterSpec ecParameterSpec = privateKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                ecParameterSpec.getG(), ecParameterSpec.getN());
        //通过私钥值和私钥钥基本参数创建私钥参数对象
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(privateKey.getD(),
                ecDomainParameters);
        //通过解密模式创建解密引擎并初始化
        SM2Engine sm2Engine = new SM2Engine(mode);
        sm2Engine.init(false, ecPrivateKeyParameters);
        String result = null;
        try {
            //通过解密引擎对密文字节串进行解密
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            //将解密后的字节串转换为utf8字符编码的字符串（需要与明文加密时字符串转换成字节串所指定的字符编码保持一致）
            result = new String(arrayOfBytes, "utf-8");
        } catch (Exception e) {
            System.out.println("SM2解密时出现异常" + e.getMessage());
        }
        return result;
    }


    /**
     * @param pubKeyHex 64字节十六进制公钥字符串(如果公钥字符串为65字节首个字节为0x04：表示该公钥为非压缩格式，操作时需要删除)
     * @return BCECPublicKey SM2公钥对象
     * @Description 公钥字符串转换为 BCECPublicKey 公钥对象
     * @Author msx
     */
    public static BCECPublicKey getECPublicKeyByPublicKeyHex(String pubKeyHex) {
        //截取64字节有效的SM2公钥（如果公钥首个字节为0x04）
        if (pubKeyHex.length() > 128) {
            pubKeyHex = pubKeyHex.substring(pubKeyHex.length() - 128);
        }
        //将公钥拆分为x,y分量（各32字节）
        String stringX = pubKeyHex.substring(0, 64);
        String stringY = pubKeyHex.substring(stringX.length());
        //将公钥x、y分量转换为BigInteger类型
        BigInteger x = new BigInteger(stringX, 16);
        BigInteger y = new BigInteger(stringY, 16);
        //通过公钥x、y分量创建椭圆曲线公钥规范
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecDomainParameters);
        //通过椭圆曲线公钥规范，创建出椭圆曲线公钥对象（可用于SM2加密及验签）
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    /**
     * @param privateKeyHex 32字节十六进制私钥字符串
     * @return BCECPrivateKey SM2私钥对象
     * @Description 私钥字符串转换为 BCECPrivateKey 私钥对象
     * @Author msx
     */
    public static BCECPrivateKey getBCECPrivateKeyByPrivateKeyHex(String privateKeyHex) {
        //将十六进制私钥字符串转换为BigInteger对象
        BigInteger d = new BigInteger(privateKeyHex, 16);
        //通过私钥和私钥域参数集创建椭圆曲线私钥规范
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecDomainParameters);
        //通过椭圆曲线私钥规范，创建出椭圆曲线私钥对象（可用于SM2解密和签名）
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

}
