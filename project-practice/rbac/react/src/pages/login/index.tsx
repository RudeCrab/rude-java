import React from 'react';
import './index.less';
import {Form, Input, Button, message} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import Ajax from "../../api/Ajax";
import LoginUtil from "../../utils/LoginUtil";
import { Redirect } from 'react-router-dom';

// 表单验证完成执行登录逻辑
const onFinish = async values => {
    try {
        const body = await Ajax.post('/login', values);
        if (body.code === 0) {
            message.success('登录成功');
            console.log(body.data);
            LoginUtil.saveLoginUser(body.data);
            window.location.href = '/';
        }
    } catch (error) {
        console.log('出现错误：', error);
    }

};

const Login = props => {
    // 如果已经登录了直接跳转到首页
    if (LoginUtil.getLoginState()) return (<Redirect to='/'/>);
    // 没有登录才能进入到登录页面
    return (
        <div className='login'>
            <div className='login-content'>
                <h2>欢迎登录</h2>
                <Form
                    name="normal_login"
                    className="login-form"
                    initialValues={{remember: true}}
                    onFinish={onFinish}
                >
                    <Form.Item
                        name="username"
                        rules={[
                            {required: true, message: '请输入用户名'},
                            {min: 4, message: '最少输入4个字符'},
                            {max: 12, message: '最多输入12个字符'}
                        ]}
                    >
                        <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="用户名"/>
                    </Form.Item>
                    <Form.Item
                        name="password"
                        rules={[
                            {required: true, message: '请输入密码'},
                            {min: 4, message: '最少输入四个字符'},
                            {max: 12, message: '最多输入12个字符'},
                        ]}
                    >
                        <Input
                            prefix={<LockOutlined className="site-form-item-icon"/>}
                            type="password"
                            placeholder="密码"
                        />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" className="login-form-button">
                            登录
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    );

}

export default Login;
