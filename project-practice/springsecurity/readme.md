# 【项目实践】一文带你搞定前后端分离下的认证和授权|Spring Security + JWT

## 项目运行

本项目为`SpringBoot`搭建，使用Spring Security完成认证和授权功能。虽然该项目可以直接访问前端页面，但是所有接口都是**前后端分离模式**，页面是我为了方便大家看到效果就整合到项目中的。

运行项目前，先建立数据库`ss`，然后运行`sql`目录下的`ss.sql`文件导入表结构和数据！

项目默认数据库连接配置为`127.0.0.1:3306/ss`，账号`root`，密码`root`。可以对文件`application.yml`进行修改配置！

数据库环境准备好后就可以直接启动程序，程序启动完毕即可在浏览器输入`localhost:8091`查看页面：

![8080.png](http://ww1.sinaimg.cn/large/dcdff92dgy1gki64adnisj21hc0smhdt.jpg)

数据库中已经准备好账号`admin`、`user1`等，密码都为`12345`！

## 文章讲解

https://juejin.cn/post/6900721218207350791

![微信二维码](http://ww1.sinaimg.cn/large/dcdff92dgy1glnmky7fb7j20p00dwdig.jpg)