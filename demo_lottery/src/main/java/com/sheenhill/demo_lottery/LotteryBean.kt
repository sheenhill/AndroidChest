package com.sheenhill.demo_lottery

data class LotteryBean(
        val lottery_type: String, // 彩票类型
        val issue_number: String, // 期号
        val lottery_time: String, // 开奖时间
        val front_num: List<String>, // 前区号码
        val back_num: List<String>, // 后区号码
        )
