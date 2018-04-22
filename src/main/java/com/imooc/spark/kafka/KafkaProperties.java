package com.imooc.spark.kafka;

/**
 * Kafka常用配置文件
 * Created by bojian pc on 2018/4/17.
 */
public interface KafkaProperties {

    String ZK = "hadoop01:2181";
    String TOPIC = "hello_topic";
    String BROKER_LIST = "hadoop01:9092";
    String GROUP_ID =  "test_group1";

}
