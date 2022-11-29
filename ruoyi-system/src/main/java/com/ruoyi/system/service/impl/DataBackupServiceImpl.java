package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.exception.yuanzhe.SqlTamperException;
import com.ruoyi.system.domain.DataBackup;
import com.ruoyi.system.mapper.DataBackupMapper;
import com.ruoyi.system.service.DataBackupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.utils.DbUtil;
import com.ruoyi.system.utils.SFTPUtil;
import com.ruoyi.system.utils.SM3Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
        // 对生成的sql进行一个hash操作，然后将得到的hash值存放到数据表中，在执行sql恢复的时候，如果文件内容进行hash不存在于表中，说明内容被篡改，不允许用户执行
        String sqlHash = SM3Util.dataDigest(sql.getBytes());

        // 文件名称
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // new一个记录对象，其中包含了这次备份记录的文件名，数据库名，操作员名称，下载地址
        DataBackup dataBackup = new DataBackup();
        dataBackup.setDatabaseName(database);
        dataBackup.setOperater("操作员");
        dataBackup.setIp(ip);
        dataBackup.setSqlEntity(sql);
        dataBackup.setSqlHash(sqlHash);
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
        String href = HREF + fileName;
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
        // 先根据id查询出对应的记录
        DataBackup backup = this.getById(id);
        // 从记录中获取sql实体
        String sql = backup.getSqlEntity();
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
        // 对sql做一次消息摘要拿到hash，判断其是否存在于数据表中，从而实现防止被篡改
        String sqlHash = SM3Util.dataDigest(sql.getBytes());
        QueryWrapper<DataBackup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sql_hash", sqlHash);
        DataBackup dataBackup = this.baseMapper.selectOne(queryWrapper);
        if (dataBackup == null) {
            // 此时说明数据表中没有当前sql文件对应的hash，说明文件已经被篡改了，不执行数据恢复操作，并且返回错误提示给前端
            throw new SqlTamperException();
        } else {
            DbUtil.executeSql(ip, sql);
        }
    }
}
