package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.KmsSecretKey;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:25
 */
public interface KmsSecretKeyMapper {

    public KmsSecretKey selectKeyById(int id);

    public List<KmsSecretKey> selectKeyList(KmsSecretKey kmsSecretKey);

    public int insertKey(KmsSecretKey kmsSecretKey);

    public int deleteKeyById(int id);

    public int checkEqual(KmsSecretKey kmsSecretKey);

    public int updateKey(KmsSecretKey kmsSecretKey);
}
