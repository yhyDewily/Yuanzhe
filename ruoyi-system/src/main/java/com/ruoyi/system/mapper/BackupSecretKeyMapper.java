package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BackupSecretKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 密钥表 Mapper 接口
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
@Mapper
public interface BackupSecretKeyMapper extends BaseMapper<BackupSecretKey> {
    List<BackupSecretKey> getKeyPairList(Integer count);
}
