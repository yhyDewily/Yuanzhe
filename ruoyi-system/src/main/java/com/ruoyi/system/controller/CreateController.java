package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dewily
 * @date 2022-07-15 14:42
 */

@Controller
@RequestMapping("/system/create")
public class CreateController {

    /**
     * todo 主密钥生成
     * @return
     */
    @RequestMapping("/generate_main_key")
    public AjaxResult generateMainKey () {
        return AjaxResult.success();
    }

    /**
     * todo 系统证书申请
     */
    @RequestMapping("/sys_cer_apply")
    public AjaxResult sysCerApply() {
        return AjaxResult.success();
    }

    /**
     * todo 系统证书导入
     */
    @RequestMapping("/sys_cer_import")
    public AjaxResult sysCerImport() {
        return AjaxResult.success();
    }

    /**
     * 创建超管
     */
    @RequestMapping("create_super_admin")
    public AjaxResult createSuperAdmin() {
        return AjaxResult.success("创建成功");
    }

    /**
     * todo 创建审计管理员
     */
    @RequestMapping("create_check_admin")
    public AjaxResult createCheckAdmin() {
        return AjaxResult.success("创建成功");
    }


}
