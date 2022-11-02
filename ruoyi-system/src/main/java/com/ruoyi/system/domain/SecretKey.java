package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 密钥表
 * </p>
 *
 * @author lls
 * @since 2022-08-20
 */
@Getter
@Setter
@TableName("kms_secret_key")
public class SecretKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    /**
     * 证书序列号
     */
    @TableField("serial_number")
    private String serialNumber;

    /**
     * 签发机构
     */
    @TableField("issure_dn")
    private String issureDn;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    @TableField(value = "expire_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime expireTime;

    /**
     * 使用时间
     */
    @TableField("use_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime useTime;

    /**
     * 密钥长度
     */
    @TableField("length")
    private Integer length;

    /**
     * 密钥是否对称，1为对称密钥，0为非对称密钥
     */
    @TableField("key_symmetry")
    private Boolean keySymmetry;

    /**
     * 密钥类型，指明密钥加密具体类型，例如SM1
     */
    @TableField("key_type")
    private String keyType;

    /**
     * 私钥字段
     */
    @TableField("private_key")
    private String privateKey;

    /**
     * 公钥字段
     */
    @TableField("public_key")
    private String publicKey;

    /**
     * 密钥名称
     */
    @TableField("key_name")
    private String keyName;

    @TableField("ca_id")
    private String caId;

    /**
     * 密钥是否使用，1为正在使用，0为已注销
     */
    @TableField(value = "valid", fill = FieldFill.INSERT)
    private Boolean valid;


}
