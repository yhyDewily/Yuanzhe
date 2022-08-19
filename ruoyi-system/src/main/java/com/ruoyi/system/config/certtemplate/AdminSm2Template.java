package com.ruoyi.system.config.certtemplate;

import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminSm2Template {

    public void applySm2Template(X500Name subject, SubjectPublicKeyInfo publicKeyInfo) throws IOException {

        // X500Name subject = new X500Name(rDNs);

        PKCS10CertificationRequestBuilder p10Builder = new PKCS10CertificationRequestBuilder(subject, publicKeyInfo);

        /**
         * 证书用途
         * 数字签名，密钥协商，不可否认。并标记为是关键扩展
         */
        KeyUsage keyUsage = new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyAgreement | KeyUsage.nonRepudiation);

        /**
         * 构造X.509 V3扩展字段
         */
        ExtensionsGenerator exKeyUsage = new ExtensionsGenerator();
        // 把密钥用途标记为关键扩展
        exKeyUsage.addExtension(Extension.keyUsage, true, keyUsage);
        // exKeyUsage.addExtension(Extension.extendedKeyUsage);

        /**
         * 扩展证书用途
         * 代码签名，时间戳，客户端认证，OCSP签名
         */

        p10Builder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, exKeyUsage.generate());

        // PKCS10CertificationRequest csr = p10Builder.build();

    }

}
