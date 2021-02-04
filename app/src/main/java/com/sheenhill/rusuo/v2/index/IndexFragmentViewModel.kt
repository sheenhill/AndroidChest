package com.sheenhill.rusuo.v2.index

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.sheenhill.rusuo.base.APIs
import com.sheenhill.rusuo.v2.index.BingPicBean.ImagesBean
import com.sheenhill.rusuo.util.LogUtil
import okhttp3.*
import java.io.IOException

class IndexFragmentViewModel:ViewModel() {
    val message=MutableLiveData<String>()
    val bingPicList= MutableLiveData<MutableList<ImagesBean>>()

    init {
        getBingPic()
        message.value=""
    }

    fun getBingPic() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(APIs.PICs_BING + "idx=0&n=10")
                .build()
        client.newCall(request).enqueue(object : Callback {
            //请求失败的回调方法
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.i("找不到图链接啦，，，$e") //
            }
            //请求成功的回调方法
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val data = response.body()!!.string()
                LogUtil.i("okhttp返回数据:$data")
                val jsonObject = JsonParser().parse(data).asJsonObject
                val jsonArray = jsonObject.getAsJsonArray("images") //拿到数组
                var resultsBean: ImagesBean
                val list= MutableList<ImagesBean>(0) { it -> ImagesBean() }
                var url: String
                for (pic in jsonArray) {
                    resultsBean = Gson().fromJson(pic,
                            object : TypeToken<ImagesBean?>() {}.type)
                    //GSON 提供了 TypeToken 这个类来帮助我们捕获（capture）像 List 这样的泛型信息。
                    // Java编译器会把捕获到的泛型信息编译到这个匿名内部类里，然后在运行时就可以被 getType() 方法用反射的 API 提取到。
                    //解释的很官方，实际上就是一句 通俗但不严谨 的话，它将泛型 T 转成 .class 。
                    // 比如上面的resultsBean 经过 getType() 后就是 ResultsBean.class 。
                    url = "https://www.bing.com${resultsBean.url.replaceFirst("1920x1080".toRegex(), "800x480")}"
                    resultsBean.url = url
                    list.add(resultsBean)
                    LogUtil.i("bingPicList.url:" + resultsBean.url)
                }
                bingPicList.postValue(list)
                LogUtil.i("图片信息集合:${list}") //照片的集合
                response.body()!!.close() //关闭body
            }
        })
    }

}