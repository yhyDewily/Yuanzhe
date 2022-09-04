package com.ruoyi.system.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 返回前端的日志记录信息
 */
public class SysLogVO extends BaseEntity {

    /** 日志主键*/
    private Long operId;

    /** 操作模块 */
    private String title;

    /** 操作人员 */
    private String operName;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private Integer businessType;

    /** 操作起始时间 */
    private Date operTime;

    /** 操作状态（0正常 1异常） */
    private Integer status;

    /** 是否已审计 0 未审计 1 已审计*/
    private Integer isAudit;

    /** 审计结果(0 成功 1 失败) */
    private Integer auditResult;

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer audit) {
        isAudit = audit;
    }

    public Integer getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }
}
