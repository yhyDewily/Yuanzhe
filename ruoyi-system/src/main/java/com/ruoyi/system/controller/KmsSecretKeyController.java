package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.KmsSecretKey;
import com.ruoyi.system.service.IKmsSecretKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @keyor Dewily
 * @date 2022-07-13 22:27
 */
@Controller
@RequestMapping("/system/key")
public class KmsSecretKeyController extends BaseController {

    @Autowired
    private IKmsSecretKeyService keyService;

    @RequestMapping("/get_by_id")
    public AjaxResult getById(int id) {
        KmsSecretKey key = keyService.selectKeyById(id);
        if (key != null) return AjaxResult.success(key);
        else return AjaxResult.error("权限信息不存在");
    }

    @RequestMapping("/get_list")
    public TableDataInfo getKeyList(KmsSecretKey key) {
        List<KmsSecretKey> list = keyService.selectKeyList(key);
        return getDataTable(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(@RequestBody KmsSecretKey key) {
        int add_flag = keyService.insertKey(key);
        if (add_flag == 0) return toAjax(add_flag);
        else return AjaxResult.error("插入失败，请检查数据项后重试");
    }

    @RequestMapping("/edit")
    public AjaxResult edit(@RequestBody KmsSecretKey key) {
        int edit_flag = keyService.updateKey(key);
        if (edit_flag > 0) return toAjax(edit_flag);
        else return AjaxResult.error("更新失败，请检查数据项");
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable int id) {
        return toAjax(keyService.deleteKeyById(id));
    }
}
