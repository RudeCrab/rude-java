import React, {useEffect} from 'react';
import Ajax from "../../api/Ajax";
import LocalStore from "../../utils/LocalStore";
import LoginUtil from "../../utils/LoginUtil";

interface Role {
    id: number,
    name: string
}

interface Resource {
    id: number,
    path: string,
    name: string,
    type: number
}

interface Company{
    id: number,
    name: string
}

const Index = props => {
    useEffect(() => {
        Ajax.get("/role/list").then(body => {
            // 将角色字典进行存储
            LocalStore.put('role', body.data);
        });

        Ajax.get("/resource/list").then(body => {
            // 将权限字典进行存储
            LocalStore.put('resource', body.data);
        });
        Ajax.get("/company/list").then(body => {
            // 将公司字典进行存储
            LocalStore.put('company', body.data);
        });

        Ajax.get("/user/resources/" + LoginUtil.getId()).then(body => {
            // 将当前用户权限进行更新
            LoginUtil.putResourceIds(body.data);
        });

        // eslint-disable-next-line
    }, []);

    return (
        <div>
            <h1>首页</h1>
            <div>
                <div>
                    博客、Github、公众号请认准<strong>RudeCrab</strong>，欢迎微信扫码关注：
                </div>
                <img src={require("../../assets/image/RudeCrab.png")} alt="微信二维码"/>
            </div>
        </div>
    );
};

export default Index;
