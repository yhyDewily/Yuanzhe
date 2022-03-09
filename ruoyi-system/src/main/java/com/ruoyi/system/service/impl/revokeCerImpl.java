package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.RevokeCer;
import com.ruoyi.system.mapper.RevokeCerMapper;
import com.ruoyi.system.service.IrevokeCerService;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ruoyi.common.utils.BCECUtil.convertSEC1ToBCECPrivateKey;


/**
 * @Author: min
 * @Date: 2022/3/9
 * @Time: 11:17
 * @Description:
 */
@Service
public class revokeCerImpl implements IrevokeCerService {

    @Autowired
    private RevokeCerMapper cerMapper;

    @Override
    public List<RevokeCer> selectRevokeCerList() {
        return cerMapper.selectRevokeCerList();
    }

   //产生CRL证书
    @Override
    public void GeCRL(int days) throws CertificateException, IOException {
        X509Certificate TemCer = null;
        List<RevokeCer> revokeCers = cerMapper.selectRevokeCerList();
        System.out.println("qqqqqqqqqqqqq:"+revokeCers.size());
        List<X509Certificate> CersList = new ArrayList<>(); // 文件路径列表
        List<Integer> reasonList = new ArrayList<>(); // 状态列表
        for (RevokeCer cer : revokeCers) {
            TemCer = loadFileFromURLToCer("http://192.168.8.202:4130/cipher/public/" + cer.getCrtUrl());
            CersList.add(TemCer); //传入的撤销证书集合
            reasonList.add(cer.getRevokeReason()); //传入的证书撤销原因集合
        }
        //产生证书吊销列表CRL时传入的第一个参数：CA证书
        X509Certificate CaCer = loadFileFromURLToCer("http://192.168.8.202:4130/cipher/public/" + "/signlist/test01/test.mid.ca.certSm2.crt");
        //产生证书吊销列表CRL时传入的第二个参数：CaPrivate密钥
        PrivateKey CaPrivateKey = loadFileFromURLToKey("http://192.168.8.202:4130/cipher/public/"+ "./signlist/test01/test.mid.ca.pri");
        try {
            generateCRL(CaCer,CaPrivateKey,CersList,reasonList, days);  //产生证书
        } catch (CRLException e) {
            e.printStackTrace();
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }
    }


    //将访问的url页面中的内容转换为X509Certificate类型证书
    private static X509Certificate loadFileFromURLToCer(String urlString) {
        BufferedInputStream bis = null;
        HttpURLConnection httpConn = null;
        X509Certificate reCer = null;
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
                // 引入BC库
                Security.addProvider(new BouncyCastleProvider());
                // 使用BC解析X.509证书
                CertificateFactory CF = CertificateFactory.getInstance("X.509", "BC"); // 从证书工厂中获取X.509的单例类
                reCer = (X509Certificate) CF.generateCertificate(bis);  // 将文件流的证书转化为证书类
                return reCer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 对流对象进行关闭，对Http连接对象进行关闭。以便释放资源。
                assert bis != null;
                bis.close();
                httpConn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //将访问的url页面中的内容转换为PrivateKey类型密钥
    private static PrivateKey loadFileFromURLToKey(String urlString) {
        BufferedInputStream bis = null;
        HttpURLConnection httpConn = null;
        PrivateKey caPrivateKey = null;
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
                byte[] keyBytes = getBytes(bis);
                //将SEC1标准的私钥字节流转为BCECPrivateKey对象
                caPrivateKey = convertSEC1ToBCECPrivateKey(keyBytes);
                return caPrivateKey;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 对流对象进行关闭，对Http连接对象进行关闭。以便释放资源。
                assert bis != null;
                bis.close();
                httpConn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //文件流转字节流(产生PrivateKey时)
    private static byte[] getBytes(InputStream fis) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    //产生CRL证书撤销列表
    public void generateCRL(X509Certificate caCertificate, PrivateKey caPrivateKey, List<X509Certificate> revokecers, List<Integer> revoke_reasons,int days) throws IOException, CRLException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
        // 创建CRL需要用到有CRL权限的 CA 机构私钥和证书,准备好创建 CRL 所需的私钥和证书
        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder((new X500Name(caCertificate.getSubjectDN().getName())), new Date());
        crlBuilder.setNextUpdate(new Date(System.currentTimeMillis() + 86400 * 1000*days)); // 7  天有效期
        for (int i = 0, size = revokecers.size(); i < size; i++) {
            crlBuilder.addCRLEntry(revokecers.get(i).getSerialNumber(), new Date(), revoke_reasons.get(i)); ///*被撤销证书序列号、被撤销时间、被撤销原因CRLReason.privilegeWithdrawn*/
        }

        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithECDSA"); //签名
        contentSignerBuilder.setProvider("BC");
        X509CRLHolder crlHolder = crlBuilder.build(contentSignerBuilder.build(caPrivateKey));
        JcaX509CRLConverter converter = new JcaX509CRLConverter();
        converter.setProvider("BC");
        X509CRL crl = converter.getCRL(crlHolder);
        //保存CRL文件
        writeFile("D:\\AllCRL.crl", crl.getEncoded());

    }

    //保存文件到某个路径
    public void writeFile(String filePath, byte[] data) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "rw");
            raf.write(data);
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }


}

