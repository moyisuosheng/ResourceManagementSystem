package com.myss.web.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDatetime序列化，去除T字符
 *
 * @author moyis
 * @date 2023/04/25
 */
@Configuration
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
    /**
     * 日期格式
     */
    @Value("${spring.jackson.dateFormat}")
    private String pattern;

    /**
     * 序列化
     *
     * @param localDateTime      本地时间
     * @param jsonGenerator      创
     * @param serializerProvider 提供者
     * @throws IOException ioexception
     */
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        String s = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        jsonGenerator.writeString(s);
    }
}
