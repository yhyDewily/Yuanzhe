package com.ruoyi.system.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginBody implements Serializable
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

}