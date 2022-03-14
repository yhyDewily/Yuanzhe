package com.ruoyi.system.utils;

import com.ruoyi.system.mapper.SignsMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.cert.ocsp.jcajce.JcaBasicOCSPRespBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

import static com.ruoyi.system.utils.CertUtil.getCert;
import static com.ruoyi.system.utils.CertUtil.getIssuerCert;

/**
 * @author Dewily
 * @date 2022-03-06 18:36
 */
public class OCSPUtil {



    /**
     * 根据传入的证书获取ocsp url
     * @param cert 证书
     * @return url字符串
     * @throws CertificateEncodingException
     */
    public static String getOCSPUrl(X509Certificate cert) throws CertificateEncodingException {
        //获取ocsp的url
        //1.将证书转为tbs
        TBSCertificate tbs = TBSCertificate.getInstance(cert.getTBSCertificate());
        //2.根据tbs获取extension
        Extensions extension = tbs.getExtensions();
        //3.从extension中获取授权信息
        AuthorityInformationAccess access = AuthorityInformationAccess.fromExtensions(extension);
        //4.从授权信息中拿到ocsp的url
        String ocspUrl = null;
        AccessDescription[] descriptions = access.getAccessDescriptions();
        for (AccessDescription description : descriptions) {
            if (description.getAccessMethod().getId().equalsIgnoreCase(AccessDescription.id_ad_ocsp.getId())) {
                ocspUrl = description.getAccessLocation().getName().toString();
            }
        }
        return ocspUrl;
    }

    /**
     * 生成OCSPResponse
     * @param caCert
     * @param caPrivateKey
     * @param ocspReq
     * @return
     */
    public static OCSPResp makeOcspResponse(X509Certificate caCert, PrivateKey caPrivateKey, OCSPReq ocspReq, CertificateStatus status) {
        DigestCalculatorProvider digCalProv = null;
        try {
            digCalProv = new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();
            BasicOCSPRespBuilder respGen = new JcaBasicOCSPRespBuilder(
                    caCert.getPublicKey(), digCalProv.get(RespID.HASH_SHA1)
            );
            CertificateID certID = ocspReq.getRequestList()[0].getCertID();
            respGen.addResponse(certID,status);
            //添加nonce防止replay攻击
            Extension ext = new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false,
                    ocspReq.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce).getExtnValue());
            respGen.setResponseExtensions(new Extensions(new Extension[]{ext}));
            //JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA384withECDSA").setProvider("BC");
            ContentSigner signer = new JcaContentSignerBuilder(caCert.getSigAlgName()).setProvider("BC").build(caPrivateKey);
                    BasicOCSPResp resp = respGen.build(
                    signer,
                    new X509CertificateHolder[]{new JcaX509CertificateHolder(caCert)},
                    new Date()
            );
            OCSPRespBuilder rGen = new OCSPRespBuilder();
            return rGen.build(OCSPRespBuilder.SUCCESSFUL, resp);
        } catch (OperatorCreationException | OCSPException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建OCSP request请求
     * @param nextCert 需要校验的证书
     * @param nextIssuer 发行者的根证书
     * @return ocsp请求
     * @throws OperatorCreationException
     * @throws CertificateEncodingException
     * @throws IOException
     * @throws OCSPException
     */
    public static OCSPReq GenOcspReq(X509Certificate nextCert,
                                     X509Certificate nextIssuer) throws OperatorCreationException, CertificateEncodingException, OCSPException, IOException {
        OCSPReqBuilder ocspReqBuilder = new OCSPReqBuilder();
        DigestCalculatorProvider digCalcProv = new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();
        CertificateID certId = new CertificateID(
                (new BcDigestCalculatorProvider()).get(CertificateID.HASH_SHA1),
                new X509CertificateHolder(nextIssuer.getEncoded()),
                nextCert.getSerialNumber());
        ocspReqBuilder.addRequest(certId);
        BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
        Extension ext = new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false,
                new DEROctetString(nonce.toByteArray()));
        ocspReqBuilder.setRequestExtensions(new Extensions(new Extension[]{ext}));
        return ocspReqBuilder.build();
    }

    /**
     * 检查证书是否撤销
     * @param certificate 需要验证的证书
     * @return
     * 0   证书状态良好
     * -1  证书已撤销
     * 1   证书状况未知
     */
    public static Integer checkCertStatus(X509Certificate certificate) {
        int resultNum = -2;
        X509Certificate issuer = getCert("src\\main\\resources\\cert\\midCA-digicert-der.cer");
        try {
            OCSPReq ocspReq = GenOcspReq(certificate, getIssuerCert(certificate));
            OCSPResp ocspResp = requestOCSPResponse("http://127.0.0.1:8001/payment/ocsp", ocspReq);
//            OCSPResp ocspResp = getOcspResponse(ocspReq);
            if (OCSPResp.SUCCESSFUL == ocspResp.getStatus()) {
                BasicOCSPResp basic = (BasicOCSPResp) ocspResp.getResponseObject();
                SingleResp[] resps = basic.getResponses();
                if (resps !=null && resps.length==1) {
                    SingleResp resp = resps[0];
                    CertificateStatus certStatus = resp.getCertStatus();
                    if (certStatus == CertificateStatus.GOOD) {
                        resultNum = 0;
                    } else {
                        if (certStatus instanceof RevokedStatus) {
                            resultNum = -1;
                        } else if (certStatus instanceof UnknownStatus) {
                            resultNum = 1;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultNum;
    }

    /**
     * 发送ocsp请求
     * @param url ocsp url
     * @param ocspReq 封装好的ocsp请求
     * @return ocsp response
     * @throws IOException
     */
    public static OCSPResp requestOCSPResponse(String url, OCSPReq ocspReq) throws IOException {
        byte[] ocspReqData = ocspReq.getEncoded();
        //Http请求发送
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(ocspReqData));
        httpPost.setHeader("content-type", "application/octet-stream;charset=UTF-8");

        HttpResponse response = closeableHttpClient.execute(httpPost);
        byte[] res_data = IOUtils.toByteArray(response.getEntity().getContent());
        return new OCSPResp(res_data);
    }


}
