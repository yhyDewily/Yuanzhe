package com.ruoyi.common.ObjectView;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class CerView {

    /** 版本号 */
    private Long version;

    /** 序列号 */
    private String serialNumber;

    /** 颁发者 */
    private String issuerDn;

    /** 生效时间 */
    private Date startDate;

    /** 失效时间 */
    private Date finalDate;

    /** 使用者 */
    private String subjectDn;

    /** 公钥 */
    private String publicKey;

    /** 签名算法 */
    private String signatureAlgorithm;

    /** 证书签名 */
    private String signature;

    /** 状态 */
    private Long status;

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }
    public void setIssuerDn(String issuerDn)
    {
        this.issuerDn = issuerDn;
    }

    public String getIssuerDn()
    {
        return issuerDn;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setFinalDate(Date finalDate)
    {
        this.finalDate = finalDate;
    }

    public Date getFinalDate()
    {
        return finalDate;
    }
    public void setSubjectDn(String subjectDn)
    {
        this.subjectDn = subjectDn;
    }

    public String getSubjectDn()
    {
        return subjectDn;
    }
    public void setPublicKey(String publicKey)
    {
        this.publicKey = publicKey;
    }

    public String getPublicKey()
    {
        return publicKey;
    }
    public void setSignatureAlgorithm(String signatureAlgorithm)
    {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getSignatureAlgorithm()
    {
        return signatureAlgorithm;
    }
    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getSignature()
    {
        return signature;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("version", getVersion())
                .append("serialNumber", getSerialNumber())
                .append("issuerDn", getIssuerDn())
                .append("startDate", getStartDate())
                .append("finalDate", getFinalDate())
                .append("subjectDn", getSubjectDn())
                .append("publicKey", getPublicKey())
                .append("signatureAlgorithm", getSignatureAlgorithm())
                .append("signature", getSignature())
                .append("status", getStatus())
                .toString();
    }
}
