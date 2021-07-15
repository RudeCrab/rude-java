import UserAdd from "../pages/user/account/userAdd";
import RoleAdd from "../pages/user/role/roleAdd";

/**
 * 非菜单页面路由设置
 */
export default [
    {
        component: UserAdd,
        name: '添加账户',
        path:'/user/account/add'
    },
    {
        component: RoleAdd,
        name: '添加角色',
        path:'/user/role/add'
    }
]