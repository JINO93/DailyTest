package com.example.administrator.test.kotlinTest

import org.junit.Test
import java.text.NumberFormat
import java.util.*

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

        val list = mutableListOf<Int>()
        for(i in 0..2)list.add(i)
        println(list)
        val asReversed = list.asReversed()
        println(asReversed)
        println(list)

        println("time:${secToTime(9990)}")
    }


    fun secToTime(time: Int): String {
        var time = time
        var timeStr: String? = null
        var hour = 0
        var minute = 0
        var second = 0
        time = Math.max(0, time)
        minute = time / 60
        if (time <= 60 * 60) {
            second = time % 60
            timeStr = String.format(Locale.CHINA, "%02d:%02d", minute, second)
        } else {
            hour = minute / 60
            if (hour > 999) {
                return "59:59:59"
            }
            minute %= 60
            second = time - hour * 3600 - minute * 60
            timeStr = String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second)
        }
        return timeStr
    }

    @Test
    fun testFor(){
        print(secToTime(3601))
    }
}