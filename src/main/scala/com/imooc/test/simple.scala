package com.imooc.test

/**
  * Created by bojian pc on 2018/4/20.
  */
object simple {

  def main(args: Array[String]): Unit = {
    println(simplifyTop(UnOp("-", UnOp("-", Var("x")))))
    println(simplifyTop(BinOp("+", Var("x"), Number(0))))
  }


  abstract  class Expr
  case class Var(name: String) extends Expr
  case class  Number(num: Double) extends Expr
  case class UnOp(operator: String, arg: Expr) extends Expr
  case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e
    case BinOp("+", e, Number(0)) => e
    case BinOp("*", e, Number(1)) => e
    case UnOp("abs", x @ UnOp("abs", _)) => x

  }
}




