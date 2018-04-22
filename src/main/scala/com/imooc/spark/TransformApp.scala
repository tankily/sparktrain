package com.imooc.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 黑名单过滤
  * Created by bojian pc on 2018/4/20.
  */
object TransformApp {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")

    //创建StreamingContext需要两个参数：conf: SparkConf, batchDuration: Duration
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    //创建黑名单
    val blacks = List("zs", "ls")
    //把list转换为RDD 并转化为（"zs",true）的形式
    val blackRDD = ssc.sparkContext.parallelize(blacks).map(x=>(x,true))

    val lines = ssc.socketTextStream("localhost", 6789)
    //把2018000,zs ==> zs,"2018000,zs"
    val clicklog = lines.map(x => (x.split(",")(1), x)).transform(rdd =>{
      //zs, ("2018000,zs", true)
      rdd.leftOuterJoin(blackRDD).filter(x=> !x._2._2.getOrElse(false))
        .map(x=>x._2._1)
    })

    //"2018000,zs"
    clicklog.print()


    ssc.start()
    ssc.awaitTermination()


  }

}
