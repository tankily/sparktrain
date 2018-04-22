package com.imooc.test

/**
  * Created by bojian pc on 2018/4/20.
  */

/*
desc：模拟控制数学表达式的库。
	  由变量、数字、一元及二元操作符组成的数学表达式。
	  jb，完全照书上的来说。
*/

object simpleBegin {
  def main(args: Array[String]): Unit = {
    //--x
    println(simplifyTop(UnOp("-", UnOp("-", Var("x")))))
    //x+0
    println(simplifyTop(BinOp("+", Var("x"), Number(0))))
    //x*1
    println(simplifyTop(BinOp("*", Var("x"), Number(1))))
    //abs abs x
    println(simplifyTop(UnOp("abs", UnOp("abs", Var("x")))))

  }

  //层级包括一个抽象基类Expr和四个子类，每个代表一种表达式。所有的五个类都没有结构体。
  //class C 和 class C {} 一个意思。
  abstract class Expr
  case class Var(name: String) extends Expr
  case class Number(num: Double) extends Expr
  case class UnOp(operator: String, arg: Expr) extends Expr
  case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

  //“e“是什么意思？？？？变量模式！
  //tips: case BinOp("*", e, Number(1)) => e 可以写成： case BinOp(a, e, b) => e !!!!!!!!!!!!!
  def simplifyTop(expr: Expr): Expr = expr match{
    case UnOp("-", UnOp("-", e)) => e
    case BinOp("+", e, Number(0)) => e
    case BinOp("*", e, Number(1)) => e
    //变量绑定，规则：变量 @ 模式，此时“变量”就代表了后面的“模式”:Unop("abs", _)
    case UnOp("abs", x @ UnOp("abs", _)) => x
  }

}
