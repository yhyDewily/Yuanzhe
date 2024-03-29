package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysRoleDatascope;

/**
 * 系统角色所拥有下属角色创建权限Mapper接口
 *
 * @author ruoyi
 * @date 2022-09-06
 */
public interface SysRoleDatascopeMapper
{
    public List<SysRoleDatascope> selectAllByRoleId(Long roleId);

    /**
     * 查询系统角色所拥有下属角色创建权限
     *
     * @param roleId 系统角色所拥有下属角色创建权限主键
     * @return 系统角色所拥有下属角色创建权限
     */
    public SysRoleDatascope selectSysRoleDatascopeByRoleId(Long roleId, Long subRoleId);

    /**
     * 查询系统角色所拥有下属角色创建权限列表
     *
     * @param sysRoleDatascope 系统角色所拥有下属角色创建权限
     * @return 系统角色所拥有下属角色创建权限集合
     */
    public List<SysRoleDatascope> selectSysRoleDatascopeList(SysRoleDatascope sysRoleDatascope);

    /**
     * 新增系统角色所拥有下属角色创建权限
     *
     * @param sysRoleDatascope 系统角色所拥有下属角色创建权限
     * @return 结果
     */
    public int insertSysRoleDatascope(SysRoleDatascope sysRoleDatascope);

    /**
     * 修改系统角色所拥有下属角色创建权限
     *
     * @param sysRoleDatascope 系统角色所拥有下属角色创建权限
     * @return 结果
     */
    public int updateSysRoleDatascope(SysRoleDatascope sysRoleDatascope);

    /**
     * 删除系统角色所拥有下属角色创建权限
     *
     * @param roleId 系统角色所拥有下属角色创建权限主键
     * @return 结果
     */
    public int deleteSysRoleDatascopeByRoleId(Long roleId);

    /**
     * 批量删除系统角色所拥有下属角色创建权限
     *
     * @param roleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysRoleDatascopeByRoleIds(Long[] roleIds);
}
