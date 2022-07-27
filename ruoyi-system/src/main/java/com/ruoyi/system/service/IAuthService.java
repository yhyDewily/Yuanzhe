package com.ruoyi.system.service;

import com.ruoyi.system.domain.Auth;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:29
 */
public interface IAuthService {

    /**
     * 根据id查找权限
     * @param id 权限id
     * @return 权限信息
     */
    public Auth selectAuthById(int id);

    /**
     * 获取权限信息列表
     * @param auth 权限信息
     * @return 权限信息列表
     */
    public List<Auth> selectAuthList(Auth auth);

    /**
     * 插入权限
     * @param auth 插入的权限信息
     * @return 插入结果
     */
    public int insertAuth(Auth auth);

    /**
     * 根据id删除权限信息
     * @param id 权限id
     * @return 删除结果
     */
    public int deleteAuthById(int id);

    /**
     * 检查是否有重复权限信息存在
     * @param auth 权限信息
     * @return 检查结果
     */
    public boolean checkExist(Auth auth);

    /**
     * 更新权限相关信息
     * @param auth 权限信息
     * @return 更新结果
     */
    public int updateAuth(Auth auth);

    /**
     * 添加一个新的角色权限
     * @param authName
     * @param authList
     * @return
     */
    public boolean addAuth(String authName, List<Integer>authList);
}
