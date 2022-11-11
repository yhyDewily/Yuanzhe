package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.vo.CipherEnvelop;
import com.ruoyi.system.service.ISmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bestFang666
 * @date 2022/11/1 16:05
 */
@RestController
@RequestMapping("/SM")
public class SmController {

    @Autowired
    ISmService iSmService;

    /**
     * @param cipherEnvelop SM4 对称密钥和 Iv，以及明文和算法
     * @return 加密密文（Base64 格式）/摘要
     */
    @PostMapping("/encryptCbcOrAbstarct")
    public AjaxResult encryptWithSM4CBCPadding(@RequestBody CipherEnvelop cipherEnvelop) {
        String algorithm = cipherEnvelop.getAlgorithm();
        String result = "";
        // 根据算法类型来做相应的处理
        if (algorithm.equals("SM4_CBC")) {
            result = iSmService.encryptWithSM4CBCPadding(cipherEnvelop);
        } else {
            result = iSmService.abstarctSM3(cipherEnvelop);
        }
        if (result.equals("")) {
            return AjaxResult.error("处理失败！");
        } else {
            return AjaxResult.success("处理成功！", result);
        }
    }

    /**
     * @param cipherEnvelop SM4 对称密钥和 Iv，以及明文和算法
     * @return 解密后的明文
     */
    @PostMapping("/decryptCbc")
    public AjaxResult decryptWithSM4CBCPadding(@RequestBody CipherEnvelop cipherEnvelop) {
        String result = iSmService.decryptWithSM4CBCPadding(cipherEnvelop);
        if (result.equals("")) {
            return AjaxResult.error("处理失败！");
        } else {
            return AjaxResult.success("处理成功！", result);
        }
    }


}
