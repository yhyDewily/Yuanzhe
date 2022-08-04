package com.ruoyi.system.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ruoyi.common.utils.SFTPChannel;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static com.ruoyi.common.utils.BCECUtil.convertSEC1ToBCECPrivateKey;


/**
 * @Author: ruoyi
 * @Date: 2022/3/9
 * @Time: 11:17
 * @Description:
 */
@Service
public class revokeCerImpl implements IrevokeCerService {

    @Autowired
    private RevokeCerMapper cerMapper;

    @Autowired
    private Environment env;

    @Override
    public List<RevokeCer> selectRevokeCerList() {
        return cerMapper.selectRevokeCerList();
    }

    //产生CRL证书
    @Override
    public void GeCRL(int days) throws CertificateException, IOException {
        Timer timer = new Timer();
        TimerTask task =new TimerTask() {
            @Override
            public void run() {
                X509Certificate TemCer = null;
                List<RevokeCer> revokeCers = cerMapper.selectRevokeCerList();
                List<X509Certificate> CersList = new ArrayList<>(); // 文件路径列表
                List<Integer> reasonList = new ArrayList<>(); // 状态列表
                for (RevokeCer cer : revokeCers) {
                    TemCer = loadFileFromURLToCer("http://192.168.8.202:4130/cipher/public/" + cer.getCrtUrl());
                    CersList.add(TemCer); //传入的撤销证书集合
                    reasonList.add(cer.getRevokeReason()); //传入的证书撤销原因集合
                }
                //产生证书吊销列表CRL时传入的第一个参数：CA证书
                // X509Certificate CaCer = loadFileFromURLToCer("http://192.168.8.202:4130/cipher/public/" + "/signlist/test01/test.mid.ca.certSm2.crt");
                X509Certificate CaCer = null;
                try {
                    CaCer = getCacer(env.getProperty("CAConfig.CAPath"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }

                //产生证书吊销列表CRL时传入的第二个参数：CaPrivate密钥
                // PrivateKey CaPrivateKey = loadFileFromURLToKey("http://192.168.8.202:4130/cipher/public/"+ "./signlist/test01/test.mid.ca.pri");
                PrivateKey CaPrivateKey = null;
                try {
                    CaPrivateKey = getCaPrivateKey(env.getProperty("CAConfig.CAPrivate"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    try {
                        generateCRL(CaCer,CaPrivateKey,CersList,reasonList, days);  //产生证书 ,并上传远程服务器
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (CRLException e) {
                    e.printStackTrace();
                } catch (OperatorCreationException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task,0,86400000*days);  //重复循环更新,间隔days天
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
        writeFile(env.getProperty("ocspConfiguration.CRLPath"), crl.getEncoded());

        //将上面writeFile路径产生的ALLCRL.crl文件上传到远程服务器指定的目录中
        try {
            RemoteUplod(env.getProperty("ocspConfiguration.CRLPath"));
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

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

    //将产生的CRL文件上传到远程服务器指定目录
    public void RemoteUplod(String FilePathString) throws JSchException, SftpException {
        //远程服务器：Ip、Root、Password、[Port(默认是22) 可选参数]
        SFTPChannel sftpChannel = new SFTPChannel(env.getProperty("CRLConfiguratin.crlIP"),
                env.getProperty("CRLConfiguratin.crlROOT"), env.getProperty("CRLConfiguratin.crlPASSWORD"),
                Integer.parseInt(env.getProperty("CRLConfiguratin.crlPORT")));
       // SFTPChannel sftpChannel = new SFTPChannel("192.168.8.169", "root", "197154",222);
        //直接将本地文件名为src的文件上传到目标服务器，目标文件名为dst。
        //（注：使用这个方法时，dst可以是目录，当dst是目录时，上传后的目标文件名将与src文件名相同）
        String src = FilePathString;
        //  String dst = "/opt/csv/";
        String dst = "/var/www/html/cipher/public/revokelist/";
        ChannelSftp channelSftp = sftpChannel.getChannel(sftpChannel);
        System.out.println("创建链接");
        channelSftp.put(src, dst, ChannelSftp.OVERWRITE);
        System.out.println("上传文件成功");
        //展示上传文件目录下的所有文件
        Vector vector = channelSftp.ls(dst);
        System.out.println(vector.toString());
        //关闭连接
        channelSftp.quit();
        sftpChannel.closeChannel();
    }


    //获取CAPrivateKey
    public static PrivateKey getCaPrivateKey(String filepath) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, CertificateException {
        File file = new File(filepath);
        try (FileInputStream fileStream = new FileInputStream(file);
             DataInputStream dataStream = new DataInputStream(fileStream)) {
            byte[] keyBytes = new byte[(int) file.length()];
            dataStream.readFully(keyBytes);
            String temp = new String(keyBytes);
            int start = temp.indexOf("-----BEGIN EC PRIVATE KEY-----");
            int end = temp.indexOf("-----END EC PRIVATE KEY-----");
            String sub = temp.substring(start, end).replace("-----BEGIN EC PRIVATE KEY-----", "").trim();
            byte[] decoded = Base64.getMimeDecoder().decode(sub);
            return convertSEC1ToBCECPrivateKey(decoded);
        }
    }

    //获取CA证书
    public static X509Certificate getCacer(String filepath) throws FileNotFoundException, CertificateException, NoSuchProviderException {
        FileInputStream certIn = new FileInputStream(filepath);
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        X509Certificate cer=(X509Certificate) cf.generateCertificate(certIn);
        return cer;
    }



}

