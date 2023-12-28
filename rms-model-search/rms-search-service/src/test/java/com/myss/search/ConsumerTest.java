package com.myss.search;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ConsumerTest {
    public static void main(String[] args) {
        // 设置属性
        Properties props = new Properties();
        // 指定连接的kafka服务器的地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.249.133:9192");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 指定消费者的分组编号

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_5");
        props.put("enable.auto.commit", "false");
        // 构建kafka消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props, new StringDeserializer(), new StringDeserializer());
        // 定义kafka主题
        String topic = "test";
        // 指定消费者订阅主题
        List<String> topics = new ArrayList<String>();
        topics.add(topic);
        consumer.subscribe(topics);
        // 调用消费者拉取消息
        while (true) {
            //每隔1秒拉取一次消息
            Duration.ofSeconds(1);
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            // 遍历集合
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                String key = consumerRecord.key();
                String value = consumerRecord.value();
                System.out.println("接收到消息: key = " + key + ", value = " + value);
//                consumer.commitSync(); //同步提交
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {

                    }
                });

            }
        }
    }
}
