package com.ruoyi.common.utils;

import com.ruoyi.common.ObjectView.CerView;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CerUtils {

    // 存放证书的目录
    public static String CERTIFICATE_DIR = "C:\\Users\\listen\\Desktop\\远哲\\x509_sm2CA\\证书\\";

    // 读取证书
    public static CerView readCerFromFile(String certificateName){
        CerView newCer = new CerView();
        try{
            // 引入BC库
            Security.addProvider(new BouncyCastleProvider());
            // 使用BC解析X.509证书
            CertificateFactory CF = CertificateFactory.getInstance("X.509", "BC"); // 从证书工厂中获取X.509的单例类
            InputStream fileIn = new FileInputStream(CERTIFICATE_DIR + certificateName); // 将本地证书读入文件流
            Certificate C = CF.generateCertificate(fileIn);  // 将文件流的证书转化为证书类
            String  certificateStr = C.toString();
//            System.out.println("使用[自带库函数]读入证书结果如下：");
//            System.out.print(certificateStr);
//            System.out.println("---------------chen-----------------------\n证书主要字段:");
            X509Certificate cer = (X509Certificate)C;
//            System.out.println("版本号：" + cer.getVersion());
//            System.out.println("序列号：" + cer.getSerialNumber().toString());
//            System.out.println("颁发者：" + cer.getSubjectDN());     // System.out.println("颁发者唯一标识符: " + cer.getSubjectUniqueID().toString());
//            System.out.println("使用者：" + cer.getIssuerDN());
//            System.out.println("有效期：from：" + cer.getNotBefore() + "  to: " + cer.getNotAfter());
//            System.out.println("签发算法" + cer.getSigAlgName());
//            System.out.println("签发算法ID：" + cer.getSigAlgOID());
//            System.out.println("证书签名:" + cer.getSignature().toString());
            byte [] sig = cer.getSigAlgParams();
            PublicKey publicKey = cer.getPublicKey();
            byte [] pkenc = publicKey.getEncoded();
//            System.out.println("解析出的公钥:" + Base64.getEncoder().encodeToString(pkenc));
//            System.out.println("公钥：");
//            for(int i = 0; i < pkenc.length; i++){
//                System.out.print(pkenc[i]);
//            }

            newCer.setVersion(Long.valueOf(cer.getVersion()));
            newCer.setSerialNumber(String.valueOf(cer.getSerialNumber()));
            newCer.setIssuerDn(cer.getIssuerDN().toString());
            newCer.setStartDate(cer.getNotBefore());
            newCer.setFinalDate(cer.getNotAfter());
            newCer.setSubjectDn(cer.getSubjectDN().toString());
            newCer.setPublicKey(new String(pkenc));
            newCer.setSignatureAlgorithm(cer.getSigAlgName());
            newCer.setSignature(cer.getSignature().toString());
            newCer.setStatus(Long.valueOf(1));

        } catch(Exception e){
            e.printStackTrace();
        }

        return newCer;
    }

}
