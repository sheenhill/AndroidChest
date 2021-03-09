package com.sheenhill.module_network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sheenhill.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.util.*

class NetTestViewModel : ViewModel() {
    val testMsg = MutableLiveData<String>("...")
    val testMsgList = MutableList<String>(1) { "..." }
    val testHost = MutableLiveData("192.128.0.46")

    init {
//        receiverSocket()
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
                    4000)

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

    fun receiverSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            val buffer = ByteArray(20)
            val packet = DatagramPacket(buffer, buffer.size)
            val socket = DatagramSocket(4000)
//            socket.soTimeout = 8000  // 接收超时时间
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
                        testHost.postValue(ip)
                        testMsg.postValue("获取到服务器ip:$ip")
                    }
                }
            } catch (e: SocketTimeoutException) {  // 接收超时异常
                LogUtil.d("startReceiver>>>>>>>> timeout")
                testMsg.postValue("搜索服务器超时")
            }finally {
                socket.close()
            }
        }
    }

    fun testConnectServer() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://${testHost.value}:3000/test")
                .build()
        client.newCall(request).enqueue(object : Callback {
            //请求失败的回调方法
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.i("请求报错>>>>>$e") //
                testMsg.postValue("连接服务器${testHost.value}失败")
                receiverSocket()
            }

            //请求成功的回调方法
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val data = response.body()!!.string()
                if (data == "200") {
                    testMsg.postValue("连接服务器${testHost.value}成功")
                }
                LogUtil.i("testConnectServer返回数据:$data")
            }
        })
    }
}