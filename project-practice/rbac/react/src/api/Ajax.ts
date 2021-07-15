import axios from 'axios';
import LoginUtil from "../utils/LoginUtil";
import {message} from "antd";

// axios基本设置
const axiosInstance = axios.create({
    baseURL:'http://localhost:8091/API'
});

// 请求拦截器
axiosInstance.interceptors.request.use(config => {
    if (LoginUtil.getLoginState()) {
        config.headers.Authorization = LoginUtil.getToken();
    }
    return config;
}, error => Promise.reject(error));

// 响应拦截器
axiosInstance.interceptors.response.use(response => {
    const {data} = response;
    // 没有登录
    if (data.code === 1001) {
        message.warn(data.data);
        LoginUtil.removeLoginState();
        window.location.href = '/';
    }
    // 接口异常
    if (data.code !== 0) {
        message.warn(data.data);
        return Promise.reject(response);
    }
    return Promise.resolve(response);
}, error => {
    message.error('系统错误');
    return Promise.reject(error)
});

export default class Ajax {

    /**
     * get请求
     * @param url 请求地址
     * @param params 请求配置
     */
    public static get(url: string, params?: any) {
        return axiosInstance
            .get(url, {params})
            .then(response => response.data);
    }

    /**
     * put请求
     * @param url 请求地址
     * @param data 请求数据
     */
    public static put(url: string, data?: any) {
        return axiosInstance
            .put(url, data)
            .then(response => response.data);
    }

    /**
     * post请求
     * @param url 请求地址
     * @param data 请求数据
     */
    public static post(url: string, data?: any) {
        return axiosInstance
            .post(url, data)
            .then(response => response.data);
    }

    /**
     * delete请求
     * @param url 请求地址
     * @param params 请求数据
     */
    public static delete(url: string, params? :any) {
        return axiosInstance
            .delete(url, {params})
            .then(response => response.data);
    }


}
