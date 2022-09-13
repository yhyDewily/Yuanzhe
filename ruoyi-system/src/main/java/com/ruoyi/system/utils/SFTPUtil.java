package com.ruoyi.system.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ruoyi.common.utils.SFTPChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * SFTP协议相关工具类，主要包含了使用SFTP上传文件到指定服务器和从指定服务器下载文件
 * @author lls
 * @Date 2022-09-10 15:43
 **/

@Slf4j
public class SFTPUtil {

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     */
    public static String download(String directory, String downloadFile, OutputStream outputStream) {
        SFTPChannel sftpChannel = new SFTPChannel("192.168.8.169",
                "root", "197154",
                222);
        ChannelSftp sftp = null;
        try {
            sftp = sftpChannel.getChannel(sftpChannel);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        String successFlg = "0";
        try {
            sftp.cd(directory);
            sftp.get(downloadFile, outputStream);
            if(log.isInfoEnabled()){
                log.info("***************** Finished **********************");
            }
        } catch (Exception e) {
            successFlg = "1";
            e.printStackTrace();
        }
        return successFlg;
    }


    /**
     * 将字符串上传到远程服务器指定目录
     *
     * @param sql        要上传的sql字符串
     * @param targetFile 要将字符串上传到远程服务器的哪个文件，必须是一个文件，当文件不存在时会自动创建
     */
    public static void remoteUplod(String sql, String targetFile) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sql.getBytes());
        //远程服务器：Ip、Root、Password、[Port(默认是22) 可选参数]
        SFTPChannel sftpChannel = new SFTPChannel("192.168.8.169",
                "root", "197154",
                222);
        ChannelSftp channelSftp = null;
        try {
            channelSftp = sftpChannel.getChannel(sftpChannel);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        System.out.println("创建链接");
        try {
            channelSftp.put(inputStream, targetFile, ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        System.out.println("上传文件成功");
        //展示上传文件目录下的所有文件
//        Vector vector = channelSftp.ls(targetFile);
//        System.out.println(vector.toString());
        //关闭连接
        channelSftp.quit();
        sftpChannel.closeChannel();
    }
}
