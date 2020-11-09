# 【项目实践】一文带你搞定页面权限、按钮权限以及数据权限

## 项目运行

本项目为`SpringBoot`搭建。

运行项目前，先建立数据库`rbac`，然后运行`sql`目录下的`rbac.sql`文件导入表结构和数据！

项目默认数据库连接配置为`127.0.0.1:3306/rbac`，账号`root`，密码`root`。可以对文件`application.yml`进行修改配置！

数据库环境准备好后就可以直接启动程序，程序启动完毕即可在浏览器输入`localhost:8080`查看页面！(端口配置也可以自行修改)：

![8080.png](http://ww1.sinaimg.cn/large/dcdff92dgy1gki64adnisj21hc0smhdt.jpg)

数据库中已经准备好账号`admin`、`root`等，密码和账号名一样！

## 文章讲解

https://juejin.im/post/6892918622230937613