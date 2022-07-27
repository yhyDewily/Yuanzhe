package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.Auth;
import com.ruoyi.system.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:26
 */
@Controller
@RequestMapping("/system/auth")
public class AuthController extends BaseController {

    @Autowired
    private IAuthService authService;

    @RequestMapping("/get_by_id")
    public AjaxResult getById(int id) {
        Auth auth = authService.selectAuthById(id);
        if (auth != null) return AjaxResult.success(auth);
        else return AjaxResult.error("权限信息不存在");
    }

    @RequestMapping("/get_list")
    public TableDataInfo getAuthList(Auth auth) {
        List<Auth> list = authService.selectAuthList(auth);
        return getDataTable(list);
    }

    @RequestMapping("/add")
    public AjaxResult add(@RequestBody Auth auth) {
        int add_flag = authService.insertAuth(auth);
        if (add_flag == 0) return toAjax(add_flag);
        else return AjaxResult.error("插入失败，请检查数据项后重试");
    }

    @RequestMapping("/edit")
    public AjaxResult edit(@RequestBody Auth auth) {
        int edit_flag = authService.updateAuth(auth);
        if (edit_flag > 0) return toAjax(edit_flag);
        else return AjaxResult.error("更新失败，请检查数据项");
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable int id) {
        return toAjax(authService.deleteAuthById(id));
    }

}
