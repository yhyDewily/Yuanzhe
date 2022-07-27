package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * @author Dewily
 * @date 2022-07-13 22:22
 */
public class Logs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    private Long logId;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 具体操作
     */
    private String step;

    /**
     * 操作者id
     */
    private Long userId;

    public Logs() {
    }

    public Logs(Long logId, Date createTime, String step, Long userId) {
        this.logId = logId;
        this.createTime = createTime;
        this.step = step;
        this.userId = userId;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "logId=" + logId +
                ", createTime=" + createTime +
                ", process='" + step + '\'' +
                ", userId=" + userId +
                '}';
    }
}
