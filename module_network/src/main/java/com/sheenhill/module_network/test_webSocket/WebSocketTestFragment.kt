package com.sheenhill.module_network.test_webSocket

import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.module_network.BR
import com.sheenhill.module_network.NetTestFragment
import com.sheenhill.module_network.NetTestViewModel
import com.sheenhill.module_network.R
import io.socket.client.IO
import io.socket.client.Socket

class WebSocketTestFragment : K_BaseJetpackFragment() {
    lateinit var vm: WebSocketTestViewModel
    override fun initViewModel() {
        vm= getFragmentViewModel(WebSocketTestViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_test_websocket, BR.vm, vm)
                .addBindingParam(BR.listener, Listener())
    }

    class Listener(){
        fun testConnect(){
            val opt=IO.Options()
            opt.path="/sheenhill/"
            val mSocket= IO.socket("http://192.168.0.7:6666",opt)
//            mSocket.on("connect",)
            mSocket.connect()
        }
    }
}