package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SecretKey;
import com.ruoyi.system.mapper.SecretKeyMapper;
import com.ruoyi.system.service.ISecretKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:31
 */
@Service
public class SecretKeyServiceImpl implements ISecretKeyService {

    @Autowired
    private SecretKeyMapper secretKeyMapper;

    @Override
    public SecretKey selectKeyById(int id) {
        return secretKeyMapper.selectKeyById(id);
    }

    @Override
    public List<SecretKey> selectKeyList(SecretKey secretKey) {
        return secretKeyMapper.selectKeyList(secretKey);
    }

    @Override
    public int insertKey(SecretKey secretKey) {
        if (!checkExist(secretKey)) return 0;
        return secretKeyMapper.insertKey(secretKey);
    }

    @Override
    public int deleteKeyById(int id) {
        return secretKeyMapper.deleteKeyById(id);
    }

    @Override
    public boolean checkExist(SecretKey secretKey) {
        return secretKeyMapper.checkEqual(secretKey) == 0;
    }

    @Override
    public int updateKey(SecretKey secretKey) {
        if (this.checkExist(secretKey)) return 0;
        return secretKeyMapper.updateKey(secretKey);
    }
}
