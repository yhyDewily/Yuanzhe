package com.ruoyi.common.enums;

/**
 * 数据源
 * 
 * @author ruoyi
 */
public enum DataSourceType
{
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库，历史库
     */
    SLAVE,

    /**
     * 备用库
     */
    BACKUP
}
