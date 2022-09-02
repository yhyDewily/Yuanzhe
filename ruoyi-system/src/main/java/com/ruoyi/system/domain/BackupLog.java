package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lls
 * @Date 2022-08-31 16:05
 **/
@Data
@TableName("kms_backup_log")
public class BackupLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(value = "key_num")
    private Integer keyNum;


    /**
     * 密钥类型，指明密钥加密具体类型，例如SM1
     */
    @TableField("key_type")
    private String keyType;
}
