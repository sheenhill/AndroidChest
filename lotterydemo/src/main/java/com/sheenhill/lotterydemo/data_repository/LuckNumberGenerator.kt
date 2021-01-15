package com.sheenhill.lotterydemo.data_repository

import com.sheenhill.common.util.LogUtil

// 前区1-35个号码
val ticket_1 = mutableSetOf("01", "02", "03", "04", "05", "06", "07", "08",
        "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
        "26", "27", "28", "29", "30", "31", "32", "33", "34", "35")

// 后区1-12个号码
val ticket_2 = mutableSetOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")

/* 获取幸运数字集合 */
fun getLuckyNums(): List<String> {
    val res1 = List(5) {
        val s = ticket_1.random()
        ticket_1.remove(s)
        s
    }
    val res2 = List(2) {
        val s = ticket_2.random()
        ticket_2.remove(s)
        s
    }
    res1.sorted()
    res2.sorted()
    LogUtil.d("getTickets.result >>>>${res1.plus(res2)}")
    return res1.plus(res2)
}