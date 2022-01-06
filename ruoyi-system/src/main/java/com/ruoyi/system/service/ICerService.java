package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Cer;

/**
 * 证书管理Service接口
 * 
 * @author ruoyi
 * @date 2022-01-05
 */
public interface ICerService 
{
    /**
     * 查询证书管理
     * 
     * @param version 证书管理主键
     * @return 证书管理
     */
    public Cer selectCerByVersion(Long version);

    /**
     * 查询证书管理列表
     * 
     * @param cer 证书管理
     * @return 证书管理集合
     */
    public List<Cer> selectCerList(Cer cer);

    /**
     * 新增证书管理
     * 
     * @param cer 证书管理
     * @return 结果
     */
    public int insertCer(Cer cer);

    /**
     * 修改证书管理
     * 
     * @param cer 证书管理
     * @return 结果
     */
    public int updateCer(Cer cer);

    /**
     * 批量删除证书管理
     * 
     * @param versions 需要删除的证书管理主键集合
     * @return 结果
     */
    public int deleteCerByVersions(Long[] versions);

    /**
     * 删除证书管理信息
     * 
     * @param version 证书管理主键
     * @return 结果
     */
    public int deleteCerByVersion(Long version);
}
