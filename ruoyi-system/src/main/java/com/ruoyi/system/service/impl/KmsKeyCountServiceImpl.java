package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.KmsKeyCount;
import com.ruoyi.system.mapper.KmsKeyCountMapper;
import com.ruoyi.system.service.IKmsKeyCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:31
 */
@Service
public class KmsKeyCountServiceImpl implements IKmsKeyCountService {

    @Autowired
    private KmsKeyCountMapper kmsKeyCountMapper;

    @Override
    public KmsKeyCount selectKeyCountById(int id) {
        return kmsKeyCountMapper.selectKeyCountById(id);
    }

    @Override
    public List<KmsKeyCount> selectKeyCountList(KmsKeyCount kmsKeyCount) {
        return kmsKeyCountMapper.selectKeyCountList(kmsKeyCount);
    }

    @Override
    public int insertKeyCount(KmsKeyCount kmsKeyCount) {
        if (!checkExist(kmsKeyCount)) return 0;
        return kmsKeyCountMapper.insertKeyCount(kmsKeyCount);
    }

    @Override
    public int deleteKeyCountById(int id) {
        return kmsKeyCountMapper.deleteKeyCountById(id);
    }

    @Override
    public boolean checkExist(KmsKeyCount kmsKeyCount) {
        return kmsKeyCountMapper.checkEqual(kmsKeyCount) == 0;
    }

    @Override
    public int updateKeyCount(KmsKeyCount kmsKeyCount) {
        if (this.checkExist(kmsKeyCount)) return 0;
        return kmsKeyCountMapper.updateKeyCount(kmsKeyCount);
    }


}
