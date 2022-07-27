package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.KeyCount;
import com.ruoyi.system.mapper.KeyCountMapper;
import com.ruoyi.system.service.IKeyCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:31
 */
@Service
public class KeyCountServiceImpl implements IKeyCountService {

    @Autowired
    private KeyCountMapper keyCountMapper;

    @Override
    public KeyCount selectKeyCountById(int id) {
        return keyCountMapper.selectKeyCountById(id);
    }

    @Override
    public List<KeyCount> selectKeyCountList(KeyCount keyCount) {
        return keyCountMapper.selectKeyCountList(keyCount);
    }

    @Override
    public int insertKeyCount(KeyCount keyCount) {
        if (!checkExist(keyCount)) return 0;
        return keyCountMapper.insertKeyCount(keyCount);
    }

    @Override
    public int deleteKeyCountById(int id) {
        return keyCountMapper.deleteKeyCountById(id);
    }

    @Override
    public boolean checkExist(KeyCount keyCount) {
        return keyCountMapper.checkEqual(keyCount) == 0;
    }

    @Override
    public int updateKeyCount(KeyCount keyCount) {
        if (this.checkExist(keyCount)) return 0;
        return keyCountMapper.updateKeyCount(keyCount);
    }


}
