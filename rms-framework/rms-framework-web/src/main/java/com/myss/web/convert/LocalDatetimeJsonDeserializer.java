package com.myss.web.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * LocalDatetime反序列化，去除T字符
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Configuration
public class LocalDatetimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

    @Value("${spring.jackson.dateFormat}")
    private String pattern;

    /**
     * 反序列化
     *
     * @param jsonParser             jsonParser
     * @param deserializationContext deserializationContext
     * @return {@link LocalDateTime}
     * @throws IOException ioexception
     */
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String str = jsonParser.getText();
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    }
}
