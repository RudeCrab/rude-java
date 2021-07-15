import React, {useEffect, useState} from 'react';
import Ajax from "../../../api/Ajax";
import {Button, Modal, Space, Table, Tag, TreeSelect} from "antd";
import {ColumnsType, TablePaginationConfig} from "antd/es/table";
import {message} from "antd";
import "./index.less";
import {getRoleTree, getCompanyTree} from "../../../common/array";
import LocalStore from "../../../utils/LocalStore";
import Auth from "../../../common/Auth";


/**
 * 从后端接受的用户数据
 */
export interface User {
    id: number,
    username: string,
    roleIds: number[],
    companyIds: number[]
}

/**
 * 根据角色id获取对应的角色名称
 * @param roleId 角色id
 */
const getRoleTreeName = (roleId: number): string => {
    const role = LocalStore.get('role');
    let title = '';
    role.forEach(e => {
        if (e.id === roleId) {
            title = e.name;
            return title;
        }
    })
    return title;
}
/**
 * 根据公司id获取对应的角公司名称
 * @param companyId 公司id
 */
const getCompanyTreeName = (companyId: number): string => {
    const role = LocalStore.get('company');
    let title = '';
    role.forEach(e => {
        if (e.id === companyId) {
            title = e.name;
            return title;
        }
    })
    return title;
}

const Account = props => {
    const [users, setUsers] = useState<User[]>([]);
    const [confirmLoading, setConfirmLoading] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const [pagination, setPagination] = useState<TablePaginationConfig>({
        current: 1,
        total: 100,
        showSizeChanger: false
    });

    const [modalVisible, setModalVisible] = useState<boolean>(false);
    const [deleteVisible, setDeleteVisible] = useState<boolean>(false);
    const [selectedUser, setSelectedUser] = useState<User>(null);

    // 表设置
    const columns: ColumnsType<User> = [
        {
            title: '序号',
            dataIndex: 'order',
            key: 'order',
            align: 'center',
        },
        {
            title: '账户名',
            dataIndex: 'username',
            key: 'username',
            align: 'center',
        },
        {
            title: '角色',
            key: 'roleIds',
            dataIndex: 'roleIds',
            align: 'center',
            ellipsis: {
                showTitle: false,
            },
            render: roleIds => (
                <>
                    {roleIds.map(roleId => {
                        return (
                            <Tag color='geekblue' key={roleId}>
                                {getRoleTreeName(roleId)}
                            </Tag>
                        );
                    })}
                </>
            ),
        },
        {
            title: '公司',
            key: 'companyIds',
            dataIndex: 'companyIds',
            align: 'center',
            ellipsis: {
                showTitle: false,
            },
            render: companyIds => (
                <>
                    {companyIds.map(companyId => {
                        return (
                            <Tag color='green' key={companyId}>
                                {getCompanyTreeName(companyId)}
                            </Tag>
                        );
                    })}
                </>
            ),
        },
        {
            title: '操作',
            key: 'action',
            dataIndex: 'action',
            align: 'center',
            render: (text, user) => (
                <Space size="small">
                    <Auth resourceId={1003}>
                        <Button
                            size='small'
                            type='link'
                            onClick={e => {
                                setSelectedUser(user);
                                setModalVisible(true);
                            }}>编辑</Button>
                    </Auth>
                    <Auth resourceId={1002}>
                        <Button
                            size='small'
                            type='link'
                            danger
                            onClick={e => {
                                setSelectedUser(user);
                                setDeleteVisible(true);
                            }}>删除</Button>
                    </Auth>
                </Space>

            ),
        },
    ];

    /**
     * 表格变化调用的接口
     * @param pagination 分页属性
     */
    const handleTableChange = (pagination) => {
        setPagination({...pagination});
        fetch(pagination);
    };
    /**
     * 获取分页数据
     * @param pagination 分页属性
     */
    const fetch = (pagination) => {
        setLoading(true);
        Ajax.get('/user/page/' + pagination.current).then(body => {
            const users = body.data.records;
            users.forEach((item, index) => {
                item.key = index;
                item.order = index + 1;
            });
            // 给数据设置好key，好渲染组件
            setUsers(users);
            setPagination({...pagination, total: body.data.total});
            setLoading(false);
        });
    }

    // 当角色树状图更改时
    const roleTreeOnChange = value => {
        const tempUser = {...selectedUser};
        tempUser.roleIds = value;
        setSelectedUser(tempUser);
    }
    // 当公司树状图更改时
    const companyTreeOnChange = value => {
        const tempUser = {...selectedUser};
        tempUser.companyIds = value;
        setSelectedUser(tempUser);
    }

    // 当编辑按钮按下确定按钮
    const editHandleOk = () => {
        setConfirmLoading(true);
        Ajax.put('/user', selectedUser).then(body => {
                message.success(body.msg);
                setModalVisible(false);
                fetch(pagination);
            }
        ).finally(() => {
            setConfirmLoading(false)
        });
    }

    // 当删除模态框按下确定按钮
    const deleteHandleOk = () => {
        setConfirmLoading(true);
        const params = {"ids": [selectedUser.id] + ''};
        Ajax.delete('/user', params).then(body => {
                message.success(body.msg);
                setDeleteVisible(false);
                fetch({...pagination, current: 1});
            }
        ).finally(() => {
            setConfirmLoading(false);
        });
    }

    // 刚进入页面时访问一次数据
    useEffect(() => {
        fetch(pagination);
        // eslint-disable-next-line
    }, [])

    return (
        <div>
            <h1>账户管理</h1>
            <div className="tableTopButtons">
                <Auth resourceId={1001}>
                    <Button type="primary"
                            href="/#/user/account/add"
                    >
                        新增账户
                    </Button>
                </Auth>
            </div>
            <Table
                dataSource={users}
                columns={columns}
                loading={loading}
                pagination={pagination}
                onChange={handleTableChange}
            />
            <Modal
                title="权限编辑"
                visible={modalVisible}
                confirmLoading={confirmLoading}
                cancelText='取消'
                okText='确认'
                onOk={editHandleOk}
                onCancel={() => {
                    setModalVisible(false)
                }}
            >
                角色
                <TreeSelect
                    treeData={getRoleTree()}
                    value={selectedUser === null ? [] : selectedUser.roleIds}
                    style={{width: '100%'}}
                    showCheckedStrategy={TreeSelect.SHOW_CHILD}
                    treeCheckable={true}
                    onChange={roleTreeOnChange}
                    treeDefaultExpandAll={true}
                    placeholder='请选择角色'
                />
                公司
                <TreeSelect
                    treeData={getCompanyTree()}
                    value={selectedUser === null ? [] : selectedUser.companyIds}
                    style={{width: '100%'}}
                    showCheckedStrategy={TreeSelect.SHOW_ALL}
                    treeCheckable={true}
                    onChange={companyTreeOnChange}
                    treeDefaultExpandAll={true}
                    placeholder='请选择公司'
                />
            </Modal>

            <Modal
                title="删除"
                visible={deleteVisible}
                confirmLoading={confirmLoading}
                cancelText='取消'
                okText='确认'
                onOk={deleteHandleOk}
                onCancel={() => {
                    setDeleteVisible(false)
                }}
            >
                确定要删除【{selectedUser && selectedUser.username}】吗
            </Modal>
        </div>
    );
};

export default Account;
