package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Signs;
import com.ruoyi.system.mapper.SignsMapper;
import com.ruoyi.system.service.IOcspService;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
            ocspResp = makeOcspResponse(getCert(CAPath), readPrivateKeySecondApproach(keyPath), ocspReq);
            assert ocspResp != null;
            return ocspResp.getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer checkBySerialNumber(String serial_number) {
        return signsMapper.selectRevokeStatusBySerialNumberAfter(serial_number);
    }
}
