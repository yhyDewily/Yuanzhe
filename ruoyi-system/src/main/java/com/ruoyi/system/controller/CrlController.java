package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * CRL管理Controller
 *
 * @author lilin
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/system/crl")
public class CrlController {

    /**
     * 创建crl
     */
    @PostMapping("/create_crl")
    public AjaxResult createUrl(String time, int updateSpan) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        System.out.println("创建时间：" + date);
        System.out.println("更新时间间隔：" + updateSpan);
        return AjaxResult.success();
    }

    /**
     * 修改crl更新间隔
     */
    @PutMapping("/update_span")
    public AjaxResult updateSpan(Integer updateSpan)
    {
        System.out.println("更新时间间隔：" + updateSpan);
        return AjaxResult.success();
    }
}

