package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.KmsSecretKey;
import com.ruoyi.system.mapper.KmsSecretKeyMapper;
import com.ruoyi.system.service.IKmsSecretKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:31
 */
@Service
public class KmsSecretKeyServiceImpl implements IKmsSecretKeyService {

    @Autowired
    private KmsSecretKeyMapper kmsSecretKeyMapper;

    @Override
    public KmsSecretKey selectKeyById(int id) {
        return kmsSecretKeyMapper.selectKeyById(id);
    }

    @Override
    public List<KmsSecretKey> selectKeyList(KmsSecretKey kmsSecretKey) {
        return kmsSecretKeyMapper.selectKeyList(kmsSecretKey);
    }

    @Override
    public int insertKey(KmsSecretKey kmsSecretKey) {
        if (!checkExist(kmsSecretKey)) return 0;
        return kmsSecretKeyMapper.insertKey(kmsSecretKey);
    }

    @Override
    public int deleteKeyById(int id) {
        return kmsSecretKeyMapper.deleteKeyById(id);
    }

    @Override
    public boolean checkExist(KmsSecretKey kmsSecretKey) {
        return kmsSecretKeyMapper.checkEqual(kmsSecretKey) == 0;
    }

    @Override
    public int updateKey(KmsSecretKey kmsSecretKey) {
        if (this.checkExist(kmsSecretKey)) return 0;
        return kmsSecretKeyMapper.updateKey(kmsSecretKey);
    }
}
