package com.sheenhill.demo_lottery

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sheenhill.common.util.LogUtil
import com.sheenhill.common.util.ToastUtils
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*

class LotteryViewModel : ViewModel() {
    val SSQ = true
    val DLT = false

    // 页面类型
    val pageInfoType = MutableLiveData(SSQ)

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
                    arr.add(LotteryBean("双",
                            tempArr[tempArr.size - 1],
                            tempArr[0],
                            listOf(tempArr[1], tempArr[2], tempArr[3], tempArr[4], tempArr[5]),
                            listOf(tempArr[6], tempArr[7]
                            )))
                }
                infoListSsq.postValue(arr)
                //                LogUtil.i("list:"+arr);
            }
        })
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
                    arr.add(LotteryBean("乐",
                            tempArr[tempArr.size - 1],
                            tempArr[0],
                            listOf(tempArr[1], tempArr[2], tempArr[3], tempArr[4], tempArr[5], tempArr[6]),
                            listOf(tempArr[7]
                            )))
                }
                infoListDlt.postValue(arr)
            }
        })
    }
}