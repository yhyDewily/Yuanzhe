package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * @author Dewily
 * @date 2022-07-13 22:23
 */
public class User extends BaseEntity {

    private Long id;

    private String username;

    private Integer role;

    private Date createTime;

    public User() {
    }

    public User(Long id, String username, Integer role, Date createTime) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
