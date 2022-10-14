package com.ruoyi.system.schedule;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.BackupLog;
import com.ruoyi.system.domain.BackupSecretKey;
import com.ruoyi.system.service.BackupLogService;
import com.ruoyi.system.service.BackupSecretKeyService;
import com.ruoyi.system.utils.SM2KeyPair;
import com.ruoyi.system.utils.SM2Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTask {

    @Autowired
    BackupSecretKeyService backupSecretKeyService;

    @Autowired
    BackupLogService backupLogService;


    /**
     * 定时任务，检查备用库中密钥对数量，如果小于指定数量，就生成密钥对
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @DataSource(DataSourceType.BACKUP)
    public void generateKeyPair() {
        long count = backupSecretKeyService.count();
        log.info("表中数据{}", count);
        Integer maxKeyPair = 100;
        if (count < maxKeyPair) {
            // 密钥对已经不足，需要生成
            List<BackupSecretKey> list = new ArrayList<>();
            for (int i = 0; i < maxKeyPair - count; i++) {
                // 先根据SM2算法生成密钥
                SM2KeyPair keyPair = SM2Util.generateorSM2KeyPair();
                // 获取到公私钥
                String publicKey = keyPair.convertPublicKeyXToString() + keyPair.convertPublicKeyYToString();
                String privateKey = keyPair.convertPrivatekeyDToString();
                BackupSecretKey secretKey = new BackupSecretKey();
                secretKey.setKeySymmetry(false);
                secretKey.setKeyType("SM2");
                secretKey.setPublicKey(publicKey);
                secretKey.setPrivateKey(privateKey);
                secretKey.setKeyName(UUID.randomUUID().toString().substring(1,8));
                log.info("产生第{}个密钥", i);
                list.add(secretKey);
            }
            BackupLog backupLog = new BackupLog();
            backupLog.setKeyNum((int) (maxKeyPair - count));
            backupLog.setKeyType("SM2");

            backupLogService.save(backupLog);
            backupSecretKeyService.saveBatch(list);
            log.info("成功插入到数据表");

        }
    }
}
