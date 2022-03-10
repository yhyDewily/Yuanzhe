package com.ruoyi.system.service;

import org.bouncycastle.cert.ocsp.OCSPResp;
import org.springframework.stereotype.Service;

/**
 * @author Dewily
 * @date 2022-03-09 18:26
 */

public interface IOcspService {

    byte[] checkByRequest(byte[] ocsp_request);

    Integer checkBySerialNumber(String serial_number);
}
