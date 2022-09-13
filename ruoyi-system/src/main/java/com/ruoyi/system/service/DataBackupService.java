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

    void downloadFile(String fileDirectory, String fileName, HttpServletResponse response);

    void restoreDataById(String id, String ip);

    void restoreData(String ip, MultipartFile file);
}
