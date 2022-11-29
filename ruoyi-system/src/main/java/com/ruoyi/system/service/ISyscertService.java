package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.Syscert;

/**
 * 系统证书Service接口
 *
 * @author ruoyi
 * @date 2022-11-18
 */
public interface ISyscertService
{
    /**
     * 查询系统证书
     *
     * @param certSn 系统证书主键
     * @return 系统证书
     */
    public Syscert selectSyscertByCertSn(String certSn);

    /**
     * 查询系统证书列表
     *
     * @param syscert 系统证书
     * @return 系统证书集合
     */
    public List<Syscert> selectSyscertList(Syscert syscert);

    /**
     * 通过用户新增证书
     * @param sysuser
     * @return
     */
    public int insertSyscertByUser(SysUser sysuser);

    /**
     * 新增系统证书
     *
     * @param syscert 系统证书
     * @return 结果
     */
    public int insertSyscert(Syscert syscert);

    /**
     * 修改系统证书
     *
     * @param syscert 系统证书
     * @return 结果
     */
    public int updateSyscert(Syscert syscert);

    /**
     * 批量删除系统证书
     *
     * @param certSns 需要删除的系统证书主键集合
     * @return 结果
     */
    public int deleteSyscertByCertSns(String[] certSns);

    /**
     * 删除系统证书信息
     *
     * @param certSn 系统证书主键
     * @return 结果
     */
    public int deleteSyscertByCertSn(String certSn);

    /**
     * 校验证书是否唯一
     *
     * @param certSn 证书序列
     * @return 结果
     */
    public String checkCertSnUnique(String certSn);

    /**
     * 校验ukeyid是否唯一
     */
    public String checkUkeyIdUnique(String ukeyId);
}
