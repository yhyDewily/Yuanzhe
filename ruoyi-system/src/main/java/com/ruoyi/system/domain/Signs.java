package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;

import java.io.Serializable;
import java.util.Date;

public class Signs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer id;

    /**
     * 证书序列号
     */
    private String serialNumber;

    /**
     * 签发人
     */
    private String signer;

    /**
     * 申请人
     */
    private String applysUser;

    /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 设备类型
     */
    private String equipmentType;

    /**
     * 设备编号
     */
    private String equipmentNumber;

    /**
     * 密钥证书名
     */
    private String crtName;

    /**
     * 公钥地址
     */
    private String crtUrl;

    /**
     * 签发类型1：手动签发2：网络协议签发
     */
    private Integer signType;

    /**
     * 撤销状态1：正常使用，2：已撤销，3：挂起
     */
    private Integer revokeStatus;

    /**
     * 密钥撤销时间
     */
    private Date revokeTime;

    /**
     * 撤销原因CRL理由码：0：空白，1：密钥泄露(1), 2：CA泄露(2)，3：附属关系已更改(3)，4：被取代(4)，5：停止操作(5)，6：证书挂起(6)，8：从CRL中删除，9和10：Unknow CRL reason
     */
    private Integer revokeReason;

    /**
     * 证书有效截至日期
     */
    private Date effecTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 变动时间
     */
    private Date updateTime;

    public Signs() {
    }

    public Signs(Integer id, String serialNumber, String signer, String applysUser, String equipmentName, String equipmentType, String equipmentNumber, String crtName, String crtUrl, Integer signType, Integer revokeStatus, Date revokeTime, Integer revokeReason, Date effecTime, Date createTime, Date updateTime) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.signer = signer;
        this.applysUser = applysUser;
        this.equipmentName = equipmentName;
        this.equipmentType = equipmentType;
        this.equipmentNumber = equipmentNumber;
        this.crtName = crtName;
        this.crtUrl = crtUrl;
        this.signType = signType;
        this.revokeStatus = revokeStatus;
        this.revokeTime = revokeTime;
        this.revokeReason = revokeReason;
        this.effecTime = effecTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getApplysUser() {
        return applysUser;
    }

    public void setApplysUser(String applysUser) {
        this.applysUser = applysUser;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtUrl() {
        return crtUrl;
    }

    public void setCrtUrl(String crtUrl) {
        this.crtUrl = crtUrl;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public Integer getRevokeStatus() {
        return revokeStatus;
    }

    public void setRevokeStatus(Integer revokeStatus) {
        this.revokeStatus = revokeStatus;
    }

    public Date getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) {
        this.revokeTime = revokeTime;
    }

    public Integer getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(Integer revokeReason) {
        this.revokeReason = revokeReason;
    }

    public Date getEffecTime() {
        return effecTime;
    }

    public void setEffecTime(Date effecTime) {
        this.effecTime = effecTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
