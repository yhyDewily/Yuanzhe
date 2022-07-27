package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Auth;
import com.ruoyi.system.domain.KeyCount;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:25
 */
public interface KeyCountMapper {

    public KeyCount selectKeyCountById(int id);

    public List<KeyCount> selectKeyCountList(KeyCount keyCount);

    public int insertKeyCount(KeyCount keyCount);

    public int deleteKeyCountById(int id);

    public int checkEqual(KeyCount keyCount);

    public int updateKeyCount(KeyCount keyCount);
}
