package com.myss.search.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileKafkaListener {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "file_test")
    public void handleMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("接收到消息,偏移量为: " + consumerRecord.offset() + " 消息为: " + consumerRecord.value());
    }


    @KafkaListener(topics = {"file_test"}, groupId = "gp1")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.info("消费消息：" + record.topic() + "----------" + record.partition() + "-------" + record.value());
        ack.acknowledge();
    }

}
