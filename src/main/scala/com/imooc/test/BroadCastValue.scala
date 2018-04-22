package com.imooc.test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bojian pc on 2018/4/22.
  */
object BroadCastValue {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("BroadCastValue").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val broads = sc.broadcast(3)
    val lists = List(1,2,3,4,5)
    val listRDD = sc.parallelize(lists)
    val results = listRDD.map(x=>x*broads.value)
    results.foreach(x=>println("The result is"+ x))
  }

}
