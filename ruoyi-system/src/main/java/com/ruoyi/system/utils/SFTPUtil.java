package com.ruoyi.system.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ruoyi.common.utils.SFTPChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

/**
 * SFTP协议相关工具类，主要包含了使用SFTP上传文件到指定服务器和从指定服务器下载文件
 *
 * @author lls
 * @Date 2022-09-10 15:43
 **/

@Slf4j
public class SFTPUtil {

    private static final String SERVER_IP = "192.168.8.169";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "197154";
    private static final Integer PORT = 222;

    /**
     * 下载文件，该方法是通过把服务器上的指定目录下的指定文件写到OutputStream流对象中，调用该方法时，传入流对象，然后再从该流对象中可以把文件内容读出来
     * outputStream.toString();就可以获得流对象中的字符串数据
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     */
    public static void download(String directory, String downloadFile, OutputStream outputStream) {
        // 创建远程服务器连接对象，其中存放了连接相关信息
        SFTPChannel sftpChannel = new SFTPChannel(SERVER_IP,
                USER_NAME, PASSWORD,
                PORT);
        ChannelSftp sftp = null;
        try {
            // 创建连接，把相关信息传递
            sftp = sftpChannel.getChannel(sftpChannel);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        try {
            // 进入该目录
            sftp.cd(directory);
            // 将文件信息写入到流对象中，调用该方法的地方可以直接从流对象中获取数据
            sftp.get(downloadFile, outputStream);
            if (log.isInfoEnabled()) {
                log.info("***************** Finished **********************");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将字符串上传到远程服务器指定目录
     *
     * @param sql        要上传的sql字符串
     * @param targetFile 要将字符串上传到远程服务器的哪个文件，必须是一个文件，当文件不存在时会自动创建
     */
    public static void remoteUpload(String sql, String targetFile) {
        // 通过sql文件创建一个字节数组输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sql.getBytes());
        //远程服务器：Ip、Root、Password、[Port(默认是22) 可选参数]
        SFTPChannel sftpChannel = new SFTPChannel(SERVER_IP,
                USER_NAME, PASSWORD,
                PORT);
        ChannelSftp channelSftp = null;
        try {
            // 创建远程服务器连接
            channelSftp = sftpChannel.getChannel(sftpChannel);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        try {
            // 调用put方法，传入字节流，指定上传到远程服务器哪个文件，可以拼接目录，例如/opt/kms/sql/1.sql，必须要是文件，如果不存在，会自动创建
            channelSftp.put(inputStream, targetFile, ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        //展示上传文件目录下的所有文件
//        Vector vector = channelSftp.ls(targetFile);
//        System.out.println(vector.toString());
        // 关闭连接
        channelSftp.quit();
        sftpChannel.closeChannel();
    }
}
