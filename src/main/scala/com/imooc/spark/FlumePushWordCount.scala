package com.imooc.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Flume-style Push-based Approach 本地测试
  * Created by bojian pc on 2018/4/20.
  */
object FlumePushWordCount {

  def main(args: Array[String]): Unit = {

    if(args.length != 2) {
      println("Usage: FlumePushWordCount <hostname> <port>")
      System.exit(1)
    }

    val Array(hostname, port) = args


    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("FlumePushWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    //监控 avro sink flume在hadoop01端启动
    val flumeStream = FlumeUtils.createStream(ssc, hostname, port.toInt)
    flumeStream.map(x=> new String(x.event.getBody.array()).trim)
      .flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).print()
    ssc.start()
    ssc.awaitTermination()
  }

}
