package com.imooc.spark

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bojian pc on 2018/4/22.
  */
object SparkOperateHBase {

  def main(args: Array[String]): Unit = {

    val conf = HBaseConfiguration.create()
    val sc = new SparkContext(new SparkConf())
    //设置查询的表名
    conf.set(TableInputFormat.INPUT_TABLE, "student")
    val stuRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.mapreduce.TableInputFormat],
      classOf[org.apache.hadoop.hbase.client.Result]
    )

    val count = stuRDD.count()
    println("Students RDD Count:" + count)
    stuRDD.cache()

    //遍历_为系统需要的数据，此处不需要
    stuRDD.foreach{ case (_, result) =>
      val key = Bytes.toString(result.getRow)
      val name = Bytes.toString(result.getValue("info".getBytes, "name".getBytes))
      val gender = Bytes.toString(result.getValue("info".getBytes, "gender".getBytes))
      val age = Bytes.toString(result.getValue("info".getBytes, "age".getBytes))
      println("Row key:" + key + "Name:" + name + "Gender:" + gender + "Age" + age)
    }
  }

}
