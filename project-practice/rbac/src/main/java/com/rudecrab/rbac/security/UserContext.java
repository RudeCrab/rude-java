package com.rudecrab.rbac.security;

/**
 * 用户上下文对象，方便在系统中任何地方都能获取用户信息
 *
 * @author RudeCrab
 */
public final class UserContext {
    private UserContext(){}

    private static final ThreadLocal<Long> USER = new ThreadLocal<>();

    public static void add(Long id) {
        USER.set(id);
    }

    public static void remove() {
        USER.remove();
    }

    /**
     * @return 当前登录用户的id
     */
    public static Long getCurrentUserId() {
        return USER.get();
    }
}
