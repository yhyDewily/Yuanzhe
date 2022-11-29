package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.Syscert;
import com.ruoyi.system.service.ISyscertService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 系统证书Controller
 *
 * @author ruoyi
 * @date 2022-11-18
 */
@RestController
@RequestMapping("/system/syscert")
public class SyscertController extends BaseController
{
    @Autowired
    private ISyscertService syscertService;

    /**
     * 查询系统证书列表
     */
    @PreAuthorize("@ss.hasPermi('system:syscert:list')")
    @GetMapping("/list")
    public TableDataInfo list(Syscert syscert)
    {
        startPage();
        List<Syscert> list = syscertService.selectSyscertList(syscert);
        return getDataTable(list);
    }

    /**
     * 导出系统证书列表
     */
    @PreAuthorize("@ss.hasPermi('system:syscert:export')")
    @Log(title = "系统证书", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Syscert syscert)
    {
        List<Syscert> list = syscertService.selectSyscertList(syscert);
        ExcelUtil<Syscert> util = new ExcelUtil<Syscert>(Syscert.class);
        util.exportExcel(response, list, "系统证书数据");
    }

    /**
     * 获取系统证书详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:syscert:query')")
    @GetMapping(value = "/{certSn}")
    public AjaxResult getInfo(@PathVariable("certSn") String certSn)
    {
        return AjaxResult.success(syscertService.selectSyscertByCertSn(certSn));
    }

    /**
     * 新增系统证书
     */
    @PreAuthorize("@ss.hasPermi('system:syscert:add')")
    @Log(title = "系统证书", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Syscert syscert)
    {
        return toAjax(syscertService.insertSyscert(syscert));
    }

    /**
     * 修改系统证书
     */
    @PreAuthorize("@ss.hasPermi('system:syscert:edit')")
    @Log(title = "系统证书", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Syscert syscert)
    {
        return toAjax(syscertService.updateSyscert(syscert));
    }

    /**
     * 删除系统证书
     */
    @PreAuthorize("@ss.hasPermi('system:syscert:remove')")
    @Log(title = "系统证书", businessType = BusinessType.DELETE)
    @DeleteMapping("/{certSns}")
    public AjaxResult remove(@PathVariable String[] certSns)
    {
        return toAjax(syscertService.deleteSyscertByCertSns(certSns));
    }
}

