package com.imooc.test

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.parsing.json.JSON

/**
  * Created by bojian pc on 2018/4/22.
  */
object JSONApp {

  def main(args: Array[String]): Unit = {
    val inputFile="file:///E:/photo/ss/people.json"
    val conf = new SparkConf().setMaster("local[1]").setAppName("JSONApp")
    val sc = new SparkContext(conf)
    val jsonStrs = sc.textFile(inputFile)
    val result = jsonStrs.map(s => JSON.parseFull(s))
    result.foreach{r =>
      r match {
        case Some(map: Map[String, Any]) => println(map)
        case None => println("Parsing failed")
        case other => println("Unknown data structure:" + other)
      }
    }
  }
}
