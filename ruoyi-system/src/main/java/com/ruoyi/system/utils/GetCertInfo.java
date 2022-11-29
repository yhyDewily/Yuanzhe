package com.ruoyi.system.utils;


import com.ruoyi.system.domain.to.CertDataTO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GetCertInfo {

/*
    public static BufferedInputStream loadFileFromURL(String urlString) {
        BufferedInputStream bis = null;
        HttpURLConnection httpConn = null;
        try {
            // 创建url对象
            URL urlObj = new URL(urlString);
            // 创建HttpURLConnection对象，通过这个对象打开跟远程服务器之间的连接
            httpConn = (HttpURLConnection) urlObj.openConnection();

            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(5000);
            httpConn.connect();

            // 判断跟服务器的连接状态。如果是200，则说明连接正常，服务器有响应
            if (httpConn.getResponseCode() == 200) {
                // 服务器有响应后，会将访问的url页面中的内容放进inputStream中，使用httpConn就可以获取到这个字节流
                bis = new BufferedInputStream(httpConn.getInputStream());
//                InputStreamToFile.writeToLocal(distinctUrl,bis);
                return bis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                // 对流对象进行关闭，对Http连接对象进行关闭。以便释放资源。
//                bis.close();
//                httpConn.disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }
*/

    public static CertDataTO getCertSnFromCert(String fileout) {
//        HashMap<String, Object> certMap = new HashMap<>();
        CertDataTO certDataTO = new CertDataTO();
        //加入头尾
        String Header = "-----BEGIN CERTIFICATE-----\n";
        String End = "\n-----END CERTIFICATE-----";
        fileout = Header + fileout + End;
        InputStream fileIn = null;
        String certSnHex = new String();
        try {
//            String fileout = fileToString(file);

            //将内容从String再次转成Input
            InputStream filestream = new ByteArrayInputStream(fileout.getBytes());

            // 引入BC库
            Security.addProvider(new BouncyCastleProvider());
            // 使用BC解析X.509证书
            CertificateFactory CF = CertificateFactory.getInstance("X.509", "BC"); // 从证书工厂中获取X.509的单例类

            // 将文件流的证书转化为证书类
            Certificate C = CF.generateCertificate(filestream);
            String certificateStr = C.toString();
            X509Certificate cer = (X509Certificate) C;

            // 获取证书的 Sn 并转换为 16 进制
            BigInteger certSn = cer.getSerialNumber();

            certSnHex = certSn.toString(16);

            //当序列号少于16位时，会缺失0，要往前面补全
            while (certSnHex.length()<16) {
                int delta = 16 - certSnHex.length();
                certSnHex = "0" + certSnHex;
            }


            certDataTO.setCertSn(certSnHex);

            String certSDN = String.valueOf(cer.getSubjectDN());
//            certMap.put("certSDN",certSDN);
            certDataTO.setCertSDN(certSDN);

            Date beginDate = cer.getNotBefore();
            Date endDate = cer.getNotAfter();
//            // 将 Date 转换为 LocalDateTime
//            Instant instant1 = beginDate.toInstant();
//            Instant instant2 = endDate.toInstant();
//            ZoneId zoneId = ZoneId.systemDefault();
////
//            LocalDateTime beginTime = instant1.atZone(zoneId).toLocalDateTime();
//            LocalDateTime endTime = instant2.atZone(zoneId).toLocalDateTime();
//            certMap.put("beginDate",beginDate);
            certDataTO.setBeginDate(beginDate);
//            certMap.put("endDate",endDate);
            certDataTO.setEndDate(endDate);



        } catch (Exception e) {
            e.printStackTrace();
        }


        return certDataTO;
    }

    public static String fileToString(MultipartFile file) throws IOException {
        InputStream fileIn;//将file文件的内容提取出来为fileout
        fileIn = file.getInputStream();
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = fileIn.read(b)) != -1;) {
            out.append(new String(b,0,n));
        }
        String fileout = out.toString();


        return fileout;
    }

    public static void GetCertInfoFromCert(String certPath, Map<String,Object> certInfo) {

        // JAVA程序中显示证书指定信息
//        System.out.println("输出证书信息:");




//        Cert cert = null;
        try {
            //加入头尾
            String Header = "-----BEGIN CERTIFICATE-----\n";
            String End = "\n-----END CERTIFICATE-----";
            // 引入BC库
            Security.addProvider(new BouncyCastleProvider());
            // 使用BC解析X.509证书
            CertificateFactory CF = CertificateFactory.getInstance("X.509", "BC"); // 从证书工厂中获取X.509的单例类
            InputStream fileIn = new FileInputStream(certPath); // 将本地证书读入文件流

            //inputstream to string

            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = fileIn.read(b)) != -1;) {
                out.append(new String(b,0,n));
            }
            String file = out.toString();

            file = Header + file + End;
            //输出证书
//            System.out.println(file);
            InputStream fileout = new ByteArrayInputStream(file.getBytes());

            Certificate C = CF.generateCertificate(fileout);  // 将文件流的证书转化为证书类


            String certificateStr = C.toString();
//            System.out.println("使用[自带库函数]读入证书结果如下：");
//            System.out.print(certificateStr);
//            System.out.println("---------------chen-----------------------\n证书主要字段:");
            X509Certificate cer = (X509Certificate) C;

            String certVersion = String.valueOf(cer.getVersion());

            // 获取证书的 Sn 并转换为 16 进制
            BigInteger certSn = cer.getSerialNumber();

            String certSnHex = certSn.toString(16);

            //当序列号少于16位时，会缺失0，要往前面补全

            while (certSnHex.length()<16) {
                int delta = 16 - certSnHex.length();
                certSnHex = "0" + certSnHex;
            }

//
            String certIDN = String.valueOf(cer.getIssuerDN());
            String certSDN = String.valueOf(cer.getSubjectDN());
            String certKeyAlg = cer.getSigAlgName();
            PublicKey publicKey = cer.getPublicKey();

            String sigAlgOID = cer.getSigAlgOID();


//
            Date beginDate = cer.getNotBefore();
            Date endDate = cer.getNotAfter();
//            // 将 Date 转换为 LocalDateTime
            Instant instant1 = beginDate.toInstant();
            Instant instant2 = endDate.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
//
            LocalDateTime beginTime = instant1.atZone(zoneId).toLocalDateTime();
            LocalDateTime endTime = instant2.atZone(zoneId).toLocalDateTime();
//
//            System.out.println("版本号：" + certVersion);
//            System.out.println("序列号：" + certSn);
            System.out.println("序列号(十六进制)：" + certSnHex);
//
//            System.out.println("颁发者：" + certIDN);     // System.out.println("颁发者唯一标识符: " + cer.getSubjectUniqueID().toString());
            System.out.println("使用者：" + certSDN);
////            System.out.println("使用者唯一标识符: " + cer.getIssuerUniqueID().toString());
            System.out.println("有效期：from：" + beginTime + "  to: " + endTime);
            System.out.println("签发算法：" + certKeyAlg);
//            System.out.println("签发算法ID：" + cer.getSigAlgOID());
//            System.out.println("证书签名:" + cer.getSignature().toString());

//            byte[] sig = cer.getSigAlgParams();
//            PublicKey publicKey = cer.getPublicKey();
//            byte[] pkenc = publicKey.getEncoded();
////            System.out.println("解析出的公钥:" + Base64.getEncoder().encodeToString(pkenc));
//
//            LocalDateTime createTime = LocalDateTime.now();
//            String certTemplate = String.valueOf(certInfo.get("certTemplate"));

//            cert = new Cert(certSnHex, certSDN ,certPath, "1", beginTime, endTime, 100, certTemplate, "1", "otherCertSn", "caCertSn", "rootCertSn", "csrSource", certKeyAlg.substring(0,3),100,Base64.getEncoder().encodeToString(pkenc),null , createTime, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return cert;
        return ;
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();

        GetCertInfoFromCert("E:\\shu\\项目\\渔翁使用手册\\ra\\aaa.cer",map);

    }
}
