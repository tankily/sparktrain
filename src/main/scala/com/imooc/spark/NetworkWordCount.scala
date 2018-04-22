package com.imooc.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * SparkStreaming处理Socket数据
  * 测试： nc
  */
object NetworkWordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")

    //创建StreamingContext需要两个参数：conf: SparkConf, batchDuration: Duration
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val lines = ssc.socketTextStream("localhost", 6789)

    //Transformations on DStreams
    val result = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    result.print()

    ssc.start()
    ssc.awaitTermination()


  }
}
