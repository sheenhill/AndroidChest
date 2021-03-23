package com.sheenhill.module_network.test_webSocket

import androidx.lifecycle.ViewModel
import io.socket.engineio.client.Socket

class WebSocketTestViewModel : ViewModel() {

    init {
        val mSocket=Socket("http://192.168.0.7:6666")
    }
}