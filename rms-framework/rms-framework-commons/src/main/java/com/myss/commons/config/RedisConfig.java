package com.myss.commons.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置类
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
@Configuration
public class RedisConfig {
    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //将对象序列化
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // json序列化配置
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //key采用String的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        //value的序列化方式采用jackson的方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的key也采用String 的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        //hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
