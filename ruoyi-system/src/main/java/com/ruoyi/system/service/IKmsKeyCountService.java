package com.ruoyi.system.service;

import com.ruoyi.system.domain.KmsKeyCount;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:30
 */
public interface IKmsKeyCountService {

    public KmsKeyCount selectKeyCountById(int id);

    public List<KmsKeyCount> selectKeyCountList(KmsKeyCount kmsKeyCount);

    public int insertKeyCount(KmsKeyCount kmsKeyCount);

    public int deleteKeyCountById(int id);

    public boolean checkExist(KmsKeyCount kmsKeyCount);

    public int updateKeyCount(KmsKeyCount kmsKeyCount);
}
