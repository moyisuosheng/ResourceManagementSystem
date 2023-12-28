package com.myss.web.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * Long反序列化，日期格式转为Long
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Configuration
public class LongJsonDeserializer extends JsonDeserializer<Long> {

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
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        Instant instant = LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(pattern)).
                atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }
}
