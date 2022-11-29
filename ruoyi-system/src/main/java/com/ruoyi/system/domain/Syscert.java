package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 系统证书对象 syscert
 *
 * @author ruoyi
 * @date 2022-11-18
 */
public class Syscert extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 证书序列号 */
    @Excel(name = "证书序列号")
    private String certSn;

    /** 证书名称 */
    @Excel(name = "证书名称")
    private String certName;

    /** 证书主题 */
    @Excel(name = "证书主题")
    private String certDn;

    /** 证书实体 */
    @Excel(name = "证书实体")
    private String certEntity;

    /** 证书类型 */
    @Excel(name = "证书类型")
    private String certType;

    /** 证书状态 */
    @Excel(name = "证书状态")
    private String certStatus;

    /** 加密算法 */
    @Excel(name = "加密算法")
    private String keyAlg;

    /** 密钥长度 */
    @Excel(name = "密钥长度")
    private Long keyLen;

    /** 生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date notbefore;

    /** 失效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date notafter;

    /** 有效期 */
    @Excel(name = "有效期")
    private Long validity;

    /** 设备id */
    @Excel(name = "设备id")
    private String deviceId;

    /** 密钥id */
    @Excel(name = "密钥id")
    private Long hardkeyId;

    /** 导入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "导入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date importTime;

    /** 注销时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注销时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date revokeTime;

    /** 注销理由 */
    @Excel(name = "注销理由")
    private String reason;

    /** 理由备注 */
    @Excel(name = "理由备注")
    private String reasonDesc;

    public void setCertSn(String certSn)
    {
        this.certSn = certSn;
    }

    public String getCertSn()
    {
        return certSn;
    }
    public void setCertName(String certName)
    {
        this.certName = certName;
    }

    public String getCertName()
    {
        return certName;
    }
    public void setCertDn(String certDn)
    {
        this.certDn = certDn;
    }

    public String getCertDn()
    {
        return certDn;
    }
    public void setCertEntity(String certEntity)
    {
        this.certEntity = certEntity;
    }

    public String getCertEntity()
    {
        return certEntity;
    }
    public void setCertType(String certType)
    {
        this.certType = certType;
    }

    public String getCertType()
    {
        return certType;
    }
    public void setCertStatus(String certStatus)
    {
        this.certStatus = certStatus;
    }

    public String getCertStatus()
    {
        return certStatus;
    }
    public void setKeyAlg(String keyAlg)
    {
        this.keyAlg = keyAlg;
    }

    public String getKeyAlg()
    {
        return keyAlg;
    }
    public void setKeyLen(Long keyLen)
    {
        this.keyLen = keyLen;
    }

    public Long getKeyLen()
    {
        return keyLen;
    }
    public void setNotbefore(Date notbefore)
    {
        this.notbefore = notbefore;
    }

    public Date getNotbefore()
    {
        return notbefore;
    }
    public void setNotafter(Date notafter)
    {
        this.notafter = notafter;
    }

    public Date getNotafter()
    {
        return notafter;
    }
    public void setValidity(Long validity)
    {
        this.validity = validity;
    }

    public Long getValidity()
    {
        return validity;
    }
    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceId()
    {
        return deviceId;
    }
    public void setHardkeyId(Long hardkeyId)
    {
        this.hardkeyId = hardkeyId;
    }

    public Long getHardkeyId()
    {
        return hardkeyId;
    }
    public void setImportTime(Date importTime)
    {
        this.importTime = importTime;
    }

    public Date getImportTime()
    {
        return importTime;
    }
    public void setRevokeTime(Date revokeTime)
    {
        this.revokeTime = revokeTime;
    }

    public Date getRevokeTime()
    {
        return revokeTime;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    public void setReasonDesc(String reasonDesc)
    {
        this.reasonDesc = reasonDesc;
    }

    public String getReasonDesc()
    {
        return reasonDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("certSn", getCertSn())
                .append("certName", getCertName())
                .append("certDn", getCertDn())
                .append("certEntity", getCertEntity())
                .append("certType", getCertType())
                .append("certStatus", getCertStatus())
                .append("keyAlg", getKeyAlg())
                .append("keyLen", getKeyLen())
                .append("notbefore", getNotbefore())
                .append("notafter", getNotafter())
                .append("validity", getValidity())
                .append("deviceId", getDeviceId())
                .append("hardkeyId", getHardkeyId())
                .append("importTime", getImportTime())
                .append("revokeTime", getRevokeTime())
                .append("remark", getRemark())
                .append("reason", getReason())
                .append("reasonDesc", getReasonDesc())
                .toString();
    }
}
