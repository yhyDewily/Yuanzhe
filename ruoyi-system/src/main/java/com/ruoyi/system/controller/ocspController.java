package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IOcspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dewily
 * @date 2022-02-23 14:10
 */

@RestController
@RequestMapping("/ocsp")
public class ocspController {

    @Autowired
    private Environment env;

    @Autowired
    private IOcspService ocspService;

    /**
     * 根据前端传的序列号返回证书状态
     * @param serialNumber
     * @return
     */
    @PostMapping(value = "/check_by_serial_number")
    @ResponseBody
    public AjaxResult checkById(String serialNumber) {
        Integer result = ocspService.checkBySerialNumber(serialNumber);
        return AjaxResult.success(result);
    }

    /**
     * 根据ocsp请求返回证书状态
     * @param ocsp_request
     * @return
     */
    @PostMapping(value = "/check_by_request")
    @ResponseBody
    public byte[] checkByRequest(@RequestBody byte[] ocsp_request) {
        return ocspService.checkByRequest(ocsp_request);
    }
}
