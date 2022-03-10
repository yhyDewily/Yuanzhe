package com.ruoyi.system.domain;

/**
 * @Author: min
 * @Date: 2022/3/8
 * @Time: 17:23
 * @Description:
 */
public class RevokeCer {

    private String crtUrl;

    private Integer revokeReason;

    public String getCrtUrl() {
        return crtUrl;
    }

    public void setCrtUrl(String crtUrl) {
        this.crtUrl = crtUrl;
    }

    public Integer getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(Integer revokeReason) {
        this.revokeReason = revokeReason;
    }

    @Override
    public String toString() {
        return "RevokeCer{" +
                "crtUrl='" + crtUrl + '\'' +
                ", revokeReason=" + revokeReason +
                '}';
    }
}
