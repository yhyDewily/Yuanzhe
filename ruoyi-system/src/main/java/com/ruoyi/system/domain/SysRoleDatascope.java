package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysRoleDatascope extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 角色id */
    @Excel(name = "角色id")
    private Long roleId;

    /** 拥有的下属角色id */
    @Excel(name = "拥有的下属角色id")
    private Long subRoleId;

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }
    public void setSubRoleId(Long subRoleId)
    {
        this.subRoleId = subRoleId;
    }

    public Long getSubRoleId()
    {
        return subRoleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("roleId", getRoleId())
                .append("subRoleId", getSubRoleId())
                .toString();
    }
}
