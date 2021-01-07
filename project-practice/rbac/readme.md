# 【项目实践】一文带你搞定页面权限、按钮权限以及数据权限

## 项目运行

本项目为`SpringBoot`搭建，没有使用安全框架的情况下手写的权限功能。虽然该项目可以直接访问前端页面，但是所有接口都是**前后端分离模式**，页面是我为了方便大家看到效果就整合到项目中的。

运行项目前，先建立数据库`rbac`，然后运行`sql`目录下的`rbac.sql`文件导入表结构和数据！

项目默认数据库连接配置为`127.0.0.1:3306/rbac`，账号`root`，密码`root`。可以对文件`application.yml`进行修改配置！

数据库环境准备好后就可以直接启动程序，程序启动完毕即可在浏览器输入`localhost:8080`查看页面！(端口配置也可以自行修改)：

![8080.png](http://ww1.sinaimg.cn/large/dcdff92dgy1gki64adnisj21hc0smhdt.jpg)

数据库中已经准备好账号`admin`、`root`等，密码和账号名一样！

## 文章讲解

https://juejin.im/post/6892918622230937613

![微信二维码](http://ww1.sinaimg.cn/large/dcdff92dgy1glnmky7fb7j20p00dwdig.jpg)