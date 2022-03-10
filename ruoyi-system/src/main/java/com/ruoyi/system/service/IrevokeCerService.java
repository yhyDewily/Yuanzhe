package com.ruoyi.system.service;


import com.ruoyi.system.domain.RevokeCer;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

/**
 * @Author: min
 * @Date: 2022/3/9
 * @Time: 11:12
 * @Description:
 */
public interface IrevokeCerService {

    /**
     * 查询撤销的证书列表
     *
     * @param
     * @return 撤销的证书集合
     */
    public List<RevokeCer> selectRevokeCerList();


    //数据库查询出来的撤销证书路径转化为这是对象，并且结果产生CRL
    public void GeCRL(int days) throws CertificateException, IOException;


}
