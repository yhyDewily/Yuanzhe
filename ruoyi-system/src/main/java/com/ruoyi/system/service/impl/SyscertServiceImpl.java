package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.to.CertDataTO;
import com.ruoyi.system.utils.GetCertInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SyscertMapper;
import com.ruoyi.system.domain.Syscert;
import com.ruoyi.system.service.ISyscertService;

/**
 * 系统证书Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-18
 */
@Service
public class SyscertServiceImpl implements ISyscertService
{
    @Autowired
    private SyscertMapper syscertMapper;

    /**
     * 查询系统证书
     *
     * @param certSn 系统证书主键
     * @return 系统证书
     */
    @Override
    public Syscert selectSyscertByCertSn(String certSn)
    {
        return syscertMapper.selectSyscertByCertSn(certSn);
    }

    /**
     * 查询系统证书列表
     *
     * @param syscert 系统证书
     * @return 系统证书
     */
    @Override
    public List<Syscert> selectSyscertList(Syscert syscert)
    {
        return syscertMapper.selectSyscertList(syscert);
    }

    @Override
    public int insertSyscertByUser(SysUser sysuser) {
        Syscert syscert = new Syscert();
        String certSn = sysuser.getCertSn();
        if (certSn == null) {
            throw new RuntimeException("证书为空");
        }


        CertDataTO certDataTO = GetCertInfo.getCertSnFromCert(certSn);

        //获得解密后的序列号
        String certSn1 = certDataTO.getCertSn();



        //新增
        Date beginDate = certDataTO.getBeginDate();
        String certSDN = certDataTO.getCertSDN();
        Date endDate = certDataTO.getEndDate();

        syscert.setCertEntity(certSn);
        syscert.setCertDn(certSDN);
        syscert.setCertSn(certSn1);
        syscert.setCertType("KM管理证书");
        syscert.setCertStatus("INUSE");
        syscert.setKeyAlg("SM2");
        syscert.setKeyLen(256L);
        syscert.setNotbefore(beginDate);
        syscert.setNotafter(endDate);
        Long days = (Long) ((endDate.getTime() - beginDate.getTime()) / (1000*3600*24));

        syscert.setValidity(days);
        syscert.setDeviceId(sysuser.getUkeyId());
        syscert.setHardkeyId(1L);
        syscert.setImportTime(sysuser.getCreateTime());


        // todo校验证书编号和设备的唯一性。(或者用户校验证书和设备即可)
        if (UserConstants.NOT_UNIQUE.equals(this.checkCertSnUnique(certSn1))) {
            throw new RuntimeException("导入编号" + certSn1 + "的证书失败，证书已存在");
        }

        if (UserConstants.NOT_UNIQUE.equals(this.checkUkeyIdUnique(sysuser.getUkeyId())))
        {
            throw new RuntimeException("导入编号" + certSn1 + "的证书失败，该设备已被使用");
        }


        return syscertMapper.insertSyscert(syscert);
    }

    /**
     * 新增系统证书
     *
     * @param syscert 系统证书
     * @return 结果
     */
    @Override
    public int insertSyscert(Syscert syscert)
    {
        return syscertMapper.insertSyscert(syscert);
    }

    /**
     * 修改系统证书
     *
     * @param syscert 系统证书
     * @return 结果
     */
    @Override
    public int updateSyscert(Syscert syscert)
    {
        return syscertMapper.updateSyscert(syscert);
    }

    /**
     * 批量删除系统证书
     *
     * @param certSns 需要删除的系统证书主键
     * @return 结果
     */
    @Override
    public int deleteSyscertByCertSns(String[] certSns)
    {
        return syscertMapper.deleteSyscertByCertSns(certSns);
    }

    /**
     * 删除系统证书信息
     *
     * @param certSn 系统证书主键
     * @return 结果
     */
    @Override
    public int deleteSyscertByCertSn(String certSn)
    {
        return syscertMapper.deleteSyscertByCertSn(certSn);
    }

    @Override
    public String checkCertSnUnique(String certSn) {
        int count = syscertMapper.checkCertSnUnique(certSn);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE; //"1"
        }
        return UserConstants.UNIQUE; //"0"
    }

    @Override
    public String checkUkeyIdUnique(String ukeyId) {
        int count = syscertMapper.checkUkeyIdUnique(ukeyId);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE; //"1"
        }
        return UserConstants.UNIQUE; //"0"
    }
}
