package com.ruoyi.system.service;

import com.ruoyi.system.domain.SecretKey;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:30
 */
public interface ISecretKeyService {

    public SecretKey selectKeyById(int id);

    public List<SecretKey> selectKeyList(SecretKey secretKey);

    public int insertKey(SecretKey secretKey);

    public int deleteKeyById(int id);

    public boolean checkExist(SecretKey secretKey);

    public int updateKey(SecretKey secretKey);
}
