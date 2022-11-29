package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.CipherEnvelop;

/**
 *
 * @author bestFang666
 * @date 2022/11/1 16:07
 */
public interface ISmService {
    public String encryptWithSM4CBCPadding(CipherEnvelop cipherEnvelop);
    public String decryptWithSM4CBCPadding(CipherEnvelop cipherEnvelop);
    public String abstarctSM3(CipherEnvelop cipherEnvelop);
}
