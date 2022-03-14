package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Cer;
import com.ruoyi.system.mapper.SignsMapper;
import com.ruoyi.system.service.IOcspService;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

import static com.ruoyi.system.utils.CertUtil.getCert;
import static com.ruoyi.system.utils.CertUtil.readPrivateKeySecondApproach;
import static com.ruoyi.system.utils.OCSPUtil.makeOcspResponse;

/**
 * @author Dewily
 * @date 2022-03-09 19:28
 */
@Service
public class OCSPServiceImpl implements IOcspService {

    @Autowired
    SignsMapper signsMapper;

    @Value("${ocspConfiguration.CAPath}")
    private String CAPath;

    @Value("${ocspConfiguration.keyPath}")
    private String keyPath;

    @Override
    public byte[] checkByRequest(byte[] ocsp_request) {
        OCSPResp ocspResp = null;
        try {
            OCSPReq ocspReq = new OCSPReq(ocsp_request);
            CertificateStatus status = check_status(ocspReq);
            ocspResp = makeOcspResponse(getCert(CAPath), readPrivateKeySecondApproach(keyPath), ocspReq, status);
            assert ocspResp != null;
            return ocspResp.getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从数据库中确认证书状态
     * @param ocspReq
     * @return CertificateStatus | RevokedStatus | UnknownStatus
     */
    public CertificateStatus check_status(OCSPReq ocspReq) {
        String serial_number = String.valueOf(ocspReq.getRequestList()[0].getCertID().getSerialNumber());
        int status = signsMapper.selectRevokeStatusBySerialNumber(serial_number);
        if (status == 1) return CertificateStatus.GOOD;
        else if (status == 2) return new RevokedStatus(new Date(), 2);
        else return new UnknownStatus();
    }

    @Override
    public Integer checkBySerialNumber(String serial_number) {
        return signsMapper.selectRevokeStatusBySerialNumber(serial_number);
    }
}
