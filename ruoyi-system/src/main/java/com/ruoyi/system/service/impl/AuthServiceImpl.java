package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Auth;
import com.ruoyi.system.mapper.AuthMapper;
import com.ruoyi.system.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:31
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public Auth selectAuthById(int id) {
        return authMapper.selectAuthById(id);
    }

    @Override
    public List<Auth> selectAuthList(Auth auth) {
        return authMapper.selectAuthList(auth);
    }

    @Override
    public int insertAuth(Auth auth) {
        if (!checkExist(auth)) return 0;
        return authMapper.insertAuth(auth);
    }

    @Override
    public int deleteAuthById(int id) {
        return authMapper.deleteAuthById(id);
    }

    @Override
    public boolean checkExist(Auth auth) {
        return authMapper.checkEqual(auth) == 0;
    }

    @Override
    public int updateAuth(Auth auth) {
        if (this.checkExist(auth)) return 0;
        return authMapper.updateAuth(auth);
    }

    @Override
    public boolean addAuth(String authName, List<Integer> authList) {
        return false;
    }
}
