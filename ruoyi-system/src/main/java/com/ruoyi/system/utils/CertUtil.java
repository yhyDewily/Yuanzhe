package com.ruoyi.system.utils;

import com.ruoyi.common.exception.OCSPCheckException;
import org.apache.commons.io.FilenameUtils;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author Dewily
 * @date 2022-03-06 18:42
 */
public class CertUtil {

    /**
     * 读取私钥文件
     * @param path
     * @return
     * @throws IOException
     */
    public static PrivateKey readPrivateKeySecondApproach(String path) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        File file = new File(path);
        if (FilenameUtils.getExtension(path).equals("key")) {
            byte[] keyBytes = Files.readAllBytes(Paths.get(path));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
        try (FileReader keyReader = new FileReader(file)) {
            PemReader pemReader = new PemReader(keyReader);
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(content);
            PrivateKey key = factory.generatePrivate(priKeySpec);
            return factory.generatePrivate(priKeySpec);
        }
    }

    /**
     * 根据文件路径读取证书
     * @param filePath 证书路径
     * @return 读取到的证书
     */
    public static X509Certificate getCert(String filePath) {
        Security.removeProvider("BC");
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory cf = null;
        X509Certificate cert = null;
        try {
            //将文件流转换为X509证书
            System.out.println(filePath);
            FileInputStream in = new FileInputStream(filePath);
            cf = CertificateFactory.getInstance("X.509", "BC");
            cert = (X509Certificate)cf.generateCertificate(in);
            //puk = cert.getPublicKey().toString();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new OCSPCheckException("根证书不存在");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return cert;
    }

    /**
     * 获取颁发者证书
     * @param certificate
     * @return
     * @throws IOException
     */
    @SuppressWarnings("All")
    public static X509Certificate getIssuerCert(X509Certificate certificate) throws IOException {
        byte[] extVal = certificate.getExtensionValue(Extension.authorityInfoAccess.getId());
        CertificateFactory cf = null;
        AuthorityInformationAccess aia = AuthorityInformationAccess.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
        AccessDescription[] descriptions = aia.getAccessDescriptions();

        try {
            cf = CertificateFactory.getInstance("X.509", "BC");
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        for (AccessDescription ad : descriptions) {
            if (ad.getAccessMethod().equals(X509ObjectIdentifiers.id_ad_caIssuers)) {
                GeneralName location = ad.getAccessLocation();
                if (location.getTagNo() == GeneralName.uniformResourceIdentifier) {
                    String issuerUrl = location.getName().toString();
                    URL url = new URL(issuerUrl);
                    X509Certificate issuer = null;
                    try {
                        assert cf != null;
                        issuer = (X509Certificate)cf.generateCertificate(url.openStream());
                    } catch (CertificateException e) {
                        e.printStackTrace();
                    }
                    return issuer;
                }
            }
        }
        return null;
    }
}

