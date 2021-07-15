import {
    UserOutlined,
    BlockOutlined,
    BarChartOutlined,
    DesktopOutlined,
    TeamOutlined
} from '@ant-design/icons';
import Account from "../pages/user/account";
import React from "react";
import Role from "../pages/user/role";
import Index from "../pages/index";
import Data from "../pages/data";

/**
 * 菜单路由设置
 */
export default [
    {
        path: '/',
        name: '首页',
        icon: <DesktopOutlined/>,
        component: Index
    },
    {
        path: '/user',
        name: '用户管理',
        icon: <TeamOutlined/>,
        routes: [
            {
                component: Account,
                icon: <UserOutlined/>,
                resId: 1,
                name: '账户管理',
                path: '/user/account'
            },
            {
                component: Role,
                icon: <BlockOutlined/>,
                resId: 2,
                name: '角色管理',
                path: '/user/role'
            },
        ]
    },
    {
        resId: 3,
        path: '/data',
        name: '数据管理',
        icon: <BarChartOutlined/>,
        component: Data
    }
]

