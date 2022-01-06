package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CerMapper;
import com.ruoyi.system.domain.Cer;
import com.ruoyi.system.service.ICerService;

/**
 * 证书管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-05
 */
@Service
public class CerServiceImpl implements ICerService 
{
    @Autowired
    private CerMapper cerMapper;

    /**
     * 查询证书管理
     * 
     * @param version 证书管理主键
     * @return 证书管理
     */
    @Override
    public Cer selectCerByVersion(Long version)
    {
        return cerMapper.selectCerByVersion(version);
    }

    /**
     * 查询证书管理列表
     * 
     * @param cer 证书管理
     * @return 证书管理
     */
    @Override
    public List<Cer> selectCerList(Cer cer)
    {
        return cerMapper.selectCerList(cer);
    }

    /**
     * 新增证书管理
     * 
     * @param cer 证书管理
     * @return 结果
     */
    @Override
    public int insertCer(Cer cer)
    {
        return cerMapper.insertCer(cer);
    }

    /**
     * 修改证书管理
     * 
     * @param cer 证书管理
     * @return 结果
     */
    @Override
    public int updateCer(Cer cer)
    {
        return cerMapper.updateCer(cer);
    }

    /**
     * 批量删除证书管理
     * 
     * @param versions 需要删除的证书管理主键
     * @return 结果
     */
    @Override
    public int deleteCerByVersions(Long[] versions)
    {
        return cerMapper.deleteCerByVersions(versions);
    }

    /**
     * 删除证书管理信息
     * 
     * @param version 证书管理主键
     * @return 结果
     */
    @Override
    public int deleteCerByVersion(Long version)
    {
        return cerMapper.deleteCerByVersion(version);
    }
}
