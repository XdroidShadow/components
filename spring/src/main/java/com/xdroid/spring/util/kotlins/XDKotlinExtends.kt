package com.xdroid.spring.example.android.log

/**
 *   偏函数
 *   带参数的科里化方法
 */
fun <P1, P2, R> Function2<P1, P2, R>.logWithTag(p1: P1) = fun(p2: P2) = this.invoke(p1, p2)



