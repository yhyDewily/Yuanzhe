package com.ruoyi.common.utils;

import com.jcraft.jsch.*;

import java.util.Properties;


/*添加依赖
*  <!-- https://mvnrepository.com/artifact/com.jcraft/jsch -->
        <dependency>
            <groupId>com.jcraft</groupId>
            <artifactId>jsch</artifactId>
            <version>0.1.54</version>
        </dependency>
*/
//上传文件到远程服务器上
public class SFTPChannel {
    //ip地址
    private String host;
    //登录账号
    private String username;
    //登录密码
    private String password;
    //默认端口号
    private int port = 22;
    //默认过期时间
    private int timeout = 60000;
    private Session session = null;
    private Channel channel = null;

    public SFTPChannel(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public SFTPChannel(String host, String username, String password, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public SFTPChannel(String host, String username, String password, int port, int timeout) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.timeout = timeout;
    }

    //创建连接
    public ChannelSftp getChannel(SFTPChannel sftpChannel) throws JSchException {
        // 创建JSch对象
        JSch jSch = new JSch();
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jSch.getSession(sftpChannel.getUsername(), sftpChannel.getHost(), sftpChannel.getPort());
        // 设置密码
        session.setPassword(sftpChannel.getPassword());
        Properties properties = new Properties();
        //主机公钥确认 无口令 SSH 登录（即通过客户端公钥认证），就可以直接连接到远程主机。
        //这是基于 SSH 协议的自动化任务常用的手段
        properties.put("StrictHostKeyChecking", "no");
        // 为Session对象设置properties
        session.setConfig(properties);
        // 设置timeout时间
        session.setTimeout(sftpChannel.getTimeout());
        // 通过Session建立链接
        session.connect();
        // 打开SFTP通道
        channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        return (ChannelSftp) channel;
    }

    //关闭连接
    public void closeChannel() {
        if (null != channel) {
            channel.disconnect();
        }
        if (null != session) {
            session.disconnect();
        }
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }



}
