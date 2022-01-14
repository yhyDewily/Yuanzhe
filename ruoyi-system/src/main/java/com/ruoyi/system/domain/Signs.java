package com.ruoyi.system.domain;

import java.util.Date;

public class Signs {
    String crtUrl;
    Date updateTime;
    Long revoteStatus;

    public String getCrtUrl() {
        return crtUrl;
    }

    public void setCrtUrl(String crtUrl) {
        this.crtUrl = crtUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRevoteStatus() {
        return revoteStatus;
    }

    public void setRevoteStatus(Long revoteStatus) {
        this.revoteStatus = revoteStatus;
    }
}
