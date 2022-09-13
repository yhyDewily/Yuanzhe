package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.BackupSecretKey;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SecretKey;

import java.util.List;

/**
 * <p>
 * 密钥表 服务类
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
public interface BackupSecretKeyService extends IService<BackupSecretKey> {

    /**
     * 从备用库中获取批量密钥
     * @param count 获取密钥长度
     * @return 密钥集合
     */
    List<BackupSecretKey> getKeyPairList(Integer count);

    IPage<BackupSecretKey> getAllBackupKey(Long currentPage,Long pageSize);

}
