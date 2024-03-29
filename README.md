<h4 align="center">基于SpringBoot+Vue前后端分离的Java快速开发框架</h4>
<p align="center">
	<a href="https://gitee.com/y_project/RuoYi-Vue/stargazers"><img src="https://gitee.com/y_project/RuoYi-Vue/badge/star.svg?theme=dark"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue"><img src="https://img.shields.io/badge/RuoYi-v3.8.1-brightgreen.svg"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## 平台简介

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。

## 配置说明
* 数据库配置在ruoyi-admin中application-druid.yml中
* 根证书的参数在ruoyi-admin中application.yml中，参数为ocspConfiguration，CAPath是根证书的路径，KeyPath是密钥路径
* 需要开启redis，redis的配置在application.yml中
* 主数据库的为本地数据库，详情参见/sql/yuanzhe.sql文件 

## 系统说明
* 密钥管理系统按照国标GB/T 25056-2018的标准实现
* 整体业务分为，密钥生成，密钥库管理，密钥恢复，密码服务，密钥管理，安全审计，认证管理
* 其中密钥管理部分需要与CA部分进行交互


