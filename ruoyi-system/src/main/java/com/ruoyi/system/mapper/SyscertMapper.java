package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Syscert;

/**
 * 系统证书Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-18
 */
public interface SyscertMapper
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
     * 删除系统证书
     *
     * @param certSn 系统证书主键
     * @return 结果
     */
    public int deleteSyscertByCertSn(String certSn);

    /**
     * 批量删除系统证书
     *
     * @param certSns 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSyscertByCertSns(String[] certSns);

    /**
     * 检查唯一性
     * @param certSn
     * @return
     */
    public int checkCertSnUnique(String certSn);



    public int checkUkeyIdUnique(String ukeyId);
}
