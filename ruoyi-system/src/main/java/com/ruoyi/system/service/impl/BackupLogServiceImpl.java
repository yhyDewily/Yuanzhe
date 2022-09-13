package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.BackupLog;
import com.ruoyi.system.mapper.BackupLogMapper;
import com.ruoyi.system.service.BackupLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
@Service
public class BackupLogServiceImpl extends ServiceImpl<BackupLogMapper, BackupLog> implements BackupLogService {

    @DataSource(value = DataSourceType.BACKUP)
    @Override
    public IPage<BackupLog> getAllBackupLog(Long currentPage, Long pageSize, String beginDate, String endDate) {
        QueryWrapper<BackupLog> queryWrapper = new QueryWrapper<>();
        if (beginDate != null && !beginDate.equals("")) {
            queryWrapper.ge("create_time", beginDate);
        }
        if (endDate != null && !endDate.equals("")) {
            queryWrapper.le("create_time", endDate);
        }
        return this.page(new Page<>(currentPage, pageSize), queryWrapper);
    }
}
