package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.KmsKeyCount;
import com.ruoyi.system.service.IKmsKeyCountService;
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
public class KmsKeyCountController extends BaseController {

    @Autowired
    private IKmsKeyCountService keyCountService;

    /**
     * todo
     * 统计各种密钥的使用次数，返回的数据格式视需要而定
     * 应该包括在用非对称密钥，在用对称密钥，归档非对称密钥，归档对称密钥
     */

    @RequestMapping("/get_by_id")
    public AjaxResult getById(int id) {
        KmsKeyCount kmsKeyCount = keyCountService.selectKeyCountById(id);
        if (kmsKeyCount != null) return AjaxResult.success(kmsKeyCount);
        else return AjaxResult.error("权限信息不存在");
    }

    @RequestMapping("/get_list")
    public TableDataInfo getKeyCountList(KmsKeyCount kmsKeyCount) {
        List<KmsKeyCount> list = keyCountService.selectKeyCountList(kmsKeyCount);
        return getDataTable(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(@RequestBody KmsKeyCount kmsKeyCount) {
        int add_flag = keyCountService.insertKeyCount(kmsKeyCount);
        if (add_flag == 0) return toAjax(add_flag);
        else return AjaxResult.error("插入失败，请检查数据项后重试");
    }

    @RequestMapping("/edit")
    public AjaxResult edit(@RequestBody KmsKeyCount kmsKeyCount) {
        int edit_flag = keyCountService.updateKeyCount(kmsKeyCount);
        if (edit_flag > 0) return toAjax(edit_flag);
        else return AjaxResult.error("更新失败，请检查数据项");
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable int id) {
        return toAjax(keyCountService.deleteKeyCountById(id));
    }
}
