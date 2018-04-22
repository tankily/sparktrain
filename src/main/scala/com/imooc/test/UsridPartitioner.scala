package com.imooc.test

import org.apache.spark.{Partitioner, SparkConf, SparkContext}



class UsridPartitioner(numParts:Int) extends Partitioner{
  override def numPartitions: Int = numParts

  override def getPartition(key: Any): Int = {
    key.toString.toInt % 10
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[1]").setAppName("Test")
    val sc = new SparkContext(sparkConf)

    val data = sc.parallelize(1 to 10, 5) //5个分区
    data.map((_,1)).partitionBy(new UsridPartitioner(10)).map(_._1).saveAsTextFile("file:///E:/photo/ss/aa")
  }
}
