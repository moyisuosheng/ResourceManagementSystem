package com.myss.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KafkaSendTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void sendMsg() {
        String topic = "file_test";
        kafkaTemplate.send(topic, "hello spring boot kafka!");
        log.info("发送成功.");
    }
}
