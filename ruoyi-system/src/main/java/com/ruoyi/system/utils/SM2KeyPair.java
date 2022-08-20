package com.ruoyi.system.utils;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

public class SM2KeyPair {
    private final ECPublicKeyParameters publicKeyParam;
    private final ECPrivateKeyParameters privateKeyParam;

    private final Base64.Encoder encoder64 = Base64.getEncoder();

    public SM2KeyPair(ECPublicKeyParameters publicKeyParam, ECPrivateKeyParameters privateKeyParam) {
        this.publicKeyParam = publicKeyParam;
        this.privateKeyParam = privateKeyParam;
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
        return SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(getPublicKeyParam());
    }

    /** 把私钥转换为ASN.1对象 */
    public PrivateKeyInfo getX509PrivateInfo() throws IOException {
        return PrivateKeyInfoFactory.createPrivateKeyInfo(getPrivateKeyParam());
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

}
