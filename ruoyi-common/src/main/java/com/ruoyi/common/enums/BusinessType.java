package com.ruoyi.common.enums;

/**
 * 业务操作类型
 * 
 * @author ruoyi
 */
public enum BusinessType
{
    /**
     * 0.其它
     */
    OTHER,

    /**
     * 1.新增
     */
    INSERT,

    /**
     * 2.修改
     */
    UPDATE,

    /**
     * 3.删除
     */
    DELETE,

    /**
     * 4.授权
     */
    GRANT,

    /**
     * 5.导出
     */
    EXPORT,

    /**
     * 6.导入
     */
    IMPORT,

    /**
     * 7.强退
     */
    FORCE,

    /**
     * 8.生成代码
     */
    GENCODE,
    
    /**
     * 9.清空数据
     */
    CLEAN,

    /**
     * 10.查询操作员
     */
    FIND_OPERATOR,

    /**
     * 11.认证机构
     */
    AUTH_DEPT
}
