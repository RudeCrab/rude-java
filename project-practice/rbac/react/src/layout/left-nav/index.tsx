import React, {useEffect, useState} from 'react';
import SubMenu from "antd/es/menu/SubMenu";
import {Menu} from "antd";
import {Link, withRouter} from "react-router-dom";
import menuConfig from "../../router/menuConfig";
import {CodepenOutlined} from '@ant-design/icons';
import LoginUtil from "../../utils/LoginUtil";


const LefNav = props => {
    // 当前选中的路由
    let currentPath = props.location.pathname;
    // 为了匹配非菜单页面
    menuConfig.forEach(item => {
        item.routes &&
        item.routes.forEach(child => {
            if (props.location.pathname.includes(child.path)) {
                currentPath = child.path;
            }
        })
    });
    // 当前展开的SubMenu
    const [openSub, setOpenSub] = useState([]);
    const handlerSubChange = (key) => {
        setOpenSub(key);
    }
    useEffect(() => {
        // 根据当前路由路径判断哪个SubMenu该展开
        menuConfig.forEach(item => {
            if (item.routes && item.routes.find(child => props.location.pathname.includes(child.path))) {
                setOpenSub([item.path]);
            }
        })
        // eslint-disable-next-line
    }, []);

    /**
     * 获取菜单路由
     * @param menu 菜单数组
     */
    const getMenuNodes = (menu = []) => (
        menu.map(item => {
            // 有子路由
            if (item.routes) {
                // 先查询出子菜单符合权限的个数，如果子菜单为0，就不要渲染父菜单
                const subs = getMenuNodes(item.routes).filter(i => i);
                return (
                    subs.length > 0 &&
                    <SubMenu key={item.path} icon={item.icon} title={item.name}>
                        {subs}
                    </SubMenu>
                );
            }

            // 无子路由
            // 判断权限
            return (
                (LoginUtil.getResourceIds().includes(item.resId) || item.path === '/') &&
                <Menu.Item key={item.path} icon={item.icon}>
                    <Link to={item.path}>{item.name}</Link>
                </Menu.Item>
            );
        })
    )


    return (
        <div className="sidebar">
            <div className="logo">
                <CodepenOutlined className="logo-icon"/>
                <span className="logo-title">
                    RudeCrab
                </span>
            </div>
            <Menu theme={props.theme} mode="inline"
                  defaultSelectedKeys={['/index']}
                  selectedKeys={[currentPath]}
                  openKeys={openSub}
                  onOpenChange={handlerSubChange}
            >
                {getMenuNodes(menuConfig)}
            </Menu>
        </div>
    );
};

// withRouter: 高阶组件: 包装非路由组件返回一个包装后的新组件, 新组件会向被包装组件传递
/**
 * 左侧导航栏，包含了路由
 */
export default withRouter(LefNav);
