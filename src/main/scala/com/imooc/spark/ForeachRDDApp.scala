package com.imooc.spark

import java.sql.{Connection, DriverManager}

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 使用Spark Streaming 完成统计，并将结果写到MySQL数据库中
  * 测试： nc
  * 将统计结果写入到MySQL
  * create table wordcount(
  * word varchar(50) default null,
  * wordcount int(10) default null
  * );
  */
object ForeachRDDApp {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("ForeachRDDApp")

    //创建StreamingContext需要两个参数：conf: SparkConf, batchDuration: Duration
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val lines = ssc.socketTextStream("localhost", 6789)

    //Transformations on DStreams
    val result = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    //将结果输出到控制台
    //result.print()

    result.foreachRDD{ rdd =>
      rdd.foreachPartition{partitionOfRecords =>

          val connection = createConnection()
          partitionOfRecords.foreach{ record =>

            val select = "select * from wordcount where word = '" + record._1+ "'"
            val resultSet = connection.createStatement().executeQuery(select)
            if(resultSet.first()){
              val wordcount = resultSet.getInt("wordcount")
              val update = "update wordcount set wordcount = "+(wordcount+record._2)+" where word='"+record._1+"'"
              connection.createStatement().executeUpdate(update)
            }else{
              val insert = "insert into wordcount(word, wordcount) values('"+record._1+"',"+record._2+")"
              connection.createStatement().execute(insert)
            }
          }
          connection.close()
      }
    }


    ssc.start()
    ssc.awaitTermination()

  }

  /**
    * 获取mysql的连接
    * @return
    */
  def createConnection() = {
    Class.forName("com.mysql.jdbc.Driver")
    DriverManager.getConnection("jdbc:mysql://localhost:3306/imooc_spark", "root", "123456")
  }





}
