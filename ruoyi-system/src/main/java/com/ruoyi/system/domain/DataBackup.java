package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lls
 * @since 2022-09-08
 */
@Getter
@Setter
@TableName("kms_data_backup")
public class DataBackup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id，唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作员
     */
    @TableField("operater")
    private String operater;

    /**
     * 备份的数据库名称
     */
    @TableField("database_name")
    private String databaseName;

    /**
     * 备份的数据表名称，如果是全库备份则为all
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 备份后的文件下载链接
     */
    @TableField("href")
    private String href;

    /**
     * 操作时间
     */
    @TableField(value = "op_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime opTime;

    /**
     * 操作类型，包含导入导出
     */
    @TableField("op_type")
    private String opType;

    /**
     * 文件目录
     */
    @TableField("file_directory")
    private String fileDirectory;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    @TableField("ip")
    private String ip;

    /**
     * sql文件内容对应的hash，用于判断hash是否被篡改
     */
    @TableField("sql_hash")
    private String sqlHash;

    /**
     * sql文件被加密后的内容
     */
    @TableField("sql_entity")
    private String sqlEntity;


}
