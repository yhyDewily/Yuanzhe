package com.ruoyi.system.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.SecretKeyService;
import com.ruoyi.system.utils.SM2KeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 密钥表 前端控制器
 * </p>
 *
 * @author lls
 * @since 2022-08-19
 */
@RestController
@RequestMapping("/kms/api")
public class SecretKeyController {

    @Autowired
    SecretKeyService secretKeyService;

    /**
     * 非对称密钥生成，目前只使用了SM2算法生成密钥
     *
     * @param type    算法类型，目前采用SM2算法
     * @param keyName 密钥名称，默认为""，同一应用同一类型的keyName不可重复
     * @return
     */
    @PostMapping("/applyKeyPair")
    public AjaxResult applyKeyPair(@RequestParam String type, @RequestParam String keyName) {
        Map<String,String> keyPair = secretKeyService.applyKeyPair(type, keyName);
        return AjaxResult.success("生成密钥成功",keyPair);
    }
}
