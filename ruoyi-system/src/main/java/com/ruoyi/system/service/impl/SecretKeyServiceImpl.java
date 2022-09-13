package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SecretKey;
import com.ruoyi.system.domain.vo.KeyPairVo;
import com.ruoyi.system.domain.vo.KeyTypePair;
import com.ruoyi.system.mapper.SecretKeyMapper;
//import com.ruoyi.system.service.ISecretKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.service.SecretKeyService;
import com.ruoyi.system.utils.SM2KeyPair;
import com.ruoyi.system.utils.SM2Util;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 密钥表 服务实现类
 * </p>
 *
 * @author lls
 * @since 2022-08-20
 */
@Service
public class SecretKeyServiceImpl extends ServiceImpl<SecretKeyMapper, SecretKey> implements SecretKeyService {

    private final Base64.Encoder encoder64 = Base64.getEncoder();

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // TODO 插入数据库时还有数据需要后续决定为何值
    @Override
    public Map<String, String> applyKeyPair(String type, String keyName) {
        // 先根据SM2算法生成密钥
        SM2KeyPair keyPair = SM2Util.generateorSM2KeyPair();
//        LocalDateTime creatTime = LocalDateTime.now();

        // 拿到公私钥参数
//        ECPublicKeyParameters publicKeyParam = keyPair.getPublicKeyParam();
//        ECPrivateKeyParameters privateKeyParam = keyPair.getPrivateKeyParam();

        // 获取到公私钥
        String publicKey = keyPair.convertPublicKeyXToString() + keyPair.convertPublicKeyYToString();
        String privateKey = keyPair.convertPrivatekeyDToString();
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("publicKey", publicKey);
        keyMap.put("privateKey", privateKey);
        // 为密钥表插入该密钥信息
        SecretKey secretKey = new SecretKey();
        secretKey.setKeyName(keyName);
        secretKey.setKeySymmetry(false);
        secretKey.setPublicKey(publicKey);
        secretKey.setPrivateKey(privateKey);
//        secretKey.setCreateTime(creatTime);
        secretKey.setSerialNumber("123456789");
        secretKey.setKeyType(type);
        this.save(secretKey);
        return keyMap;


        // 获取到公钥
//        String publicKey = encoder64.encodeToString(publicKeyParam.getQ().getAffineXCoord().getEncoded())
//                + encoder64.encodeToString(publicKeyParam.getQ().getAffineYCoord().getEncoded());
        // 获取到私钥
//        String privateKey = encoder64.encodeToString(privateKeyParam.getD().toByteArray());


    }

    @Override
    public Integer revokeKeyPair(String id) {
        // 根据id获取到当前密钥信息
        SecretKey secretKey = this.baseMapper.selectById(id);
        // 该密钥已经注销了
        if (!secretKey.getValid()) {
            return 0;
        } else {
            // 将密钥是否可用字段设置为false
            secretKey.setValid(false);
            // 根据id进行更新，返回受影响的行数
            return this.baseMapper.updateById(secretKey);
        }
    }

    @Override
    public SecretKey getKeyPairName(String type, String keyName) {
        QueryWrapper<SecretKey> queryWrapper = new QueryWrapper<SecretKey>();
        queryWrapper.eq("key_type", type).eq("key_name", keyName);
        SecretKey secretKey = this.baseMapper.selectOne(queryWrapper);
//        KeyPairVo keyPair = new KeyPairVo();
//        BeanUtils.copyProperties(secretKey, keyPair);
        return secretKey;
    }

    @Override
    public SecretKey getKeyPair(String id) {
        SecretKey secretKey = this.baseMapper.selectById(id);
//        KeyTypePair keyPair = new KeyTypePair();
//        BeanUtils.copyProperties(secretKey, keyPair);
//        keyPair.setType(secretKey.getKeyType());
        return secretKey;
    }


    @Override
    public Boolean backupStart(String backName) {

        // 先从在用库中查询出过去一小时到当前时间的记录
        QueryWrapper<SecretKey> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("create_time", LocalDateTime.now().minusHours(1)).le("create_time", LocalDateTime.now());
        List<SecretKey> list = this.list(queryWrapper);
        // 把这些记录插入到从库中
        SpringUtils.getAopProxy(this).insertKeyPairsToSlave(list);
        return null;
    }

    @DataSource(value = DataSourceType.SLAVE)
    public Boolean insertKeyPairsToSlave(List<SecretKey> list) {
//        CompletableFuture<Boolean> insert = CompletableFuture.supplyAsync(() -> {
//            return this.saveBatch(list);
//        }, threadPoolTaskExecutor);
        return this.saveBatch(list);
    }

    @Override
    public void moveExpireKey() {
        // 先从在用库中查询出已经过期的key和已经失效的key
        QueryWrapper<SecretKey> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("expire_time", LocalDateTime.now()).or().eq("valid", false);
        List<SecretKey> list = this.list(queryWrapper);
        // 把这些记录插入到历史库中
        Boolean flag = SpringUtils.getAopProxy(this).insertKeyPairsToSlave(list);
        if (flag) {
            // 插入到历史库成功后，再删除在用库中的这些密钥对
            this.removeBatchByIds(list);
        }
    }

    @Override
    public IPage<SecretKey> getAllUseKeyPair(Long currentPage, Long pageSize) {
        return this.page(new Page<>(currentPage, pageSize));
    }

    @Override
    public IPage<SecretKey> getKeyPariByCondition(Map<String, String> map, Long currentPage, Long pageSize) {
        String id = map.get("id");
        String keyType = map.get("keyType");
        String createBegin = map.get("createBegin");
        String createEnd = map.get("createEnd");
        String useBegin = map.get("useBegin");
        String useEnd = map.get("useEnd");
        String expireBegin = map.get("expireBegin");
        String expireEnd = map.get("expireEnd");
        QueryWrapper<SecretKey> queryWrapper = new QueryWrapper<>();
        if (id != null && !"".equals(id)) {
            queryWrapper.eq("id", id);
        }
        if (keyType != null && !"".equals(keyType)) {
            queryWrapper.eq("key_type", keyType);
        }
        if (createBegin != null && !"".equals(createBegin)) {
            queryWrapper.ge("create_time", createBegin);
        }
        if (createEnd != null && !"".equals(createEnd)) {
            queryWrapper.le("create_time", createEnd);
        }
        if (useBegin != null && !"".equals(useBegin)) {
            queryWrapper.ge("use_time", useBegin);
        }
        if (useEnd != null && !"".equals(useEnd)) {
            queryWrapper.le("use_time", useEnd);
        }
        if (expireBegin != null && !"".equals(expireBegin)) {
            queryWrapper.ge("expire_time", expireBegin);
        }
        if (expireEnd != null && !"".equals(expireEnd)) {
            queryWrapper.eq("expire_time", expireEnd);
        }
        return this.page(new Page<>(currentPage, pageSize), queryWrapper);
    }


}

