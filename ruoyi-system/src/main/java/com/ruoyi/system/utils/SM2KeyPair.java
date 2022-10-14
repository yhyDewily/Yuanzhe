package com.ruoyi.system.utils;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ECPoint;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SM2KeyPair {
    private final ECPublicKeyParameters publicKeyParam;
    private final ECPrivateKeyParameters privateKeyParam;
    private X9ECParameters x9ecParameters;

    private final Base64.Encoder encoder64 = Base64.getEncoder();

    public SM2KeyPair(ECPublicKeyParameters publicKeyParam, ECPrivateKeyParameters privateKeyParam) {
        this.publicKeyParam = publicKeyParam;
        this.privateKeyParam = privateKeyParam;
    }

    public SM2KeyPair(ECPublicKeyParameters publicKeyParam, ECPrivateKeyParameters privateKeyParam,
                      X9ECParameters x9ecParameters) {
        this.publicKeyParam = publicKeyParam;
        this.privateKeyParam = privateKeyParam;
        this.x9ecParameters = x9ecParameters;
    }

    /** 获取椭圆曲线上的公钥参数 */
    public ECPublicKeyParameters getPublicKeyParam() {
        return publicKeyParam;
    }

    /** 获取椭圆曲线上的私钥参数 */
    public ECPrivateKeyParameters getPrivateKeyParam() {
        return privateKeyParam;
    }

    /** 获取椭圆曲线上的公钥x轴上的坐标点 */
    public ECFieldElement getPublicKeyX() {
        return getPublicKeyParam().getQ().getAffineXCoord();
    }

    /** 获取椭圆曲线上的公钥Y轴上的坐标点 */
    public ECFieldElement getPublicKeyY() {
        return getPublicKeyParam().getQ().getAffineYCoord();
    }

    /** 获取椭圆曲线上 基点到最终公钥坐标点 所做的加密次数 就是私钥的值 */
    public BigInteger getPrivateKeyD() {

        return getPrivateKeyParam().getD();
    }

    /** 把公钥x轴上的坐标点转换为字节数组 */
    public byte[] convertPublicKeyXToByte() {
        return getPublicKeyX().getEncoded();
    }

    /** 把公钥Y轴上的坐标点转换为字节数组 */
    public byte[] convertPublicKeyYToByte() {
        return getPublicKeyY().getEncoded();
    }

    /** 把私钥转换为字节数组 */
    public byte[] convertPrivatekeyDToByte() {
        return getPrivateKeyD().toByteArray();
    }

    /** 把公钥x轴上的坐标点转换为BASE64编码 */
    public String convertPublicKeyXToString() {
        return encoder64.encodeToString(convertPublicKeyXToByte());
    }

    /** 把公钥Y轴上的坐标点转换为BASE64编码 */
    public String convertPublicKeyYToString() {
        return encoder64.encodeToString(convertPublicKeyYToByte());
    }

    /** 把私钥转换为BASE64编码 */
    public String convertPrivatekeyDToString() {
        return encoder64.encodeToString(convertPrivatekeyDToByte());
    }

    /** 把公钥转换为ASN.1对象 */
    public SubjectPublicKeyInfo getX509SubjectPublicInfo() throws IOException {
        // bc库处理有问题，没有对sm2p256v1进行定义
        // return
        // SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(getPublicKeyParam());

        // 把公钥转换为asn.1格式数据
        ASN1OctetString publicKey = (ASN1OctetString) new X9ECPoint(getPublicKeyParam().getQ(), false)
                .toASN1Primitive();
        /*
         * GMObjectIdentifiers GM对象标识符库
         */
        SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(
                new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, GMObjectIdentifiers.sm2p256v1),
                publicKey.getOctets());

        return subjectPublicKeyInfo;
    }

    /** 把私钥转换为ASN.1对象 */
    public PrivateKeyInfo getX509PrivateInfo() throws IOException {
        // return PrivateKeyInfoFactory.createPrivateKeyInfo(getPrivateKeyParam());

        // bc库处理有问题，没有对sm2p256v1进行定义
        // ECDomainParameters privateDomainParams = privateKeyParam.getParameters();

        // ECC椭圆曲线计算的终点就是公钥 , 基点G * 运算次数D (私钥) = 公钥坐标点Q
        // ECPoint q = new
        // FixedPointCombMultiplier().multiply(privateDomainParams.getG(),
        // getPrivateKeyD());

        // DERBitString publicKey = new DERBitString(q.getEncoded(false));

        // ASN1BitString privateKey = (ASN1BitString)
        // ASN1BitString.fromByteArray(convertPrivatekeyDToByte());
        // DERBitString.convert(privateKey);

        return new PrivateKeyInfo(
                new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey,
                        GMObjectIdentifiers.sm2p256v1),
                new ECPrivateKey(privateKeyParam.getParameters().getN().bitLength(), getPrivateKeyD()),
                null, convertECPublicKeyPointToByte());
    }

    /** 获取椭圆曲线上的公钥对象 */
    public ECPoint getECPublicKeyPoint() {
        return getPublicKeyParam().getQ();
    }

    /**
     * 把ECPoint上的公钥转换为字节数组并压缩数组长度,sm2建议压缩公钥长度。
     * 压缩过的公钥,以16进制显示的公钥头部会以0x03开头
     * 若公钥的y坐标最后一位是0，公钥头部会以0x02开头
     * 压缩就是把公钥的Y坐标点舍去，只返回公钥的X坐标点
     */
    public byte[] convertECPublicKeyPointToCompressedByte() {
        return getECPublicKeyPoint().getEncoded(true);
    }

    /**
     * 把ECPoint上的公钥转换为字节数组并压缩数组长度,sm2建议压缩公钥长度。
     * 未压缩过的公钥,以16进制显示的公钥头部会以0x04开头
     * 若公钥的y坐标最后一位是0，公钥头部会以0x02开头
     */
    public byte[] convertECPublicKeyPointToByte() {
        return getECPublicKeyPoint().getEncoded(false);
    }

    /**
     * 把ECPoint上的公钥转换为压缩过的BASE64字符串,长度是44
     */
    public String convertECPublicKeyPointToCompressedString() {
        return encoder64.encodeToString(convertECPublicKeyPointToCompressedByte());
    }

    /**
     * 把ECPoint上的公钥转换为未压缩过的BASE64字符串，长度是88
     */
    public String convertECPublicKeyPointToString() {
        return encoder64.encodeToString(convertECPublicKeyPointToByte());
    }
    /**
     * 获得经过BASE64编码的SM2密钥对;
     * get("private") 获取私钥;
     * get("public") 获取公钥;
     *
     * @param origin 是否获取原生的密钥对，不是的话获取PKCS8包装的密钥对
     * @throws IOException
     */
    public Map<String, String> getSM2KeyPair(boolean origin) throws IOException {
        HashMap<String, String> sm2KeyPair = new HashMap<>(2);

        String privateKey = null;
        String publicKey = null;

        if (origin) {
            privateKey = convertPrivatekeyDToString();
            publicKey = convertECPublicKeyPointToString();
        } else {
            privateKey = encoder64.encodeToString(getX509PrivateInfo().getEncoded());
            publicKey = encoder64.encodeToString(getX509SubjectPublicInfo().getEncoded());
        }

        sm2KeyPair.put("private", privateKey);
        sm2KeyPair.put("public", publicKey);

        return sm2KeyPair;
    }

    /**
     * 获取一串经过PKCS8包装的私钥
     *
     * @return 返回一串经过PKCS8包装的私钥
     * @throws IOException
     */
    public String getPKCS8PrivateKey() throws IOException {
        return getSM2KeyPair(false).get("private");
    }

    /**
     * 获取一串经过PKCS8包装的公钥
     *
     * @return 返回一串经过PKCS8包装的公钥
     * @throws IOException
     */
    public String getPKCS8PublicKey() throws IOException {
        return getSM2KeyPair(false).get("public");
    }

    /**
     * 获取ECC椭圆参数
     */
    public X9ECParameters getX9ecParameters() {
        if (x9ecParameters != null) {
            return x9ecParameters;
        }

        return new X9ECParameters(getPublicKeyParam().getParameters().getCurve(), new X9ECPoint(getECPublicKeyPoint(), false),
                getPublicKeyParam().getParameters().getN(), getPublicKeyParam().getParameters().getH());
    }

    /**
     * ECC椭圆参数转换成字节数组
     * @return
     * @throws IOException
     */
    public byte[] convertX9ecParametersToByteArray() throws IOException{
        return getX9ecParameters().getEncoded(ASN1Encoding.DER);
    }

    /**
     * ECC椭圆参数经过Base64编码转换成字符串
     * @return
     * @throws IOException
     */
    public String convertX9ecParametersToString() throws IOException{
        return encoder64.encodeToString(convertX9ecParametersToByteArray());
    }

}
