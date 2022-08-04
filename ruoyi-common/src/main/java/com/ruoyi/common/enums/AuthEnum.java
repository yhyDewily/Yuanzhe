package com.ruoyi.common.enums;

/**
 * @author Dewily
 * @date 2022-07-15 14:49
 */

/**
 * 23个权限
 */
public enum AuthEnum {

    /**
     * 角色管理
     */
    USER_MANAGE,

    /**
     * 操作员管理
     */
    OPERATOR_MANAGE,

    /**
     * 密码修改
     */
    PASSWD_CHANGE,

    /**
     * 系统证书管理
     */
    SYS_CER_MANAGE,

    /**
     * CA证书管理
     */
    CA_CER_MANAGE,

    /**
     * 系统策略管理
     */
    SYS_STRATEGY_MANAGE,

    /**
     * 定时任务管理
     */
    CRON_JOB_MANAGE,

    /**
     * 网络配置
     */
    NETWORK_MANAGE,

    /**
     * 数据备份恢复
     */
    DATA_BACKUP,

    /**
     * 双机热备管理
     */
    DOUBLE_HOT_MANAGE,

    /**
     * 密钥统计
     */
    KEY_COUNT,

    /**
     * 在用对称密钥查询
     */
    INUSE_SYM_KEY_SEARCH,

    /**
     * 在用非对称密钥查询
     */
    INUSE_ASYM_KEY_SEARCH,

    /**
     * 归档对称密钥查询
     */
    FILED_SYM_KEY_SEARCH,

    /**
     * 归档非对称密钥查询
     */
    FILED_ASYM_KEY_SEARCH,

    /**
     * 密钥应用查询
     */
    KEY_APPLY_SEARCH,

    /**
     * 密钥应用统计
     */
    KEY_APPLY_COUNT,

    /**
     * 司法密钥恢复
     */
    LEGAL_KEY_RECREATE,

    /**
     * 密钥操作审计
     */
    KEY_OPERATE_CHECK,

    /**
     * 应用系统管理
     */
    APPLY_SYS_MANAGE,

    /**
     * 操作日志查询
     */
    LOGS_SEARCH,

    /**
     * 操作日志审计
     */
    LOGS_CHECK,

    /**
     * 系统日志管理
     */
    LOGS_MANAGE
}
