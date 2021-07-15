import React, {ReactNode} from 'react';
import LoginUtil from "../utils/LoginUtil";
import LocalStore from "../utils/LocalStore";

interface AuthProps {
    resourceId: number,
    children: ReactNode
}

const Auth: React.FunctionComponent<AuthProps> = props => {
    const {resourceId, children} = props;
    const resourceList = LocalStore.get('resource').map(i => i.id);
    // 如果资源字典里没有这个id，就代表此id已无需权限处理，直接返回组件
    if (!resourceList.includes(resourceId) || LoginUtil.getResourceIds().includes(resourceId)) {
        return (
            <>{children}</>
        );
    } else {
        return null;
    }
};

export default Auth;
