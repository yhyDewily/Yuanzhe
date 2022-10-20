package com.ruoyi.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BackupLog;
import com.ruoyi.system.domain.BackupSecretKey;
import com.ruoyi.system.service.BackupLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
@RestController
@RequestMapping("/kms/api/log")
public class BackupLogController {

    @Autowired
    BackupLogService backupLogService;

    @Log(title = "备份数据库日志", businessType = BusinessType.EXPORT)
    @GetMapping("/getAllBackupLog")
    public AjaxResult getAllBackupLog(@RequestParam Long currentPage, @RequestParam Long pageSize
    , @RequestParam String beginDate,@RequestParam String endDate){
        IPage<BackupLog> page = backupLogService.getAllBackupLog(currentPage,pageSize,beginDate,endDate);
        return AjaxResult.success(page);
    }


}
