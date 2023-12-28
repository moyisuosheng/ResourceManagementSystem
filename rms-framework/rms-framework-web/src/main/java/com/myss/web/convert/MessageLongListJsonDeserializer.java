package com.myss.web.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Long反序列化，日期格式转为Long
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/04/26
 */
@Configuration
public class MessageLongListJsonDeserializer extends JsonDeserializer<List<Long>> {

    @Value("${spring.jackson.messageDateFormat}")
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
    public List<Long> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        if (ObjectUtils.isEmpty(jsonParser)) {
            return Collections.emptyList();
        }
//        对于集合类型getText()方法是不行的
//        jsonParser.getText()
//        此处是重点：VO对应的属性是什么类型,此处的参数就什么类型
        List<String> list = jsonParser.readValueAs(List.class);
        List<Long> dates = new ArrayList<>();
        for (String date : list) {
            Instant instant = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern)).
                    atZone(ZoneId.systemDefault()).toInstant();
            Long instantEpochMilli = instant.toEpochMilli();
            dates.add(instantEpochMilli);
        }
        return dates;
    }
}
