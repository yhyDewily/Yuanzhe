package com.ruoyi.system.service;

import com.ruoyi.system.domain.SecretKey;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.utils.SM2KeyPair;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 密钥表 服务类
 * </p>
 *
 * @author lls
 * @since 2022-08-19
 */
public interface SecretKeyService extends IService<SecretKey> {

    /**
     * 通过SM2算法生成密钥
     * @param type 密钥类型，目前不填，为SM2类型
     * @param keyName 密钥名称
     * @return
     */
    Map<String,String> applyKeyPair(String type, String keyName);
}
