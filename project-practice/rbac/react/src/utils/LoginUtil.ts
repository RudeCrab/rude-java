import LocalStore from "./LocalStore";

const idKey = 'id';
const tokenKey = 'login-jwt';
const usernameKey = 'username';
const resourceIdsKey = 'resourceIds';

interface User {
    id: number
    username: string,
    token: string,
    resourceIds: number[]
}

export default {
    /**
     * 保存当前登录用户信息
     * @param user 用户对象
     */
    saveLoginUser(user: User) {
        LocalStore.put(tokenKey, user.token);
        LocalStore.put(idKey, user.id);
        LocalStore.put(usernameKey, user.username);
        LocalStore.put(resourceIdsKey, user.resourceIds);
    },

    /**
     * 获取登录状态
     */
    getLoginState() {
        return !!LocalStore.get(tokenKey);
    },
    /**
     * 移除登录状态
     */
    removeLoginState() {
      LocalStore.remove(tokenKey);
      LocalStore.remove(idKey);
      LocalStore.remove(usernameKey);
      LocalStore.remove(resourceIdsKey);
    },

    /**
     * 获取当前登录的token
     */
    getToken() {
        return LocalStore.get(tokenKey);
    },
    getId() {
        return LocalStore.get(idKey);
    },

    getUsername() {
        return LocalStore.get(usernameKey);
    },

    getResourceIds() {
        return LocalStore.get(resourceIdsKey);
    },

    putResourceIds(resourceIds: number[]) {
        LocalStore.put(resourceIdsKey, resourceIds);
    }
}
