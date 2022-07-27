package com.ruoyi.system.service;

import com.ruoyi.system.domain.KeyCount;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:30
 */
public interface IKeyCountService {

    public KeyCount selectKeyCountById(int id);

    public List<KeyCount> selectKeyCountList(KeyCount keyCount);

    public int insertKeyCount(KeyCount keyCount);

    public int deleteKeyCountById(int id);

    public boolean checkExist(KeyCount keyCount);

    public int updateKeyCount(KeyCount keyCount);
}
