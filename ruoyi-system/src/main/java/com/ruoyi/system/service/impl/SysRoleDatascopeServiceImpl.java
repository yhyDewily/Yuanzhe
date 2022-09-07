package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysRoleDatascopeMapper;
import com.ruoyi.system.domain.SysRoleDatascope;
import com.ruoyi.system.service.ISysRoleDatascopeService;

/**
 * 系统角色所拥有下属角色创建权限Service业务层处理
 *
 * @author ruoyi
 * @date 2022-09-06
 */
@Service
public class SysRoleDatascopeServiceImpl implements ISysRoleDatascopeService
{
    @Autowired
    private SysRoleDatascopeMapper sysRoleDatascopeMapper;

    /**
     * 查询系统角色所拥有下属角色创建权限
     *
     * @param roleId 系统角色所拥有下属角色创建权限主键
     * @return 系统角色所拥有下属角色创建权限
     */
    @Override
    public SysRoleDatascope selectSysRoleDatascopeByRoleId(Long roleId, Long subRoleId)
    {
        return sysRoleDatascopeMapper.selectSysRoleDatascopeByRoleId(roleId, subRoleId);
    }

    /**
     * 查询系统角色所拥有下属角色创建权限列表
     *
     * @param sysRoleDatascope 系统角色所拥有下属角色创建权限
     * @return 系统角色所拥有下属角色创建权限
     */
    @Override
    public List<SysRoleDatascope> selectSysRoleDatascopeList(SysRoleDatascope sysRoleDatascope)
    {
        return sysRoleDatascopeMapper.selectSysRoleDatascopeList(sysRoleDatascope);
    }

    /**
     * 新增系统角色所拥有下属角色创建权限
     *
     * @param sysRoleDatascope 系统角色所拥有下属角色创建权限
     * @return 结果
     */
    @Override
    public int insertSysRoleDatascope(SysRoleDatascope sysRoleDatascope)
    {
        return sysRoleDatascopeMapper.insertSysRoleDatascope(sysRoleDatascope);
    }

    /**
     * 修改系统角色所拥有下属角色创建权限
     *
     * @param sysRoleDatascope 系统角色所拥有下属角色创建权限
     * @return 结果
     */
    @Override
    public int updateSysRoleDatascope(SysRoleDatascope sysRoleDatascope)
    {
        List<SysRoleDatascope> list = new ArrayList<>();
        if (sysRoleDatascopeMapper.selectAllByRoleId(sysRoleDatascope.getRoleId()) == null ||
            sysRoleDatascopeMapper.selectAllByRoleId(sysRoleDatascope.getRoleId()).isEmpty()) {
            return sysRoleDatascopeMapper.updateSysRoleDatascope(sysRoleDatascope);
        } else {
            list = sysRoleDatascopeMapper.selectAllByRoleId(sysRoleDatascope.getRoleId());
            if (list.contains(sysRoleDatascope)) return 0;
            else return sysRoleDatascopeMapper.updateSysRoleDatascope(sysRoleDatascope);
        }
    }

    /**
     * 批量删除系统角色所拥有下属角色创建权限
     *
     * @param roleIds 需要删除的系统角色所拥有下属角色创建权限主键
     * @return 结果
     */
    @Override
    public int deleteSysRoleDatascopeByRoleIds(Long[] roleIds)
    {
        return sysRoleDatascopeMapper.deleteSysRoleDatascopeByRoleIds(roleIds);
    }

    /**
     * 删除系统角色所拥有下属角色创建权限信息
     *
     * @param roleId 系统角色所拥有下属角色创建权限主键
     * @return 结果
     */
    @Override
    public int deleteSysRoleDatascopeByRoleId(Long roleId)
    {
        return sysRoleDatascopeMapper.deleteSysRoleDatascopeByRoleId(roleId);
    }
}

