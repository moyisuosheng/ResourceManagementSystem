package com.myss.commons.utils;


import com.myss.commons.model.vo.AuthInfo;


/**
 * ThreadLocal工具类
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
public class AuthInfoHolder {

    private AuthInfoHolder() {
        throw new IllegalStateException("AuthInfoHolder class");
    }

    /**
     * 创建ThreadLocal
     */
    private static final ThreadLocal<AuthInfo> threadLocal = new ThreadLocal<>();


    /**
     * 设置当前线程中的用户
     *
     * @param authInfo 身份验证信息
     */
    public static void setAuthInfo(AuthInfo authInfo) {
        threadLocal.set(authInfo);
    }


    /**
     * 获取线程中的用户
     *
     * @return {@link AuthInfo}
     */
    public static AuthInfo getAuthInfo() {
        return threadLocal.get();
    }


    /**
     * 获取线程中的用户id
     *
     * @return {@link Long}
     */
    public static Long getUserId() {
        return threadLocal.get().getUid();
    }


    /**
     * 获取线程中的企业id
     *
     * @return {@link Long}
     */
    public static Long getCompanyId() {
        if (threadLocal.get() != null) {
            return threadLocal.get().getCompanyId();
        } else {
            return null;
        }
    }

    /**
     * 移除当前线程的用户
     */
    public static void remove() {
        threadLocal.remove();
    }

}
