package com.myss.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myss.commons.constants.Constants;
import com.myss.commons.model.vo.AuthInfo;
import com.myss.commons.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 用户权限校验全局过滤器
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {


    /**
     * Redis 模板
     */
    private final RedisTemplate<String, AuthInfo> redisTemplate;

    private final ObjectMapper objectMapper;

    /**
     * 过滤器
     *
     * @param exchange 交换
     * @param chain    链
     * @return {@link Mono}<{@link Void}>
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取request和response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 2.放行一部分url
        // 2-1 获取客户端请求url地址
        String path = request.getURI().getPath();
        // 2-2 判断部分地址放行
        if (path.contains("/rms-system/v1/public/user/login") ||
                path.contains("search/")) {
            return chain.filter(exchange);
        }
        // 3.获取请求头token
        String token = request.getHeaders().getFirst("Authorization");
        // 4.校验token合法性
        boolean verifyToken = JwtUtils.verifyToken(token);
        if (!verifyToken) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete(); // 返回权限不足
        }
        // 5.提取用户信息
        AuthInfo authInfo = JwtUtils.getInfoFromToken(token);
        // 6.校验token是否过期
        if (Boolean.FALSE.equals(redisTemplate.hasKey(Constants.User.USER_ID + authInfo.getUid()))) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete(); // 返回权限不足
        }
        // 7.续期
        Long expire = redisTemplate.getExpire(Constants.User.USER_ID + authInfo.getUid(), TimeUnit.MINUTES);
        if (null != expire && 30 > expire) {
            redisTemplate.expire(Constants.User.USER_ID + authInfo.getUid(), 180, TimeUnit.MINUTES);
        }
        // 8.将用户信息设置到request头
        ServerHttpRequest httpRequest = request.mutate().header(Constants.PAYLOAD, objectMapper.writeValueAsString(authInfo)).build();
        // 8-1 将新的request设置到exchange
        exchange.mutate().request(httpRequest);
        // 9.放行
        return chain.filter(exchange);
    }


    /**
     * 获取订单
     *
     * @return int
     */// 过滤器执行顺序
    @Override
    public int getOrder() {
        return -1;
    }
}
