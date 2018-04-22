package com.imooc.spark.kafka;

/**
 * Created by bojian pc on 2018/4/17.
 */
public class KafkaClientApp {

    public static void main(String[] args) {
        new KafkaProducer(KafkaProperties.TOPIC).start();
        new KafkaConsumer(KafkaProperties.TOPIC).start();
    }
}
