import React from 'react';
import {Button, Form, Input, message, TreeSelect} from "antd";
import {getResourceTree} from "../../../common/array";
import Ajax from "../../../api/Ajax";
import LocalStore from "../../../utils/LocalStore";

const onFinish = values => {
    const user = {"name":values.name, "resourceIds":[]};
    user.resourceIds = [].concat(values.menuIds || [], values.operateIds || []);
    Ajax.post('/role', user).then(body => {
        if (body.code === 0) {
            // 将本地的角色字典进行更新
            Ajax.get("/role/list").then(body => {
                LocalStore.put('role', body.data);
            });
            message.success(body.data);
            window.location.href = '/#/user/role';
        }
    });
}

const RoleAdd = props => {
    return (
        <div>
            <h1>角色添加</h1>
            <Form
                labelCol={{span: 2}}
                wrapperCol={{span: 6}}
                onFinish={onFinish}
            >
                <Form.Item
                    label="角色名"
                    name="name"
                    rules={[
                        {required: true, message: '请输入角色名'},
                        {max: 12, message: '最多输入12个字符'}
                    ]}
                >
                    <Input placeholder='请输入角色名'/>
                </Form.Item>
                <Form.Item label="页面权限" name="menuIds">
                    <TreeSelect
                        treeData={getResourceTree(0)}
                        style={{width: '100%'}}
                        showCheckedStrategy={TreeSelect.SHOW_CHILD}
                        treeCheckable={true}
                        treeDefaultExpandAll={true}
                        placeholder='点击选择页面权限'
                    />
                </Form.Item>
                <Form.Item label="操作权限" name="operateIds">
                    <TreeSelect
                        treeData={getResourceTree(1)}
                        style={{width: '100%'}}
                        showCheckedStrategy={TreeSelect.SHOW_CHILD}
                        treeCheckable={true}
                        treeDefaultExpandAll={true}
                        placeholder='点击选择操作权限'
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

export default RoleAdd;