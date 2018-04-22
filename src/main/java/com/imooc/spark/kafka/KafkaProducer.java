package com.imooc.spark.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * kafka生产者
 * Created by bojian pc on 2018/4/17.
 */
public class KafkaProducer extends Thread {

    private String topic;
    private Producer<Integer, String> producer;

    public KafkaProducer(String topic){
        this.topic = topic;

        //设置properties属性
        Properties properties = new Properties();
        properties.put("metadata.broker.list", KafkaProperties.BROKER_LIST);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        //生产者发送消息后，broker的回复机制
        properties.put("request.required.acks", "1");

        producer = new Producer<Integer, String>(new ProducerConfig(properties));
    }

    @Override
    public void run() {
        int messageNo = 1;
        while (true) {
            //发送消息
            String message = "message_" + messageNo;
            producer.send(new KeyedMessage<Integer, String>(topic, message));
            System.out.println("Sent:" + message);

            messageNo++;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
