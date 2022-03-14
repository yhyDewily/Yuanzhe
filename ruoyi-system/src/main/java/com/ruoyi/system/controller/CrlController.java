package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IrevokeCerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.cert.CertificateException;
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
    @Autowired
    IrevokeCerService irevokeCerService;
    @Value("${ocspConfiguration.crlUrl}")
    private String crlUrl;

    /**
     * crl 下载地址
     */
    @GetMapping("/get_crl_url")
    public AjaxResult getCrlUrl(){
        return AjaxResult.success("crlurl获取成功", crlUrl);
    }
    /**
     * 创建crl
     */
    @PostMapping("/create_crl")
    public AjaxResult createUrl(String time, int updateSpan) throws ParseException, CertificateException, IOException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        irevokeCerService.GeCRL(updateSpan);
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

