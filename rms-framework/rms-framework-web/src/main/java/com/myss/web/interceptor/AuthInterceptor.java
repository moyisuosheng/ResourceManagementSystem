package com.myss.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.myss.commons.constants.Constants;
import com.myss.commons.model.vo.AuthInfo;
import com.myss.commons.utils.AuthInfoHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份验证拦截器
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {


    /**
     * 取出网关提供的payload
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return boolean
     * @throws Exception 例外
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.取出payload头
        String json = request.getHeader(Constants.PAYLOAD);
        //添加身份安全校验
//        if (StrUtil.isBlank(json)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return false; // 返回权限不足
//        }
        // 2.转为java对象
        AuthInfo authInfo = JSON.parseObject(json, AuthInfo.class);
        // 3.存入threadLocal
        AuthInfoHolder.setAuthInfo(authInfo);
        // 4.放行
        return true;
    }

    /**
     * 删除线程绑定
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @param ex       前任
     * @throws Exception 例外
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthInfoHolder.remove();
    }
}
