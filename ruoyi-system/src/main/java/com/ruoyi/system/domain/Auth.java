package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * @author Dewily
 * @date 2022-07-13 22:16
 */
public class Auth extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String roleName;

    private Integer roleAuth;

    private Date createTime;

    private Date updateTime;

    public Auth() {
    }

    public Auth(Integer id, String roleName, Integer roleAuth, Date createTime, Date updateTime) {
        this.id = id;
        this.roleName = roleName;
        this.roleAuth = roleAuth;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleAuth() {
        return roleAuth;
    }

    public void setRoleAuth(Integer roleAuth) {
        this.roleAuth = roleAuth;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleAuth=" + roleAuth +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
