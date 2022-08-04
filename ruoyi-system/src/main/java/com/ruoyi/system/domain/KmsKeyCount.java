package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * @author Dewily
 * @date 2022-07-13 22:21
 */
public class KmsKeyCount extends BaseEntity {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer downloadCount;

    private Integer logOutCount;

    private Integer applyCount;

    public KmsKeyCount() {
    }

    public KmsKeyCount(Integer id, Integer downloadCount, Integer logOutCount, Integer applyCount) {
        this.id = id;
        this.downloadCount = downloadCount;
        this.logOutCount = logOutCount;
        this.applyCount = applyCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getLogOutCount() {
        return logOutCount;
    }

    public void setLogOutCount(Integer logOutCount) {
        this.logOutCount = logOutCount;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    @Override
    public String toString() {
        return "KeyCount{" +
                "id=" + id +
                ", downloadCount=" + downloadCount +
                ", logOutCount=" + logOutCount +
                ", applyCount=" + applyCount +
                '}';
    }
}
