package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lls
 * @since 2022-11-22
 */
@Getter
@Setter
@TableName("db_config")
public class DbConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ip")
    private String ip;

    @TableField("port")
    private String port;

    @TableField("db")
    private String db;

    @TableField("user")
    private String user;

    @TableField("password")
    private String password;


}
