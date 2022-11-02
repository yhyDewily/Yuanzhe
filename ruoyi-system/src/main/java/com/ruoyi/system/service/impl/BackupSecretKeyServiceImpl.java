package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.BackupSecretKey;
import com.ruoyi.system.domain.SecretKey;
import com.ruoyi.system.mapper.BackupSecretKeyMapper;
import com.ruoyi.system.service.BackupSecretKeyService;
import com.ruoyi.system.service.SecretKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 密钥表 服务实现类
 * </p>
 *
 * @author lls
 * @since 2022-08-31
 */
@Service
@Slf4j
public class BackupSecretKeyServiceImpl extends ServiceImpl<BackupSecretKeyMapper, BackupSecretKey> implements BackupSecretKeyService {

    @Autowired
    BackupSecretKeyMapper backupSecretKeyMapper;

    @Autowired
    SecretKeyService secretKeyService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * TODO 测试异步情况和同步情况时间差，决定用哪个
     *
     * @param count 需要从备用密钥库中获取多少个密钥
     * @return
     */
    @DataSource(DataSourceType.BACKUP)
    @Override
    public List<BackupSecretKey> getKeyPairList(Integer count) {
        // 先拿到密钥数据，然后需要插入到在用库中，随后从备用库中删除这些数据
        List<BackupSecretKey> list = backupSecretKeyMapper.getKeyPairList(count);
        // 清除数据源信息，便于更改数据源操作
        DynamicDataSourceContextHolder.clearDataSourceType();
        // 修改为master在用库
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.MASTER.name());
        List<SecretKey> secretKeys = new ArrayList<>();
        CompletableFuture<Boolean> supplyAsync = CompletableFuture.supplyAsync(() -> {
            for (BackupSecretKey backupSecretKey : list) {
                SecretKey secretKey = new SecretKey();
                BeanUtils.copyProperties(backupSecretKey, secretKey);
                // TODO 设置证书序列号
                secretKey.setSerialNumber(BigInteger.probablePrime(64,new Random()).toString(16));
                secretKeys.add(secretKey);
            }
            return secretKeyService.saveBatch(secretKeys);
        }, threadPoolTaskExecutor);

        DynamicDataSourceContextHolder.clearDataSourceType();
        this.removeBatchByIds(list);
        log.info("操作完成{}", supplyAsync.join());

        return list;
    }

    @DataSource(DataSourceType.BACKUP)
    @Override
    public IPage<BackupSecretKey> getAllBackupKey(Long currentPage, Long pageSize) {
        return this.page(new Page<>(currentPage, pageSize));
    }


}
