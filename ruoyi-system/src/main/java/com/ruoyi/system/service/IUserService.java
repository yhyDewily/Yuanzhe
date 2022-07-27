package com.ruoyi.system.service;

import com.ruoyi.system.domain.User;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:30
 */
public interface IUserService {

    public User selectUserById(int id);

    public List<User> selectUserList(User user);

    public int insertUser(User user);

    public int deleteUserById(int id);

    public boolean checkExist(User user);

    public int updateUser(User user);
}
