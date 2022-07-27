package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.User;

import java.util.List;

/**
 * @useror Dewily
 * @date 2022-07-13 22:26
 */
public interface UserMapper {

    public User selectUserById(int id);

    public List<User> selectUserList(User user);

    public int insertUser(User user);

    public int deleteUserById(int id);

    public int checkEqual(User user);

    public int updateUser(User user);
}
