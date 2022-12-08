package com.ruoyi.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BackupSecretKey;
import com.ruoyi.system.domain.SecretKey;
import com.ruoyi.system.service.BackupSecretKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 密钥表 前端控制器
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
@RestController
@PreAuthorize("@ss.hasRole('business_operator')")
@RequestMapping("/kms/api/backup")
public class BackupSecretKeyController {

    @Autowired
    BackupSecretKeyService backupSecretKeyService;


    /**
     * 从备用库中获取批量密钥
     *
     * @param type   密钥类型
     * @param keyNum 需要获取的密钥数量
     * @return 密钥集合
     */
    @Log(title = "从备用库中获取批量密钥", businessType = BusinessType.EXPORT)
    @PostMapping("/getKeyPairList")
    public AjaxResult getKeyPairList(@RequestParam String type, @RequestParam Integer keyNum) {
        List<BackupSecretKey> list = backupSecretKeyService.getKeyPairList(keyNum);
        return AjaxResult.success("获取成功！", list);
    }

    /**
     * 从备用库中获取一对
     *
     * @param type 密钥类型
     * @return 密钥
     */
    @Log(title = "从备用库中获取密钥", businessType = BusinessType.EXPORT)
    @PostMapping("/getKeyPair")
    public AjaxResult getKeyPairList(@RequestParam(required = false) String type) {
        List<BackupSecretKey> list = backupSecretKeyService.getKeyPairList(1);
        if (list != null && list.size() > 0){
            return AjaxResult.success("获取成功！", list.get(0));
        }
        return AjaxResult.error("获取失败");
    }

    @Log(title = "查询备用库密钥", businessType = BusinessType.EXPORT)
    @GetMapping("getAllBackupKey")
    public AjaxResult getAllBackupKey(@RequestParam Long currentPage, @RequestParam Long pageSize) {
        IPage<BackupSecretKey> page = backupSecretKeyService.getAllBackupKey(currentPage, pageSize);
        return AjaxResult.success(page);
    }

}
