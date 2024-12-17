package com.myss.search.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileKafkaListener {

    @KafkaListener(topics = {"file_test"}, groupId = "gp1")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.info("消费消息：" + record.topic() + "----------" + record.partition() + "-------" + record.value());
        ack.acknowledge();
    }

}
