package com.myss.web.feign;

import com.myss.commons.constants.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置Feign请求时，携带请求头
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            //设置授权头
            template.header(Constants.PAYLOAD, request.getHeader(Constants.PAYLOAD));
        }
    }
}