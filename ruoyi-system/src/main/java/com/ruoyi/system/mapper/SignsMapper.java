package com.ruoyi.system.mapper;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.Signs;

import java.util.List;

@DataSource(DataSourceType.SLAVE)
public interface SignsMapper{

    List<Signs> selectSigns();

    Integer selectRevokeStatusBySerialNumber(String serial_number);
}
