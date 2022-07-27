package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.OperatorType;
import com.ruoyi.system.domain.User;
import com.ruoyi.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-13 22:27
 */
@Controller
@RequestMapping("/system/user_controller")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/get_by_id")
    public AjaxResult getById(int id) {
        User user = userService.selectUserById(id);
        if (user != null) return AjaxResult.success(user);
        else return AjaxResult.error("权限信息不存在");
    }

    @RequestMapping("/get_list")
    public TableDataInfo getUserList(User user) {
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @RequestMapping("/add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT, operatorType = OperatorType.SUPER_ADMIN)
    public AjaxResult add(@RequestBody User user) {
        int add_flag = userService.insertUser(user);
        if (add_flag == 1) return toAjax(add_flag);
        else return AjaxResult.error("插入失败，请检查数据项后重试");
    }

    @RequestMapping("/edit")
    public AjaxResult edit(@RequestBody User user) {
        int edit_flag = userService.updateUser(user);
        if (edit_flag > 0) return toAjax(edit_flag);
        else return AjaxResult.error("更新失败，请检查数据项");
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable int id) {
        return toAjax(userService.deleteUserById(id));
    }
}
