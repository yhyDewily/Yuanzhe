package com.ruoyi.system.service;

import com.ruoyi.system.domain.DataBackup;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lls
 * @since 2022-09-08
 */
public interface DataBackupService extends IService<DataBackup> {

    /**
     * 执行数据备份，将sql文件上传到指定服务器的指定目录下
     * @param ip 前端传入的ip对应的db.setting中的名称
     * @param database 前端传入的数据库名称
     * @param datatable 数据表名称
     */
    void doBackupData(String ip, String database, String datatable);

    /**
     * 从远程服务器下载sql文件
     * @param fileDirectory 要下载的sql文件的目录
     * @param fileName 要下载的sql文件的名称
     * @param response 获得servletOutputStream对象，从而将文件内容写到前端
     */
    void downloadFile(String fileDirectory, String fileName, HttpServletResponse response);

    /**
     * 前端页面点击每一条备份记录选择要执行的备份记录
     * @param id 对应备份数据表中的记录的id，唯一标识，可以查询出该次备份产生的文件目录和文件名称
     */
    void restoreDataById(String id, String ip);

    /**
     * 该方法是用户选择sql文件和MySQL服务器ip地址，将sql文件进行执行
     * @param ip 用户传入的ip地址对应db.setting文件中的名称
     * @param file 用户上传的要执行恢复的sql文件
     */
    void restoreData(String ip, MultipartFile file);
}
