package com.myss.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

@Slf4j
public class ProducerTest {

    public static void main(String[] args) {
        // 设置属性
        Properties props = new Properties();
        // 指定连接的kafka服务器的地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.249.133:9192");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        // 构建kafka生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props, new StringSerializer(), new StringSerializer());
        // 定义kafka主题
        String topic = "test";
        // 调用生产者发送消息
        String message = "hello kafka!";
        // 构建消息
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
        // 同时发送key和value
        record = new ProducerRecord<String, String>(topic, UUID.randomUUID().toString(), message);
        // 发送消息  发送并忘记
//        producer.send(record);

//        同步发送
//        Future<RecordMetadata> send = producer.send(record);
//        try {
//            RecordMetadata recordMetadata = send.get();
//            System.out.println(recordMetadata.offset());
//        } catch (Exception e) {
//            e.printStackTrace();
////            在这儿应该记录发送失败的消息  mysql   file   log
//        }

//        异步发送
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("消息发送成功。偏移量：" + metadata.offset());
            }
        });
        System.out.println("发送结束！");
        // 释放连接
        producer.close();
    }
}