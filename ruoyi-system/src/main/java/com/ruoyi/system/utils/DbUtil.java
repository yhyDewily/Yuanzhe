package com.ruoyi.system.utils;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于数据库备份相关的工具类
 *
 * @author lls
 * @Date 2022-09-03 15:52
 **/
@Slf4j
public class DbUtil {

    // 加密密钥现在使用UUID
    static final String originKey = "abcd1234";


    /**
     * 获取连接下的所有数据库名称
     *
     * @param dbSetting 在db.setting中配置的数据源名称
     * @return 数据库名称集合
     */
    public static List<String> getDatabase(String dbSetting) {
        Db db = cn.hutool.db.DbUtil.use(DSFactory.get(dbSetting));
        String sql = "SHOW DATABASES;";
        List<String> data = getData(db, sql);
        data.remove("information_schema");
        data.remove("performance_schema");
        return data;
    }

    /**
     * 获取连接下指定数据库下的所有数据表名
     *
     * @param dbSetting 在db.setting中配置的数据源名称
     * @param dataBase  数据库名称
     * @return 当前数据库下的所有数据表集合
     */
    public static List<String> getDatatable(String dbSetting, String dataBase) {
        Db db = cn.hutool.db.DbUtil.use(DSFactory.get(dbSetting));
        String sql = "select table_name from information_schema.tables where table_schema='" + dataBase + "'";
        return getData(db, sql);
    }

    /**
     * 根据数据源和sql从数据源中获取数据 主要用于执行获取数据库名称和数据表的sql
     *
     * @param db  数据源
     * @param sql 要执行的sql语句，目前主要支持SHOW DATABASE和select table_name from information_schema.tables where table_schema='库名'
     * @return 字符串集合
     */
    private static List<String> getData(Db db, String sql) {
        List<String> list = new ArrayList<>();
        try {
            List<Entity> query = db.query(sql);
            for (Entity entity : query) {
                String tableName = entity.values().toString();
                tableName = tableName.substring(1, tableName.length() - 1);
                list.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 加密
     *
     * @param file
     * @return
     */
    public static String desEncriptSql(String file) {
        // 密文数据
        String cipherText = "";
        try {
            cipherText = DSCUtil.desEncript(file, originKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * 对文件进行解密
     *
     * @param cipherText 密文内容
     * @param originKey  加密用的key
     * @return 解析后的明文
     */
    public static String desDecriptSql(String cipherText, String originKey) {
        String cipherText2 = "";
        try {
            // 解密
            cipherText2 = DSCUtil.desDecript(cipherText, originKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText2;
    }

    /**
     * 对单表进行备份，生成SQL文件到fileName指定路径文件
     *
     * @param dbName          数据库名称
     * @param dbSetting       数据源配置
     * @param tableName       数据表名
     * @param create          是否需要创建语句
     * @param insert          是否需要插入语句
     * @param filedIgnoreList 要忽略哪些字段的列表
     */
    public static String generateSql(String dbSetting, String dbName, String tableName,
                                     Boolean create, Boolean insert, List<String> filedIgnoreList) {
        // 在之前要先把dbsetting中的配置数据库转换
        Db db = cn.hutool.db.DbUtil.use(DSFactory.get(dbSetting));
        try {
            db.execute("USE " + dbName + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> tables = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        if (tableName == null || "".equals(tableName)) {
            // 前端没有传入要备份的表名称，说明是对全库进行备份，需要先拿到该库下的所有表名称，然后进行循环备份
            tables.addAll(getDatatable(dbSetting, dbName));
        } else {
            // 前端传入了备份的表名称
            tables.add(tableName);
        }

        sql.append("CREATE DATABASE IF NOT EXISTS " + dbName + ";\n");
        sql.append("USE " + dbName + ";\n");
        for (String table : tables) {
            // 是否需要建表语句，如果是，则进入写建表语句
            if (create) {
                sql.append("SET NAMES utf8mb4;\n");
                sql.append("SET FOREIGN_KEY_CHECKS = 0;\n");
                // 追加三行空行
                sql.append("\n\n\n");
                // 根据sql文件规范，则先写drop语句
                // DROP TABLE
                sql.append("DROP TABLE IF EXISTS `" + table + "`;\n");
                // 写建表语句
                // CREATE TABLE
                // 查询出表结构
                Entity createTableEntity = null;
                try {
                    createTableEntity = db.queryOne("SHOW CREATE TABLE " + table);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//                log.info("当前表结构为：{}", createTableEntity);
                sql.append((String) createTableEntity.get("Create Table"));
                sql.append(";\n");
            }
            // 看配置，是否需要insert语句
            if (insert) {
                // INSERT INTO
                // 查询出该表中的所有数据，封装成为Entity实体类集合
                // TODO 可能需要根据备份时间来决定要查询出哪一部分的数据
                List<Entity> dataEntityList = null;
                try {
                    dataEntityList = db.query("SELECT * FROM " + table);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for (Entity dataEntity : dataEntityList) {
                    // StrBuilder 在hutoll6.x中移除了，因为StringBuilder已经完善
                    StrBuilder field = StrBuilder.create();
                    StrBuilder data = StrBuilder.create();

                    dataEntity.forEach((key, valueObj) -> {
                        String valueStr = StrUtil.toStringOrNull(valueObj);
                        // 看配置，某些列不需要
                        if (filedIgnoreList != null && filedIgnoreList.size() > 0) {
                            if (filedIgnoreList.contains(key)) {
                                return;
                            }
                        }
                        field.append("`").append(key).append("`").append(", ");
                        if (ObjectUtil.isNotNull(valueStr)) {
                            // 值包含 ' 转义处理
                            valueStr = StrUtil.replace(valueStr, "'", "\\'");
                            // boolean 值处理
                            if (StrUtil.equals("true", valueStr)) {
                                data.append("b'1'");
                            } else if (StrUtil.equals("false", valueStr)) {
                                data.append("b'0'");
                            } else {
                                data.append("'").append(valueStr).append("'");
                            }
                        } else {
                            data.append("NULL");
                        }
                        data.append(", ");
                    });

                    sql.append("INSERT INTO `" + table + "`(");
                    String fieldStr = field.subString(0, field.length() - 2);
                    sql.append(fieldStr);
                    sql.append(") VALUES (");
                    String dataStr = data.subString(0, data.length() - 2);
                    sql.append(dataStr);
                    sql.append(");\n");
                }
            }
            sql.append("\n\n\n");
            sql.append("SET FOREIGN_KEY_CHECKS = 1;\n");
            sql.append("\n\n\n");
        }
//        System.out.println(sql);
        String desEncriptSql = desEncriptSql(sql.toString());
        return desEncriptSql;
    }

    /**
     * 执行sql文件 完成备份
     *
     * @param dbSetting 数据源配置
     * @param sql       sql文件所在路径
     */
    public static void executeSql(String dbSetting, String sql) {
        try {
            Db db = cn.hutool.db.DbUtil.use(DSFactory.get(dbSetting));
            // 建立连接
            Connection conn = db.getConnection();
            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            // 读取sql文件生成String字符串
            // 对文件进行解密
            String execSql = desDecriptSql(sql, originKey);

            // 执行SQL脚本
            // 使用new StringReader(execSql)获得Reader对象
//            runner.runScript(new StringReader(sqlPath));
            runner.runScript(new StringReader(execSql));
            // 关闭连接
            conn.close();
            // 若成功，打印提示信息
            log.info("数据备份成功！");
        } catch (SQLException e) {
            log.info("执行SQL脚本出错！");
            e.printStackTrace();
        }
    }

    public static String readFileAsString(File file) {
        String str = "";
        try {
            FileInputStream in = new FileInputStream(file);
            // size 为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "utf-8");
        } catch (IOException e) {
            // todo auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }


    @Test
    public void testdemo() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")));
        Path path = Paths.get("src/main/resources/static/sql/", "bansys-t_log" + ".sql"); // 保存sql文件到src/main/resources/sql目录下
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             FileChannel channel = fos.getChannel();) {
            // 把字节数组写进缓冲区
            ByteBuffer buffer = ByteBuffer.wrap("bytes".getBytes());
            // 通过通道来传输缓冲区内容到指定位置
            channel.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
