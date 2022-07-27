package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Auth;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:25
 */
public interface AuthMapper {

    public Auth selectAuthById(int id);

    public List<Auth> selectAuthList(Auth auth);

    public int insertAuth(Auth auth);

    public int deleteAuthById(int id);

    public int checkEqual(Auth auth);

    public int updateAuth(Auth auth);
}
