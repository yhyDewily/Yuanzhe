package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.KeyCount;
import com.ruoyi.system.service.IKeyCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author  Dewily
 * @date 2022-07-13 22:27
 */
@Controller
@RequestMapping("/system/key-count")
public class KeyCountController extends BaseController {

    @Autowired
    private IKeyCountService keyCountService;

    @RequestMapping("/get_by_id")
    public AjaxResult getById(int id) {
        KeyCount keyCount = keyCountService.selectKeyCountById(id);
        if (keyCount != null) return AjaxResult.success(keyCount);
        else return AjaxResult.error("权限信息不存在");
    }

    @RequestMapping("/get_list")
    public TableDataInfo getKeyCountList(KeyCount keyCount) {
        List<KeyCount> list = keyCountService.selectKeyCountList(keyCount);
        return getDataTable(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(@RequestBody KeyCount keyCount) {
        int add_flag = keyCountService.insertKeyCount(keyCount);
        if (add_flag == 0) return toAjax(add_flag);
        else return AjaxResult.error("插入失败，请检查数据项后重试");
    }

    @RequestMapping("/edit")
    public AjaxResult edit(@RequestBody KeyCount keyCount) {
        int edit_flag = keyCountService.updateKeyCount(keyCount);
        if (edit_flag > 0) return toAjax(edit_flag);
        else return AjaxResult.error("更新失败，请检查数据项");
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable int id) {
        return toAjax(keyCountService.deleteKeyCountById(id));
    }
}
