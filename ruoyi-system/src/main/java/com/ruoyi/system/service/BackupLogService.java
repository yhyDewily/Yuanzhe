package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.BackupLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.BackupSecretKey;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
public interface BackupLogService extends IService<BackupLog> {

    /**
     * 分页查询定时任务每次操作备用库的日志
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<BackupLog> getAllBackupLog(Long currentPage, Long pageSize, String beginDate, String endDate);
}
