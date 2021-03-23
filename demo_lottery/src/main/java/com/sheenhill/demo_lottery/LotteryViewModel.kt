package com.sheenhill.demo_lottery

import android.content.Context
import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sheenhill.common.util.LogUtil
import com.sheenhill.common.util.ToastUtils
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class LotteryViewModel : ViewModel() {
    val TYPE_SSQ = 0
    val TYPE_DLT = 1

    // 页面类型
    val pageInfoType = MutableLiveData(TYPE_SSQ)

    // 双色球集合
    val infoListSsq = MutableLiveData<List<LotteryBean>>()

    // 大乐透集合
    val infoListDlt = MutableLiveData<List<LotteryBean>>()

    /* 爬取双色球彩票数据 */
    fun crawlerSsq(context: Context?, url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback {
            //请求失败的回调方法
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.i("找不到链接啦，，，$e") //
            }

            //请求成功的回调方法
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val data = response.body()!!.string()
                //                LogUtil.i("给爷爬okhttp返回数据:"+data);
                if (data.contains("访问被禁止")) {
                    ToastUtils.showShort(context, "不能爬")
                    return
                }
                val arr: MutableList<LotteryBean> = ArrayList()
                val doc = Jsoup.parse(data)
                val list = doc.getElementById("tablelist").getElementById("tdata").getElementsByClass("t_tr1")
                for (element in list) {
//                    LogUtil.i("element:"+element.text());
                    val tempArr = element.text().split(" ".toRegex()).toTypedArray()
                    arr.add(LotteryBean(lottery_type = TYPE_SSQ,
                            lottery_name = "双色球", lottery_sign = "双",
                            issue_number = "${tempArr[0]}期",
                            lottery_time = getDayofWeek(tempArr[tempArr.size - 1]),
                            front_num = listOf(tempArr[1], tempArr[2], tempArr[3], tempArr[4], tempArr[5], tempArr[6]),
                            back_num = listOf(tempArr[7])
                    ))
                }
                infoListSsq.postValue(arr)
                //                LogUtil.i("list:"+arr);
            }
        })
    }

    private fun getDayofWeek(date: String): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        cal.time = sdf.parse(date)
//        return "星期${(cal.get(Calendar.DAY_OF_WEEK)-1)%7}"
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> "${date}(周日)"
            2 -> "${date}(周一)"
            3 -> "${date}(周二)"
            4 -> "${date}(周三)"
            5 -> "${date}(周四)"
            6 -> "${date}(周五)"
            7 -> "${date}(周六)"
            else -> date
        }
    }

    /* 爬取大乐透彩票数据 */
    fun crawlerDLT(context: Context?, url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback {
            //请求失败的回调方法
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.i("找不到链接啦，，，$e") //
            }

            //请求成功的回调方法
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val data = response.body()!!.string()
                //                LogUtil.i("给爷爬okhttp返回数据:"+data);
                if (data.contains("访问被禁止")) {
                    ToastUtils.showShort(context, "不能爬")
                    return
                }
                val arr: MutableList<LotteryBean> = ArrayList()
                val doc = Jsoup.parse(data)
                val list = doc.getElementById("tablelist").getElementById("tdata").getElementsByTag("tr")
                //                LogUtil.i("crawlerDLT.list.size():"+list.size());
                for (element in list) {
//                    LogUtil.i("element:"+element.text());
                    val tempArr = element.text().split(" ".toRegex()).toTypedArray()
                    arr.add(LotteryBean(TYPE_DLT,
                            "超级大乐透", "乐",
                            "${tempArr[0]}期",
                            getDayofWeek(tempArr[tempArr.size - 1]),
                            listOf(tempArr[1], tempArr[2], tempArr[3], tempArr[4], tempArr[5]),
                            listOf(tempArr[6], tempArr[7])
                    ))
                }
                infoListDlt.postValue(arr)
            }
        })
    }
}