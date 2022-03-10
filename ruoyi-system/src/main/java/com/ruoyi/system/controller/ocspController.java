package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IOcspService;
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

    @Autowired
    private IOcspService ocspService;

    @PostMapping(value = "/check_by_serial_number")
    @ResponseBody
    public AjaxResult checkById(String serialNumber) {
        /**
         * Todo
         * 通过序列号从数据库orCRL列表检测证书是否有效
         */
        Integer result = ocspService.checkBySerialNumber(serialNumber);
        return AjaxResult.success(result);
    }

    @PostMapping(value = "/check_by_request")
    @ResponseBody
    public byte[] checkByRequest(@RequestBody byte[] ocsp_request) {
        return ocspService.checkByRequest(ocsp_request);
    }
}
