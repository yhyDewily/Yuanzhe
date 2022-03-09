package com.ruoyi.system.mapper;


import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.RevokeCer;

import java.util.List;

/**
 * @Author: min
 * @Date: 2022/3/8
 * @Time: 17:23
 * @Description:
 */
@DataSource(DataSourceType.SLAVE)
public interface RevokeCerMapper {

    /**
     * 查询撤销的证书列表
     *
     * @param
     * @return 撤销的证书集合
     */
    public List<RevokeCer> selectRevokeCerList();
}
