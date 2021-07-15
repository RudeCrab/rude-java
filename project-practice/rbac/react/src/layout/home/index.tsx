import React, {useState} from 'react';
import LoginUtil from "../../utils/LoginUtil";
import {Redirect} from 'react-router-dom';
import './index.less';
import {Route, Switch} from 'react-router-dom';


import {Layout} from 'antd';
import {
    MenuUnfoldOutlined,
    MenuFoldOutlined,
} from '@ant-design/icons';
import LefNav from "../../layout/left-nav";
import TopRightMenu from "../../layout/top-right-menu";
import menuConfig from "../../router/menuConfig";
import pageConfig from "../../router/pageConfig";

const {Header, Sider, Content} = Layout;


/**
 * 获取菜单路由
 * @param menu 菜单数组
 */
const getMenuNodes = (menu) => (
    // eslint-disable-next-line
    menu.map(item => {
        // 有子路由
        if (item.routes) {
            return (
                getMenuNodes(item.routes)
            )
        }

        if (item.component) {
            // 无子路由
            return (
                // 判断权限
                (LoginUtil.getResourceIds().includes(item.resId) || item.path === '/') &&
                <Route exact path={item.path} component={item.component} key={item.path}/>
            );
        }

    })
)

const getPageNodes = (pages) => (
    pages.map(item => {
        return (
            <Route exact path={item.path} component={item.component} key={item.path}/>
        );
    })
)


const Home = props => {
    const [collapsed, setCoolapsed] = useState(false);
    const toggle = () => {
        setCoolapsed(!collapsed);
    }

    // 如果没有登录就跳转登录页面
    if (!LoginUtil.getLoginState()) return (<Redirect to='/login'/>);
    // 登录了就正常渲染内容
    return (
        <Layout>
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <LefNav theme='dark'/>
            </Sider>
            <Layout className="site-layout">
                <Header className="site-layout-background" style={{padding: 0}}>
                    {React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
                        className: 'trigger',
                        onClick: toggle,
                    })}

                    <div className="box2">
                        <TopRightMenu/>
                    </div>
                </Header>
                <Content
                    className="site-layout-background"
                    style={{
                        margin: '16px',
                        padding: 24,
                        minHeight: 'auto'
                    }}
                >
                    <Switch>
                        {getMenuNodes(menuConfig)}
                        {getPageNodes(pageConfig)}
                        {/*匹配不到页面就跳转到404*/}
                        <Redirect to='/404'/>
                    </Switch>
                </Content>
            </Layout>
        </Layout>
    );
};


export default Home;
