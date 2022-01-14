package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.framework.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.framework.web.domain.server.Sys;
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
import com.ruoyi.system.domain.Cer;
import com.ruoyi.system.service.ICerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 证书管理Controller
 *
 * @author ruoyi
 * @date 2022-01-05
 */
@RestController
@RequestMapping("/system/cer")
public class CerController extends BaseController
{
    @Autowired
    private ICerService cerService;

    /**
     * 生成证书列表
     */
    @GetMapping("/gen_cer")
    public AjaxResult genCer() {
        System.out.println("生成请求");
        //System.out.println(DynamicDataSourceContextHolder.getDataSourceType());
        cerService.synchronousData();
        return AjaxResult.success();
    }

    /**
     * 查询证书管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:cer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Cer cer)
    {
        startPage();
        List<Cer> list = cerService.selectCerList(cer);
        return getDataTable(list);
    }

    /**
     * 导出证书管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:cer:export')")
    @Log(title = "证书管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Cer cer)
    {
        List<Cer> list = cerService.selectCerList(cer);
        ExcelUtil<Cer> util = new ExcelUtil<Cer>(Cer.class);
        util.exportExcel(response, list, "证书管理数据");
    }

    /**
     * 获取证书管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:cer:query')")
    @GetMapping(value = "/{version}")
    public AjaxResult getInfo(@PathVariable("version") Long version)
    {
        return AjaxResult.success(cerService.selectCerByVersion(version));
    }

    /**
     * 同步
     */
    @Log(title = "证书管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add()
    {
        System.out.println("哈哈哈哈");
        return toAjax(1);
    }

    /**
     * 修改证书管理
     */
    @PreAuthorize("@ss.hasPermi('system:cer:edit')")
    @Log(title = "证书管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Cer cer)
    {
        return toAjax(cerService.updateCer(cer));
    }

    /**
     * 删除证书管理
     */
    @PreAuthorize("@ss.hasPermi('system:cer:remove')")
    @Log(title = "证书管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{versions}")
    public AjaxResult remove(@PathVariable Long[] versions)
    {
        return toAjax(cerService.deleteCerByVersions(versions));
    }
}