import React from 'react';
import LoginUtil from "../../utils/LoginUtil";
import {Dropdown, Menu} from "antd";
import './index.less';
import {
    DownOutlined
} from '@ant-design/icons';

const menu = (
    <Menu>
        <Menu.Item onClick={() => {
            LoginUtil.removeLoginState();
            window.location.href = '/';
        }}>
            退出登录
        </Menu.Item>
    </Menu>
);

/**
 * 顶部右边菜单栏
 */
const TopRightMenu = props => {
    return (
        <Dropdown overlay={menu}>
            <span>
            {LoginUtil.getUsername()} <DownOutlined/>
            </span>
        </Dropdown>
    );
};

export default TopRightMenu;
