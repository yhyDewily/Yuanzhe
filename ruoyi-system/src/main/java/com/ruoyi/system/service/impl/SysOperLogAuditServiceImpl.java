package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.domain.vo.SysLogVO;
import com.ruoyi.system.mapper.SysOperLogMapper;
import com.ruoyi.system.service.ISysOperLogAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogAuditServiceImpl implements ISysOperLogAuditService {

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Override
    public int auditLog(SysLogVO sysLogVO) {
        SysOperLog sysOperLog = operLogMapper.selectOperLogById(sysLogVO.getOperId());
        if (sysOperLog == null) {
            return 1;
        }
        //比对前端传入的日志信息和数据库存储的日志信息是否被修改，如果被修改，则审计失败
        sysOperLog.setIsAudit(1);
        if (!sysLogVO.getTitle().equals(sysOperLog.getTitle())) {
            sysOperLog.setAuditResult(1);
            operLogMapper.updateLog(sysOperLog);
            return 1;
        }
        if (!sysLogVO.getOperName().equals(sysOperLog.getOperName())) {
            sysOperLog.setAuditResult(1);
            operLogMapper.updateLog(sysOperLog);
            return 1;
        }
        if (!sysLogVO.getBusinessType().equals(sysOperLog.getBusinessType())) {
            sysOperLog.setAuditResult(1);
            operLogMapper.updateLog(sysOperLog);
            return 1;
        }
        if (!sysLogVO.getOperTime().equals(sysOperLog.getOperTime())) {
            sysOperLog.setAuditResult(1);
            operLogMapper.updateLog(sysOperLog);
            return 1;
        }
        sysOperLog.setAuditResult(0);
        operLogMapper.updateLog(sysOperLog);
        return 0;
    }
}
