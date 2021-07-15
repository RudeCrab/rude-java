import React, {useEffect, useState} from 'react';
import {ColumnsType, TablePaginationConfig} from "antd/es/table";
import {Button, message, Modal, Space, Table, Tag, TreeSelect} from "antd";
import LocalStore from "../../../utils/LocalStore";
import Ajax from "../../../api/Ajax";
import {getResourceTree} from "../../../common/array";
import Auth from "../../../common/Auth";

/**
 * 从后端接受的角色数据
 */
export interface Role {
    id: number,
    name: string,
    resourceIds: number[],
    menusIds: number[],
    operateIds: number[]
}

/**
 * 根据权限id获取对应的权限资源名称
 * @param resourceId 权限资源id
 */
const getResourceTreeName = (resourceId: number): string => {
    // 根据存储在本地的字典进行名称判断
    const resource = LocalStore.get('resource');
    let title = '';
    resource.forEach(e => {
        if (e.id === resourceId) {
            title = e.name;
            return title;
        }
    })
    return title;
}

/**
 * 根据权限id判断是否为指定权限类型
 * @param resourceId 权限资源id
 * @param type 资源类型，0为菜单，1为操作
 */
const isResourceType = (resourceId: number, type: number): boolean => {
    // 根据存储在本地的字典进行名称判断
    const resource = LocalStore.get('resource');
    let flag = false;
    resource.forEach(e => {
        if (e.type === type && e.id === resourceId) {
            flag = true;
            return flag;
        }
    })
    return flag;
}

const Role = props => {
    const [roles, setRoles] = useState<Role[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [confirmLoading, setConfirmLoading] = useState<boolean>(false);
    const [pagination, setPagination] = useState<TablePaginationConfig>({
        current: 1,
        total: 100,
        showSizeChanger: false
    });
    const [modalVisible, setModalVisible] = useState<boolean>(false);
    const [deleteVisible, setDeleteVisible] = useState<boolean>(false);
    const [selectedRole, setSelectedRole] = useState<Role>(null);


    // 表设置
    const columns: ColumnsType<Role> = [
        {
            title: '序号',
            dataIndex: 'order',
            key: 'order',
            align: 'center',
        },
        {
            title: '角色名',
            dataIndex: 'name',
            key: 'name',
            align: 'center',
        },
        {
            title: '页面权限',
            key: 'menu',
            dataIndex: 'resourceIds',
            align: 'center',
            ellipsis: {
                showTitle: false,
            },
            render: resourceIds => (
                <>
                    {resourceIds
                        .filter(e => isResourceType(e, 0))
                        .map(resourceId => {
                            return (
                                <Tag color='geekblue' key={resourceId}>
                                    {getResourceTreeName(resourceId)}
                                </Tag>
                            );
                        })}
                </>
            ),
        },
        {
            title: '操作权限',
            key: 'operate',
            dataIndex: 'resourceIds',
            align: 'center',
            ellipsis: {
                showTitle: false,
            },
            render: resourceIds => (
                <>
                    {resourceIds
                        .filter(e => isResourceType(e, 1))
                        .map(resourceId => {
                            return (
                                <Tag color='green' key={resourceId}>
                                    {getResourceTreeName(resourceId)}
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
            render: (text, role) => (
                <Space size="small">
                    <Auth resourceId={2003}>
                        <Button
                            size='small'
                            type='link'
                            onClick={e => {
                                role.menusIds = role.resourceIds.filter(i => isResourceType(i, 0));
                                role.operateIds = role.resourceIds.filter(i => isResourceType(i, 1));
                                setSelectedRole(role);
                                setModalVisible(true);
                            }}>编辑</Button>
                    </Auth>
                    <Auth resourceId={2002}>
                        <Button
                            size='small'
                            type='link'
                            danger
                            onClick={e => {
                                setSelectedRole(role);
                                setDeleteVisible(true);
                            }}>删除</Button>
                    </Auth>
                </Space>
            ),
        },
    ];

    /**
     * 获取分页数据
     * @param pagination 分页属性
     */
    const fetch = (pagination) => {
        setLoading(true);
        Ajax.get('/role/page/' + pagination.current).then(body => {
            const roles = body.data.records;
            roles.forEach((item, index) => {
                item.key = index;
                item.order = index + 1;
            });
            // 给数据设置好key，好渲染组件
            setRoles(roles);
            setPagination({...pagination, total: body.data.total});
            setLoading(false);
        });
    }

    // 刚进入页面时访问一次数据
    useEffect(() => {
        fetch(pagination);
        // eslint-disable-next-line
    }, []);

    /**
     * 表格变化调用的接口
     * @param pagination 分页属性
     */
    const handleTableChange = (pagination) => {
        setPagination({...pagination});
        fetch(pagination);
    };

    // 当权限树状图更改时
    const menuTreeOnChange = value => {
        const tempRole = {...selectedRole};
        tempRole.menusIds = value;
        tempRole.resourceIds = tempRole.operateIds.concat(value);
        setSelectedRole(tempRole);
    }
    // 当权限树状图更改时
    const operateTreeOnChange = value => {
        const tempRole = {...selectedRole};
        tempRole.operateIds = value;
        tempRole.resourceIds = tempRole.menusIds.concat(value);
        setSelectedRole(tempRole);
    }

    // 当编辑按钮按下确定按钮
    const editHandleOk = () => {
        setConfirmLoading(true);
        Ajax.put('/role', selectedRole).then(body => {
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
        const params = {"ids": [selectedRole.id] + ''};
        Ajax.delete('/role', params).then(body => {
                message.success(body.msg);
                setDeleteVisible(false);
                fetch({...pagination, current: 1});
            }
        ).finally(() => {
            setConfirmLoading(false);
        });
    }


    return (
        <div>
            <h1>角色管理</h1>
            <div className="tableTopButtons">
                <Auth resourceId={2001}>
                    <Button type="primary"
                            href="/#/user/role/add"
                    >
                        新增角色
                    </Button>
                </Auth>
            </div>
            <Table
                dataSource={roles}
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
                页面权限
                <TreeSelect
                    treeData={getResourceTree(0)}
                    value={selectedRole === null ? [] : selectedRole.menusIds}
                    style={{width: '100%'}}
                    showCheckedStrategy={TreeSelect.SHOW_CHILD}
                    treeCheckable={true}
                    onChange={menuTreeOnChange}
                    treeDefaultExpandAll={true}
                    placeholder='请选择页面权限'
                />

                操作权限
                <TreeSelect
                    treeData={getResourceTree(1)}
                    value={selectedRole === null ? [] : selectedRole.operateIds}
                    style={{width: '100%'}}
                    showCheckedStrategy={TreeSelect.SHOW_CHILD}
                    treeCheckable={true}
                    onChange={operateTreeOnChange}
                    treeDefaultExpandAll={true}
                    placeholder='请选择操作权限'
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
                确定要删除【{selectedRole && selectedRole.name}】吗
            </Modal>
        </div>
    );
};

export default Role;
