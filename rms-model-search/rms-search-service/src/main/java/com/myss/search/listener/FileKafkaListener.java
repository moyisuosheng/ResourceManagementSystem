package com.myss.search.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileKafkaListener {

//    /**
//     * 指定监听的主题
//     *
//     * @param message
//     */
//    @KafkaListener(topics = "spring_test")
//    public void receiveMsg(String message) {
//        System.out.println("接收到的消息：" + message);
//    }

    @KafkaListener(topics = "file_test")
    public void handleMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("接收到消息,偏移量为: " + consumerRecord.offset() + " 消息为: " + consumerRecord.value());
    }
}
