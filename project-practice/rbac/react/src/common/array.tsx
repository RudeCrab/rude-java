/**
 * 权限菜单，用于权限树选择
 */
import LocalStore from "../utils/LocalStore";

const menuTree = [
    {
        title: '用户管理',
        value: '',
        path: '/user',
        children: [
            {title: '账户管理', path: '/user/account', value: 1,},
            {title: '角色管理', path: '/user/role', value: 2}
        ]
    },
    {
        title: '数据管理',
        path: '/data',
        value: 3
    }
];

const getRoleTree = () => {
    const roleList = LocalStore.get('role');
    // 将角色字段转为树状图数据
    return roleList.map(role => {
        let roleNode = {title: null, value: null};
        roleNode.title = role.name;
        roleNode.value = role.id;
        return roleNode;
    });
}

const getCompanyTree = () => {
    const companyList = LocalStore.get('company');
    // 将角色字段转为树状图数据
    return companyList.map(role => {
        let companyNode = {title: null, value: null};
        companyNode.title = role.name;
        companyNode.value = role.id;
        return companyNode;
    });
}

const getResourceTree = (type: number) => {
    const resourceList = LocalStore.get('resource');
    // 将权限字段转为树状图数据
    return resourceList
        .filter(resource => resource.type === type)
        .map(resource => {
        let resourceNode = {title: null, value: null};
        resourceNode.title = resource.name;
        resourceNode.value = resource.id;
        return resourceNode;
    });
}


export {menuTree, getRoleTree, getResourceTree, getCompanyTree};
