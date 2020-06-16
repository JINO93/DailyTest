package com.example.administrator.test.kotlinTest

import org.junit.Test

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 10:42 2020/6/3
 */
class KTest {

    @Test
    fun testSth(){
        val b = true
        println("${TAG} res:${b?:"JINO"}")
        test()
    }
}