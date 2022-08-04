package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.KmsKeyCount;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:25
 */
public interface KmsKeyCountMapper {

    public KmsKeyCount selectKeyCountById(int id);

    public List<KmsKeyCount> selectKeyCountList(KmsKeyCount kmsKeyCount);

    public int insertKeyCount(KmsKeyCount kmsKeyCount);

    public int deleteKeyCountById(int id);

    public int checkEqual(KmsKeyCount kmsKeyCount);

    public int updateKeyCount(KmsKeyCount kmsKeyCount);
}
