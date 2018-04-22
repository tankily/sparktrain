package com.imooc.spark

import java.io.File

import scala.collection.mutable
import scala.io.Source

/**
  * Created by bojian pc on 2018/4/19.
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val dirfile = new File("/a")
    val files: Array[File] = dirfile.listFiles()
    for(file <- files) println(file)
    val listFiles = files.toList
    val wordsMap = mutable.Map[String, Int]()
    listFiles.foreach(
      file =>Source.fromFile(file).getLines().foreach(line=>line.split(" ").foreach(
        word=>{
          if(wordsMap.contains(word)){
            wordsMap(word)+=1
          }else {
            wordsMap+=(word->1)

          }
        }
      ))
    )
    println(wordsMap)
    for((key, value) <- wordsMap)
      println(key+":"+value)
  }

}
