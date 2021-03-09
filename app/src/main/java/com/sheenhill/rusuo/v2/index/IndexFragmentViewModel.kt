package com.sheenhill.rusuo.v2.index

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.sheenhill.rusuo.base.APIs
import com.sheenhill.rusuo.v2.index.BingPicBean.ImagesBean
import com.sheenhill.rusuo.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.util.*

class IndexFragmentViewModel : ViewModel() {
    val message = MutableLiveData<String>()
    val bingPicList = MutableLiveData<MutableList<ImagesBean>>()

    init {
        getBingPic()
        message.value = ""
        getTest()
//        broadSocket()
        startReceiver()
    }

    fun broadSocket() {
        viewModelScope.launch {
            var count = 0
            val hostSocket = DatagramSocket()
            hostSocket.soTimeout = 1500
            var broadIP = InetAddress.getByName("255.255.255.255")
            val sendPack = DatagramPacket("aaa".toByteArray(),
                    "aaa".length,
                    broadIP,
                    3000)

            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val msg = "这是Android发出的第${count++}条消息"
                    val bytes = msg.toByteArray()
                    hostSocket.send(
                            DatagramPacket(bytes,
                                    bytes.size,
                                    broadIP,
                                    3000));
                    LogUtil.d("broadSocket>>>>>$msg")
                }

            }, 0, 1000)
        }
    }

    fun startReceiver() {
        viewModelScope.launch(Dispatchers.IO) {
            val buffer = ByteArray(20)
            val packet = DatagramPacket(buffer, buffer.size)
            val socket = DatagramSocket(3000)
            socket.soTimeout = 8000  // 接收超时时间
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    socket.receive(packet) // 阻塞接收
                    LogUtil.i("socket.receive(packet)")
                    if (packet.data != null) {
                        val ip = packet.address.hostAddress
                        val msg = String(packet.data)
                        val length = packet.length
                        LogUtil.d("startReceiver>>>>>>>>ip=${ip},msg=${msg},length=${length}")
                        if (msg.contains("server.sheenhill.lan")) {
                            LogUtil.d("startReceiver>>>>>>>>OK")
                        }
                    }
                } catch (e: SocketTimeoutException) {  // 接收超时异常
                    LogUtil.d("startReceiver>>>>>>>> timeout")
                }
            }
//            var count=5
//            Timer().schedule(object : TimerTask() {
//                override fun run() {
//                    count--
//                    if(count==0&&socket.isConnected)
//                        socket.close()
//                    // 拿不到socket状态
//                    LogUtil.d("socket.isConnected=${socket.isConnected},isClosed=${socket.isClosed}")
//                }
//            }, 0, 1000)

//                    if(time!=-1){
//                        time--;
//                    }else {
//                        return;
//                    }
//            socket.close()
//        }
//            }, 0, 1000)
        }
    }

    fun find(socket: DatagramSocket, packet: DatagramPacket) {
        socket.receive(packet)
        val ip = packet.address.hostAddress
        val msg = String(packet.data)
        val length = packet.length
        LogUtil.d("startReceiver>>>>>>>>ip=${ip},msg=${msg},length=${length}")
        if (msg.contains("server.sheenhill.lan")) {
            LogUtil.d("startReceiver>>>>>>>>OK")
            return
        } else {
            find(socket, packet)
        }
    }

    private fun getTest() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://www.sheenhill.art:3000/user_table")
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
                LogUtil.i("getTest() 返回数据:$data")

            }
        })
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
                val list = MutableList<ImagesBean>(0) { it -> ImagesBean() }
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