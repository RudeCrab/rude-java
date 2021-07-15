const store = window.localStorage;

/**
 * 存储工具类
 */
class LocalStore {
    /**
     * 存放数据
     * @param key
     * @param value 如果是object就转换为json存储
     */
    public static put(key: string, value: any) {
        if (!store) {
            return;
        }
        let v = value;
        if (typeof value === 'object') {
            v = JSON.stringify(value);
        }
        store.setItem(key, v);
    }

    /**
     * 直接获取原数据
     * @param key
     */
    public static get(key: string) {
        if (!store) {
            return;
        }
        const item = store.getItem(key);
        try {
            return JSON.parse(item);
        } catch (e) {

        }
        return item;
    }

    /**
     * 删除数据
     * @param key
     */
    public static remove(key: string) {
        if (!store) {
            return;
        }
        store.removeItem(key);
    }
}

export default LocalStore;
