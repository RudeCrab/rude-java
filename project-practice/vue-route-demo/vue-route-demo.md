## 前言
大家都知道，后台管理界面不需要很酷炫的动画效果，也不需要花里胡哨的界面布局，只需要简洁易用、清爽明了的界面以便于管理数据。而现在普遍的后台管理系统的界面布局都差不多，上中下结构，然后左边是导航栏。随便贴两个Bootstrap的主题模板就是这样的：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200223115558.png)

这其中难的不是布局，而是如何点击左侧导航栏来渲染中央显示界面（路由）。在这里我会用Vue.js和ElementUI来快速搭建起这样的后台管理界面布局！

## 准备
本文搭建项目时的工具以及版本号如下：

> node.js  -- v12.16.1
>
> npm  -- 6.13.4
>
> @vue/cli  -- 4.2.2

版本有差异也没有事情，变化不会太大。

首先，通过Vue-cli工具来快速搭建起一个Vue的项目（这里就不讲解怎么用Vue-cli搭建项目了，文末有项目的github演示地址，下载下来即可运行）

项目搭建好后呢，接下来要导入我们要用的组件，我在这里会用到ElementUI和font-awesome图标（当然也可以直接使用ElementUI中的图标）。
使用npm来安装两个工具：
`npm install element-ui`
`npm install font-awesome`
安装完毕后，在`main.js`里导入两个工具，这样才能在项目中使用：

```javascript
import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 导入ElementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 导入font-awesome(导入就可以直接用了)
import 'font-awesome/scss/font-awesome.scss'

// 使用ElementUI
Vue.use(ElementUI); 

Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')

```
## 配置路由
所有都准备好了后，我们来修改App.vue文件，这个是整个项目的界面入口，所以我们在这里定义好最基本的视图：
```html
<template>
    <div id="app">
        <!--主路由视图-->
        <router-view/>
    </div>
</template>

<style lang="scss">
    // 整体布局样式，让整个视图都铺满
    html, body, #app {
        height: 100%;
        width: 100%;
        margin: 0;
        padding: 0;
    }
</style>
```
视图配置好后，接下来要配置路由设置，我们先新建四个页面组件：Main.vue，Index.Vue，Setting.vue，404.vue。这个等下都要用的，其中Index.Vue和Setting.vue都是Main.vue的嵌套路由，这里为了做演示，Index.vue和Setting.vue里面就只写一个简单的一级标题。此时我们的项目结构如下：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200223122235.png)



然后我们在router的js文件里开始配置路由，注意看注释：

```javascript
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        // 重定向，用来指向一打开网页就跳转到哪个路由
        path: '/',
        redirect: '/main'
    },
    {
        // 首页
        path: '/main',
        name: 'Main',
        component: () => import('../views/Main.vue'),
        children:[// 开始嵌套路由，这下面的所有路由都是Main路由的子路由
            {
                path:'/', // 嵌套路由里默认是哪个网页
                redirect: '/index'
            },
            {
                path:'/index', // 首页的路由
                name:'Index',
                component:() => import('../views/Index.vue')
            },
            {
                path:'/setting', // 设置页面的路由
                name:'Setting',
                component:() => import('../views/Setting.vue')
            }
        ]
    },
    {
        path:'/*', // 注意，这里不是嵌套路由了，这是为了设置404页面，一定要放在最后面，这样当服务器找不到页面的时候就会全部跳转到404
        name:'404',
        component: () => import('../views/404.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
```
路由是配置好了，接下来就是最重要的`Main.vue`里的布局

## 布局
我们先在`Main.vue`里布置最基本的结构，即上中下，中间又分左右：
```html
<template>
    <el-container>
        <!--顶部-->
        <el-header></el-header>

        <!--中央区域-->
        <el-main>
            <el-container>
                <!--左侧导航栏-->
                <el-aside></el-aside>
                <!--主内容显示区域，数据内容都是在这里面渲染的-->
                <el-main></el-main>
            </el-container>
        </el-main>

        <!--底部-->
        <el-footer></el-footer>
    </el-container>
</template>
```
这样最基本的布局就好了，我们接下来只需要在对应的区域渲染好内容就行，这里最主要的就是使用ElementUI其中的路由功能。

我们将`Main.vue`里的内容完整给写好，注意看注释：

```html
<template>
    <el-container>
        <!--顶部-->
        <el-header style="border-bottom: 1px solid gray;">
            <el-row style="margin: 10px 15px">
                <el-col :span="1">
                    <!--收缩条-->
                    <a href="#" @click="changeCollapse" style="font-size: 25px;color:#909399;"><i
                            :class="collpaseIcon"></i></a>
                </el-col>
            </el-row>
        </el-header>
        <!--中央区域-->
        <el-main>
            <el-container>
                <!--左侧导航栏-->
                <el-aside :style="{width:collpaseWidth}">
                    <!--default-active代表导航栏默认选中哪个index, :collapse决定导航栏是否展开，为boolean类型
                    :router决定导航栏是否开启路由模式，即在菜单item上设置路由是否生效，值为boolean类型-->
                    <el-menu
                            default-active="0"
                            class="el-menu-vertical-demo"
                            :collapse="isCollapse"
                            :router="true"
                    >
                        <!--index设置当前item的下标，:route则是传一个对象进行，指定路由-->
                        <el-menu-item index="0" :route="{name:'Index'}">
                            <i class="fa fa-magic"></i>
                            <span slot="title"> 首页</span>
                        </el-menu-item>

                        <el-submenu index="1">
                            <template slot="title">
                                <i class="fa fa-cogs"></i><span> 系统管理</span>
                            </template>

                            <el-menu-item index="/Setting" :route="{name:'Setting'}"><i class="fa fa-cog"></i> 网站设置
                            </el-menu-item>
                            <el-menu-item index="1-2"><i class="fa fa-user-circle-o"></i> 角色管理</el-menu-item>
                            <el-menu-item index="1-2"><i class="fa fa-object-group"></i> 店铺模板</el-menu-item>
                        </el-submenu>

                        <el-submenu index="2">
                            <template slot="title">
                                <i class="fa fa-users"></i>
                                <span> 会员管理</span>
                            </template>

                            <el-menu-item index="2-1" :route="{name:'Customer'}"><i class="fa fa-address-card-o"></i>
                                会员列表
                            </el-menu-item>
                            <el-menu-item index="2-2"><i class="fa fa-envelope-o"></i> 会员通知</el-menu-item>
                        </el-submenu>


                    </el-menu>

                </el-aside>
                <!--主内容显示区域-->
                <el-main>
                    <!--路由渲染-->
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-main>
        <!--底部-->
        <el-footer style="border-top: 1px solid gray"></el-footer>
    </el-container>
</template>

<script>
    // 这一大段JS就是为了做收缩/展开导航栏而用的！
    export default {
        name: "Main",
        data: function () {
            return {
                isCollapse: false, // 决定左侧导航栏是否展开
            }
        },
        computed: {
            collpaseIcon: function () { // 左侧导航栏是否展开状态的图标
                // 如果是展开状态就图标向右，否则图标向左
                return this.isCollapse ? 'el-icon-s-fold' : 'el-icon-s-unfold';
            },
            collpaseWidth: function () { // 左侧导航栏是否展开状态的宽度
                // 如果是展开状态就导航栏宽度为65px，否则200px
                return this.isCollapse ? '65px' : '200px';
            }
        },
        methods: {
            changeCollapse: function () { // 更改左侧导航栏展示状态
                this.isCollapse = !this.isCollapse;
            }
        }
    }
</script>

<style scoped>
    /*整体显示区域布局样式*/
    .el-container {
        height: 100%;
    }

    .el-header, .el-main {
        padding: 0;
    }

    /*左边导航栏具体样式*/
    .el-menu-vertical-demo.el-menu {
        padding-left: 20px;
        text-align: left;
        height: 100%;
        padding: 0;
    }

    el-container > .el-menu-vertical-demo.el-menu {
        padding: 0;
    }

    .el-submenu .el-menu-item, .el-menu-item {
        min-width: 50px;
    }

    .el-menu-item {
        padding: 0;
    }
</style>
```
这时候页面就已经做好了，我们来看下效果：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200223124115.gif)

项目github地址如下：

[https://github.com/RudeCrab/rude-java/tree/master/project-practice/vue-route-demo](https://github.com/RudeCrab/rude-java/tree/master/project-practice/vue-route-demo)

clone到本地即可运行，如果对你有帮助请在github上点个star，我还会继续更新更多【项目实践】哦！