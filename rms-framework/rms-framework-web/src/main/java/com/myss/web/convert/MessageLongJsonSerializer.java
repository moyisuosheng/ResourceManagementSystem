package com.myss.web.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Long反序列化，转为日期格式
 *
 * @author moyis
 * @date 2023/04/25
 */
@Configuration
public class MessageLongJsonSerializer extends JsonSerializer<Long> {
    /**
     * 日期格式
     */
    @Value("${spring.jackson.messageDateFormat}")
    private String pattern;

    /**
     * 序列化
     *
     * @param jsonGenerator      创
     * @param serializerProvider 提供者
     * @param time               毫秒值
     * @throws IOException ioexception
     */
    @Override
    public void serialize(Long time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        String s = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        jsonGenerator.writeString(s);
    }
}
