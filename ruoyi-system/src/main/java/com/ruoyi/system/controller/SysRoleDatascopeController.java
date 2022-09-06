package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysRoleDatascope;
import com.ruoyi.system.service.ISysRoleDatascopeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 系统角色所拥有下属角色创建权限Controller
 *
 * @author ruoyi
 * @date 2022-09-06
 */
@RestController
@RequestMapping("/system/datascope")
public class SysRoleDatascopeController extends BaseController
{
    @Autowired
    private ISysRoleDatascopeService sysRoleDatascopeService;

    /**
     * 查询系统角色所拥有下属角色创建权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:datascope:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysRoleDatascope sysRoleDatascope)
    {
        startPage();
        List<SysRoleDatascope> list = sysRoleDatascopeService.selectSysRoleDatascopeList(sysRoleDatascope);
        return getDataTable(list);
    }

    /**
     * 导出系统角色所拥有下属角色创建权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:datascope:export')")
    @Log(title = "系统角色所拥有下属角色创建权限", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRoleDatascope sysRoleDatascope)
    {
        List<SysRoleDatascope> list = sysRoleDatascopeService.selectSysRoleDatascopeList(sysRoleDatascope);
        ExcelUtil<SysRoleDatascope> util = new ExcelUtil<SysRoleDatascope>(SysRoleDatascope.class);
        util.exportExcel(response, list, "系统角色所拥有下属角色创建权限数据");
    }

    /**
     * 获取系统角色所拥有下属角色创建权限详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:datascope:query')")
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable("roleId") Long roleId)
    {
        return AjaxResult.success(sysRoleDatascopeService.selectSysRoleDatascopeByRoleId(roleId));
    }

    /**
     * 新增系统角色所拥有下属角色创建权限
     */
    @PreAuthorize("@ss.hasPermi('system:datascope:add')")
    @Log(title = "系统角色所拥有下属角色创建权限", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysRoleDatascope sysRoleDatascope)
    {
        return toAjax(sysRoleDatascopeService.insertSysRoleDatascope(sysRoleDatascope));
    }

    /**
     * 修改系统角色所拥有下属角色创建权限
     */
    @PreAuthorize("@ss.hasPermi('system:datascope:edit')")
    @Log(title = "系统角色所拥有下属角色创建权限", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysRoleDatascope sysRoleDatascope)
    {
        return toAjax(sysRoleDatascopeService.updateSysRoleDatascope(sysRoleDatascope));
    }

    /**
     * 删除系统角色所拥有下属角色创建权限
     */
    @PreAuthorize("@ss.hasPermi('system:datascope:remove')")
    @Log(title = "系统角色所拥有下属角色创建权限", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Long[] roleIds)
    {
        return toAjax(sysRoleDatascopeService.deleteSysRoleDatascopeByRoleIds(roleIds));
    }
}
