package com.ruoyi.system.controller;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.cert.ocsp.jcajce.JcaBasicOCSPRespBuilder;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

import static com.ruoyi.system.utils.CertUtil.getCert;
import static com.ruoyi.system.utils.CertUtil.readPrivateKeySecondApproach;
import static com.ruoyi.system.utils.OCSPUtil.makeOcspResponse;

/**
 * @author Dewily
 * @date 2022-02-23 14:10
 */

@RestController
@RequestMapping("/ocsp")
public class ocspController {

    @Autowired
    private Environment env;

    @Value("${ocspConfiguration.CAPath}")
    private String CAPath;

    @Value("${ocspConfiguration.keyPath}")
    private String keyPath;

    @PostMapping(value = "/check_by_serial_number")
    @ResponseBody
    public byte[] checkById(int serialNumber) {
        /**
         * Todo
         * 通过序列号从数据库orCRL列表检测证书是否有效
         */
        return null;
    }

    @PostMapping(value = "/check_by_request")
    @ResponseBody
    public byte[] checkByRequest(@RequestBody byte[] ocsp_request) {
        OCSPResp ocspResp = null;
        try {
            System.out.println("收到ocsp请求");
            OCSPReq ocspReq = new OCSPReq(ocsp_request);
            ocspResp = makeOcspResponse(getCert(CAPath), readPrivateKeySecondApproach(keyPath), ocspReq);
            System.out.println("返回ocsp响应");
            assert ocspResp != null;
            return ocspResp.getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/fuck")
    public String fuck() {
        return "fuck: " + CAPath;

    }
}
