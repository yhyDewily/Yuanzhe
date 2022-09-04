package com.ruoyi.system.service;


import com.ruoyi.system.domain.vo.SysLogVO;

/**
 * 审计员操作日志服务层
 */
public interface ISysOperLogAuditService {

    /**
     *
     * @param sysLogVO 日志信息
     * @return 结果 0 成功 1 失败
     */
    public int auditLog(SysLogVO sysLogVO);
}
