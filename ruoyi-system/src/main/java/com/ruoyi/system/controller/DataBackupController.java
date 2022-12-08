package com.ruoyi.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.yuanzhe.SqlTamperException;
import com.ruoyi.system.domain.DataBackup;
import com.ruoyi.system.service.DataBackupService;
import com.ruoyi.system.utils.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lls
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/kms/api/database")
@PreAuthorize("@ss.hasRole('business_operator')")
public class DataBackupController {

    @Autowired
    DataBackupService dataBackupService;

    @GetMapping("/getAllData")
    public AjaxResult getAllData() {

        return AjaxResult.success();
    }

    /**
     * 根据指定ip获取其所有的数据库名称
     *
     * @param ip 前端传入的ip，其是在db.setting中配置的master-db-dev格式
     * @return 数据库名称集合
     */
    @Log(title = "根据IP地址查询数据库集合", businessType = BusinessType.EXPORT)
    @GetMapping("/getDatabaseByIp")
    public AjaxResult getDatabaseByIp(@RequestParam String ip) {
        List<String> database = DbUtil.getDatabase(ip);

        return AjaxResult.success(database);
    }

    /**
     * 根据ip和数据库名称获取其所有的数据表名称
     *
     * @param ip       ip对应的db.setting中的配置名称
     * @param database 数据库名称
     * @return 数据表集合
     */
    @Log(title = "根据数据库查询数据表集合", businessType = BusinessType.EXPORT)
    @GetMapping("/getDatatableByBase")
    public AjaxResult getDatatableByBase(@RequestParam String ip, @RequestParam String database) {
        List<String> datatable = DbUtil.getDatatable(ip, database);
        return AjaxResult.success(datatable);
    }

    /**
     * sql备份
     * 1、拿到加密的SQL语句
     * 2、拼接要上传到服务器的文件名称-时间戳-数据库-数据表/all
     * 3、将文件保存到远程服务器指定目录下
     * 该接口只负责将前端选择的需要备份的数据SQL生成一个文件到远程服务器的指定目录下，目前是/opt/kms下；并且在数据表中生成一条记录，来记录此次操作以及生成的文件的文件名和文件目录
     *
     * @param ip        主机地址
     * @param database  数据库名
     * @param datatable 数据表名
     * @return
     */
    @Log(title = "执行数据库备份", businessType = BusinessType.INSERT)
    @GetMapping("/doBackupData")
    public AjaxResult doBackupData(@RequestParam String ip, @RequestParam String database, @RequestParam String datatable) {
        dataBackupService.doBackupData(ip, database, datatable);
        return AjaxResult.success();
    }

    /**
     * 从远程服务器下载sql文件
     *
     * @param fileDirectory 要下载的sql文件的目录
     * @param fileName      要下载的sql文件的名称
     * @param response      获得servletOutputStream对象，从而将文件内容写到前端
     */
    @Log(title = "从服务器下载sql文件", businessType = BusinessType.EXPORT)
    @GetMapping("/downloadFile")
    public AjaxResult downloadFile(@RequestParam String fileDirectory, @RequestParam String fileName, HttpServletResponse response) {
        dataBackupService.downloadFile(fileDirectory, fileName, response);
        return AjaxResult.success();
    }

    @Log(title = "数据备份记录", businessType = BusinessType.EXPORT)
    @GetMapping("/getDataBackupRecords")
    public AjaxResult getDataBackupRecords(@RequestParam Long currentPage, @RequestParam Long pageSize) {
        IPage<DataBackup> page = dataBackupService.page(new Page<>(currentPage, pageSize));
        return AjaxResult.success(page);
    }


    /**
     * 该方法是用户选择sql文件和MySQL服务器ip地址，将sql文件进行执行
     *
     * @param ip   用户传入的ip地址对应db.setting文件中的名称
     * @param file 用户上传的要执行恢复的sql文件
     * @return
     */
    @Log(title = "数据库恢复操作", businessType = BusinessType.IMPORT)
    @PostMapping("/restoreData")
    public AjaxResult restoreData(@RequestParam String ip, @RequestParam("file") MultipartFile file) {
//        DbUtil.readFileAsString(file);
        if (ip == null || "".equals(ip)) {
            return AjaxResult.error("请选择要恢复数据的ip！");
        }
        if (file.isEmpty()) {
            return AjaxResult.error("请选择要恢复的文件！");
        }
        try{
            dataBackupService.restoreData(ip, file);
        }catch (SqlTamperException e){
            return AjaxResult.error(e.getDefaultMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 前端页面点击每一条备份记录选择要执行的备份记录
     *
     * @param id 对应备份数据表中的记录的id，唯一标识，可以查询出该次备份产生的文件目录和文件名称
     * @return
     */
    @Log(title = "根据记录id执行备份", businessType = BusinessType.INSERT)
    @PostMapping("/restoreDataById")
    public AjaxResult restoreDataById(@RequestParam String id, @RequestParam String ip) {
        dataBackupService.restoreDataById(id, ip);
        return AjaxResult.success();
    }


}
