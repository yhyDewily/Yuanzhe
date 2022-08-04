package com.ruoyi.system.service;

import com.ruoyi.system.domain.KmsSecretKey;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:30
 */
public interface IKmsSecretKeyService {

    public KmsSecretKey selectKeyById(int id);

    public List<KmsSecretKey> selectKeyList(KmsSecretKey kmsSecretKey);

    public int insertKey(KmsSecretKey kmsSecretKey);

    public int deleteKeyById(int id);

    public boolean checkExist(KmsSecretKey kmsSecretKey);

    public int updateKey(KmsSecretKey kmsSecretKey);
}
