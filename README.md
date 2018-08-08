# AppInternalTest
app内测分发

### 本项目主要用于将app分发给需要的人（主要是测试），可以理解为特简单版的蒲公英。使用SpringBoot开发，支持
1. 扫描二维码下载
2. 链接下载

### 下载该项目后需要增加或者修改
1. 创建数据库sql
```
DROP TABLE IF EXISTS `app_`;
CREATE TABLE `app_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) DEFAULT NULL,
  `create_date` varchar(255) DEFAULT NULL,
  `download_url` varchar(255) DEFAULT NULL,
  `md5_name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
```
2. 修改数据库url,用户名，密码
```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/app_internal_test?characterEncoding=UTF-8
spring.datasource.username = root
spring.datasource.password = root
```

3. 导出war包然后上传到服务器上



## 为啥要做这个东西？
网上这种app内测分发平台很多，但是绝大部分都需要验证身份证。我最讨厌的就是这个，所以就自己做了一个简单的。能实现需求就OK
