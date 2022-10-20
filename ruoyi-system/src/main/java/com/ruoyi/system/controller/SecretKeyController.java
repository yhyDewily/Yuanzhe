package com.ruoyi.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SecretKey;
import com.ruoyi.system.domain.vo.ApplyKeyVo;
import com.ruoyi.system.service.SecretKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 密钥表 前端控制器
 * </p>
 *
 * @author lls
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/kms/api")
@CrossOrigin
public class SecretKeyController {

    @Autowired
    SecretKeyService secretKeyService;

    /**
     * 非对称密钥生成，目前只使用了SM2算法生成密钥
     *
     * @param keyVo 包含 type和keyName
     *              type 算法类型，目前采用SM2算法
     *              keyName 密钥名称，默认为""，同一应用同一类型的keyName不可重复
     * @return 返回生成的公私钥对
     * TODO 对前端传入的type进行判断，根据字段来进行SM2算法生成密钥或者使用RSA算法生成密钥
     */
    @Log(title = "在用库密钥申请", businessType = BusinessType.EXPORT)
    @PostMapping("/applyKeyPair")
    public AjaxResult applyKeyPair(@RequestBody ApplyKeyVo keyVo) {
        Map<String, String> keyPair = secretKeyService.applyKeyPair(keyVo.getType(), keyVo.getKeyName());
        return AjaxResult.success("生成密钥成功", keyPair);
    }

    /**
     * 非对称密钥注销，优先根据id进行注销，如果id为空，则根据type和keyName进行注销
     *
     * @param id 密钥的唯一标识
     * @return 注销成功返回success，失败返回error
     */
    @Log(title = "在用库密钥注销", businessType = BusinessType.DELETE)
    @PostMapping("/revokeKeyPair")
    public AjaxResult revokeKeyPair(@RequestParam(value = "id") String id) {

        Integer keyPair = secretKeyService.revokeKeyPair(id);
        if (keyPair > 0) {
            return AjaxResult.success("注销密钥成功！");
        }
        return AjaxResult.error(10001, "注销密钥失败");
    }


    /**
     * 非对称密钥获取。根据密钥类型和密钥名称
     *
     * @param type    密钥类型
     * @param keyName 密钥名称
     * @return 查询到的密钥对应的公私钥
     */
    @Log(title = "非对称密钥获取", businessType = BusinessType.EXPORT)
    @GetMapping("/getKeyPairName")
    public AjaxResult getKeyPairName(@RequestParam String type, @RequestParam String keyName) {
        SecretKey keyPair = secretKeyService.getKeyPairName(type, keyName);
        return AjaxResult.success("查询成功", keyPair);
    }

    /**
     * 根据id获取非对称密钥
     *
     * @param id 前端传入的密钥id
     * @return 返回查询到的密钥对信息
     */
    @Log(title = "非对称密钥获取", businessType = BusinessType.EXPORT)
    @GetMapping("/getKeyPair")
    public AjaxResult getKeyPair(@RequestParam String id) {
        SecretKey keyPair = secretKeyService.getKeyPair(id);
        return AjaxResult.success("查询成功", keyPair);
    }

    /**
     * 密钥备份启动，备份过去一小时-当前时间段产生的密钥
     * TODO 该接口功能待定，后续需要修改
     * @param backName 备份名称
     * @return
     */
    @Log(title = "密钥备份", businessType = BusinessType.INSERT)
    @PostMapping("/backupStart")
    public AjaxResult backupStart(@RequestParam String backName) {
        secretKeyService.backupStart(backName);
        return AjaxResult.success();
    }

    /**
     * 从在用库中移除那些已经过期的key和已经失效的key，然后会将该密钥对插入到历史库中
     *
     * @return
     */
    @Log(title = "密钥移除", businessType = BusinessType.DELETE)
    @PostMapping("/moveExpireKey")
    public AjaxResult moveExpireKey() {
        secretKeyService.moveExpireKey();
        return AjaxResult.success();
    }

    /**
     * 分页获取所有的在用密钥对
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return
     */
    @Log(title = "获取所有的在用密钥对", businessType = BusinessType.EXPORT)
    @GetMapping("/getAllUseKeyPair")
    public AjaxResult getAllUseKeyPair(@RequestParam Long currentPage, @RequestParam Long pageSize) {
        IPage<SecretKey> list = secretKeyService.getAllUseKeyPair(currentPage, pageSize);
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 条件分页查询所有在用密钥对
     * @param map 条件map集合
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return
     */
    @Log(title = "获取所有的在用密钥对", businessType = BusinessType.EXPORT)
    @GetMapping("/getKeyPariByCondition")
    public AjaxResult getKeyPariByCondition(@RequestParam Map<String, String> map, @RequestParam Long currentPage, @RequestParam Long pageSize) {
        IPage<SecretKey> list = secretKeyService.getKeyPariByCondition(map, currentPage, pageSize);
        return AjaxResult.success("查询成功",list);
    }


}

