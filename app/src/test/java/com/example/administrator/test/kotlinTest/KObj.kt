package com.example.administrator.test.kotlinTest

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 16:42 2020/10/12
 */
open class KObj<T> {

    var obj : T? = null

    fun init(initSth:(T?)->Unit){
        initSth(obj)
    }
}

class IntKObj:KObj<Int>(){}
class StringKObj:KObj<String>(){}

interface Initer{
    fun init()
}