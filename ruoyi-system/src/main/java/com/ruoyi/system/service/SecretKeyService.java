package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.SecretKey;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.vo.KeyPairVo;
import com.ruoyi.system.domain.vo.KeyTypePair;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 密钥表 服务类
 * </p>
 *
 * @author lls
 * @since 2022-08-20
 */
public interface SecretKeyService extends IService<SecretKey> {
    /**
     * 通过SM2算法生成密钥
     *
     * @param type    密钥类型，目前不填，为SM2类型
     * @param keyName 密钥名称
     * @return
     */
    Map<String, String> applyKeyPair(String type, String keyName);

    /**
     * 非对称密钥的注销
     *
     * @param id
     * @return
     */
    Integer revokeKeyPair(String id);


    /**
     * 非对称密钥获取，根据密钥类型和密钥名称获取
     *
     * @param type    密钥类型
     * @param keyName 密钥名称
     * @return
     */
    SecretKey getKeyPairName(String type, String keyName);

    /**
     * 根据id获取非对称密钥
     *
     * @param id 密钥id，唯一标识
     * @return 密钥对和密钥类型
     */
    SecretKey getKeyPair(String id);

    /**
     * TODO 该方法后续可能需要修改
     * @param backName
     * @return
     */
    Boolean backupStart(String backName);

    /**
     * 插入密钥集合到历史库中
     * @param list  密钥集合
     * @return 返回插入是否成功
     */
    public Boolean insertKeyPairsToSlave(List<SecretKey> list);

    /**
     * 从在用库中移除那些已经过期的key和已经失效的key，然后会将该密钥对插入到历史库中
     *
     * @return
     */
    void moveExpireKey();

    /**
     * 分页获取所有的在用密钥对
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return 分页对象
     */
    IPage<SecretKey> getAllUseKeyPair(Long currentPage, Long pageSize);


    /**
     * 条件分页查询所有在用密钥对
     *
     * @param map         条件map集合
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return 分页对象
     */
    IPage<SecretKey> getKeyPariByCondition(Map<String, String> map, Long currentPage, Long pageSize);
}
