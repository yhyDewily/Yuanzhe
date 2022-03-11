package com.ruoyi.system.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

import com.ruoyi.system.domain.Signs;
import com.ruoyi.system.mapper.SignsMapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CerMapper;
import com.ruoyi.system.domain.Cer;
import com.ruoyi.system.service.ICerService;

/**
 * 证书管理Service业务层处理
 *
 * @author ruoyi
 * @date 2022-01-05
 */
@Service
public class CerServiceImpl implements ICerService
{
    @Autowired
    private CerMapper cerMapper;
    @Autowired
    private SignsMapper signsMapper;
    @Value("${ocspConfiguration.synUrl}")
    private String synUrl;

    /**
     * 查询证书管理
     *
     * @param version 证书管理主键
     * @return 证书管理
     */
    @Override
    public Cer selectCerByVersion(Long version)
    {
        return cerMapper.selectCerByVersion(version);
    }

    /**
     * 查询证书管理列表
     *
     * @param cer 证书管理
     * @return 证书管理
     */
    @Override
    public List<Cer> selectCerList(Cer cer)
    {
        return cerMapper.selectCerList(cer);
    }

    /**
     * 新增证书管理
     *
     * @param cer 证书管理
     * @return 结果
     */
    @Override
    public int insertCer(Cer cer)
    {
        return cerMapper.insertCer(cer);
    }

    /**
     * 修改证书管理
     *
     * @param cer 证书管理
     * @return 结果
     */
    @Override
    public int updateCer(Cer cer)
    {
        return cerMapper.updateCer(cer);
    }

    /**
     * 批量删除证书管理
     *
     * @param versions 需要删除的证书管理主键
     * @return 结果
     */
    @Override
    public int deleteCerByVersions(Long[] versions)
    {
        return cerMapper.deleteCerByVersions(versions);
    }

    /**
     * 删除证书管理信息
     *
     * @param version 证书管理主键
     * @return 结果
     */
    @Override
    public int deleteCerByVersion(Long version)
    {
        return cerMapper.deleteCerByVersion(version);
    }

    @Override
    public List<Signs> testSigns() {
        return signsMapper.selectSigns();
    }

    @Override
    public void synchronousData(){
        System.out.println("哈哈哈"+synUrl);
        // 远程数据列表
        List<Signs> signsList = signsMapper.selectSigns();

        // 读取远程数据：文件路径、状态、变动时间、注销原因
        List<String> fileUrlList = new ArrayList<>(); // 文件路径列表
        List<Date> modifyDateList = new ArrayList<>(); // 变动时间列表
        List<Integer> statusList = new ArrayList<>(); // 状态列表

        for(Signs signs:signsList){
            fileUrlList.add(signs.getCrtUrl());
            modifyDateList.add(signs.getUpdateTime());
            statusList.add(signs.getRevokeStatus());
        }

        /**
         * -----------------------------------------------------------------------------
         */
        // 删除原表
        cerMapper.deleteAll();
        // 插入新数据
        Iterator<Date> dateIterator = modifyDateList.iterator();
        Iterator<Integer> statusIterator = statusList.iterator();

        for(String url:fileUrlList){
            System.out.println("synUrl:" + synUrl);
            Cer cer = loadFileFromURL(synUrl + url);
            // set 证书中没有的数据
            // assert cer != null;
            if(cer != null){
                cer.setModifyDate(dateIterator.next());
                cer.setStatus(statusIterator.next());
                if (cerMapper.selectCerBySerialNumber(cer.getSerialNumber())!=1) {
                    cerMapper.insertCer(cer);
                }
            }
        }
    }

    @Override
    public int selectCerBySerialNumber(String serialNumber) {
        return cerMapper.selectCerBySerialNumber(serialNumber);
    }

    // 读取证书
    private Cer readCerFromFile(InputStream fileIn){
        Cer newCer = new Cer();
        try{
            // 引入BC库
            Security.addProvider(new BouncyCastleProvider());
            // 使用BC解析X.509证书
            CertificateFactory CF = CertificateFactory.getInstance("X.509", "BC"); // 从证书工厂中获取X.509的单例类
            Certificate C = CF.generateCertificate(fileIn);  // 将文件流的证书转化为证书类
            String  certificateStr = C.toString();

            X509Certificate cer = (X509Certificate)C;

            byte [] sig = cer.getSigAlgParams();
            PublicKey publicKey = cer.getPublicKey();
            byte [] pkenc = publicKey.getEncoded();

            newCer.setVersion(cer.getVersion());
            newCer.setSerialNumber(String.valueOf(cer.getSerialNumber()));
            newCer.setIssuerDn(cer.getIssuerDN().toString());
            newCer.setStartDate(cer.getNotBefore());
            newCer.setFinalDate(cer.getNotAfter());
            newCer.setModifyDate(cer.getNotBefore());
            newCer.setSubjectDn(cer.getSubjectDN().toString());
            newCer.setPublicKey(Base64.getEncoder().encodeToString(pkenc));
            newCer.setSignatureAlgorithm(cer.getSigAlgName());
            newCer.setSignature(Base64.getEncoder().encodeToString(cer.getSignature()));
            newCer.setStatus(1);
            newCer.setRevokeReason("");

        } catch(Exception e){
            e.printStackTrace();
        }

        return newCer;
    }

    /**
     * 作用：实现网络访问文件，将获取到数据储存在文件流中
     *
     * @param urlString
     *            ：访问网络的url地址
     * @return Cer
     */
    private Cer loadFileFromURL(String urlString) {
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
                return readCerFromFile(bis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 对流对象进行关闭，对Http连接对象进行关闭。以便释放资源。
                if (bis != null) {
                    bis.close();
                }
                httpConn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
