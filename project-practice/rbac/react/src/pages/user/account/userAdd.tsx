import React from 'react';
import {Button, Form, Input, message, TreeSelect} from "antd";
import {getRoleTree} from "../../../common/array";
import Ajax from "../../../api/Ajax";

const onFinish = values => {
    Ajax.post('/user', values).then(body => {
        if (body.code === 0) {
            message.success(body.data);
            window.location.href = '/#/user/account';
        }
    });
}
const UserAdd = porps => {
    return (
        <div>
            <h1>账户新增</h1>
            <Form
                labelCol={{span: 2}}
                wrapperCol={{span: 6}}
                onFinish={onFinish}
            >
                <Form.Item
                    label="用户名"
                    name="username"
                    rules={[
                        {required: true, message: '请输入用户名'},
                        {min: 4, message: '最少输入4个字符'},
                        {max: 12, message: '最多输入12个字符'}
                    ]}
                >
                    <Input placeholder='默认密码12345'/>
                </Form.Item>
                <Form.Item label="角色" name="roleIds">
                    <TreeSelect
                        treeData={getRoleTree()}
                        style={{width: '100%'}}
                        showCheckedStrategy={TreeSelect.SHOW_CHILD}
                        treeCheckable={true}
                        treeDefaultExpandAll={true}
                        placeholder='点击选择角色'
                    />
                </Form.Item>
                <Form.Item wrapperCol={{span: 6, offset: 2}}>
                    <Button style={{width:"50%"}} href='/#/user/account'>返回</Button>
                    <Button type="primary" htmlType="submit" style={{width:"50%"}}>确定</Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default UserAdd;
