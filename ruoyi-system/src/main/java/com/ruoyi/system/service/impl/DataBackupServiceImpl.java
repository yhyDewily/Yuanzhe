package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.DataBackup;
import com.ruoyi.system.mapper.DataBackupMapper;
import com.ruoyi.system.service.DataBackupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.utils.DbUtil;
import com.ruoyi.system.utils.SFTPUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lls
 * @since 2022-09-08
 */
@Service
public class DataBackupServiceImpl extends ServiceImpl<DataBackupMapper, DataBackup> implements DataBackupService {

    private static final String DIRECTORY = "/opt/kms/sql/";

    private static final String HREF = "http://localhost:8080/kms/api/database/downloadFile?fileDirectory=/opt/kms/sql&fileName=";

    @Override
    public void doBackupData(String ip, String database, String datatable) {
        // 先根据前端传入的ip对应的配置名、数据库名、数据表名称和是否需要建表语句，是否需要数据，以及要忽略哪一些列来生成SQL语句，此时的SQL语句是已经加密了的
        String sql = DbUtil.generateSql(ip, database, datatable, true, true, null);
        // 拿到sql文件的字节数组
//        byte[] bytes = sql.getBytes();

        // 文件名称
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // new一个记录对象，其中包含了这次备份记录的文件名，数据库名，操作员名称，下载地址
        DataBackup dataBackup = new DataBackup();
        dataBackup.setDatabaseName(database);
        dataBackup.setOperater("操作员");
        dataBackup.setIp(ip);
        // 文件名根据前端是否选择是单表备份还是全表备份，如果是全表备份，则将数据表名替换为all
        if (datatable == null || "".equals(datatable)) {
            // 前端没有选择备份哪张表，说明对库全备份
            fileName = fileName + database + "-all.sql";
            dataBackup.setTableName("all");
        } else {
            fileName = fileName + database + "-" + datatable + ".sql";
            dataBackup.setTableName(datatable);
        }
        // 保存文件到远程服务器
        SFTPUtil.remoteUpload(sql, DIRECTORY + fileName);
        // 设置下载地址，带上目录和文件名
        String href = HREF+ fileName;
        dataBackup.setHref(href);
        dataBackup.setFileDirectory(DIRECTORY);
        dataBackup.setFileName(fileName);
        dataBackup.setOpType("导出");

        // 插入操作记录
        this.save(dataBackup);
    }

    @Override
    public void downloadFile(String fileDirectory, String fileName, HttpServletResponse response) {
        //将文件流写到前端，用户可以选择路径下载
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 此步会把要下载的文件内容写到os中
            SFTPUtil.download(fileDirectory, fileName, os);

//            os.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restoreDataById(String id, String ip) {
        // 先根据id查询出对应我们需要的文件目录和文件名称
        DataBackup backup = this.getById(id);
        String fileDirectory = backup.getFileDirectory();
        String fileName = backup.getFileName();
        OutputStream outputStream = new ByteArrayOutputStream();
        // 将文件内容保存到outputStream输出流中
        SFTPUtil.download(fileDirectory, fileName, outputStream);
        // 得到sql语句，此时是密文
        String sql = outputStream.toString();
        // 执行sql语句，其中会进行解密操作
        DbUtil.executeSql(ip, sql);

    }

    @Override
    public void restoreData(String ip, MultipartFile file) {
        String sql = "";
        try {
            InputStream in = file.getInputStream();
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            sql = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DbUtil.executeSql(ip, sql);
    }
}
