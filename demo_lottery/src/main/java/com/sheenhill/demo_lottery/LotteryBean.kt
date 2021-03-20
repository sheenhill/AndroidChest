package com.sheenhill.demo_lottery

data class LotteryBean(
        val lottery_type: Int, // 彩票类型  0=双色球，1=大乐透
        val lottery_name: String, // 彩票名称
        val lottery_sign: String, // 彩票名称缩写
        val issue_number: String, // 期号
        val lottery_time: String, // 开奖时间
        val front_num: List<String>, // 前区号码
        val back_num: List<String>, // 后区号码
        )
