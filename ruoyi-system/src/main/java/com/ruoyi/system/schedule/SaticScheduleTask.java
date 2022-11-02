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
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTask {

    @Autowired
    BackupSecretKeyService backupSecretKeyService;

    @Autowired
    BackupLogService backupLogService;

    final Integer MAX_KEYPAIR_NUM = 100;


    /**
     * 定时任务，检查备用库中密钥对数量，如果小于指定数量，就生成密钥对
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @DataSource(DataSourceType.BACKUP)
    public void generateKeyPair() {
        long count = backupSecretKeyService.count();
        log.info("表中数据{}", count);

        if (count < MAX_KEYPAIR_NUM) {
            // 密钥对已经不足，需要生成
            List<BackupSecretKey> list = new ArrayList<>();
            for (int i = 0; i < MAX_KEYPAIR_NUM - count; i++) {

                // 先根据SM2算法生成密钥
                SM2KeyPair keyPair = SM2Util.generateorSM2KeyPair();
                // 得到私钥参数
                ECPrivateKeyParameters ecPrivateKey = keyPair.getPrivateKeyParam();
                // 得到公钥参数
                ECPublicKeyParameters ecPublicKey = keyPair.getPublicKeyParam();
                // 得到公钥和私钥
                // 得到公钥和私钥
                BigInteger priKey = ecPrivateKey.getD();
                ECPoint pubKey = ecPublicKey.getQ();

                // 得到公钥和私钥的字节数组
                byte[] pubSm2Byte = pubKey.getEncoded(false);
                byte[] priSm2Byte = priKey.toByteArray();

                // 获取到公私钥
                String publicKey = Base64.getEncoder().encodeToString(pubSm2Byte);
                String privateKey = Base64.getEncoder().encodeToString(priSm2Byte);
                BackupSecretKey secretKey = new BackupSecretKey();
                String id = BigInteger.probablePrime(64, new Random()).toString(16);
                secretKey.setId(id)
                        .setKeySymmetry(false)
                        .setKeyType("SM2")
                        .setPublicKey(publicKey)
                        .setPrivateKey(privateKey)
                        .setKeyName(UUID.randomUUID().toString().substring(1, 8));
                log.info("产生第{}个密钥", i);
                list.add(secretKey);
            }
            BackupLog backupLog = new BackupLog();
            backupLog.setKeyNum((int) (MAX_KEYPAIR_NUM - count));
            backupLog.setKeyType("SM2");

            backupLogService.save(backupLog);
            backupSecretKeyService.saveBatch(list);
            log.info("成功插入到数据表");

        }
    }
}
