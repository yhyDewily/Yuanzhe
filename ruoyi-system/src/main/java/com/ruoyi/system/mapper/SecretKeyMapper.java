package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SecretKey;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:25
 */
public interface SecretKeyMapper {

    public SecretKey selectKeyById(int id);

    public List<SecretKey> selectKeyList(SecretKey secretKey);

    public int insertKey(SecretKey secretKey);

    public int deleteKeyById(int id);

    public int checkEqual(SecretKey secretKey);

    public int updateKey(SecretKey secretKey);
}
