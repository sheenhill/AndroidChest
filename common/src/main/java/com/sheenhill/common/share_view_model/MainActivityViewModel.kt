package com.sheenhill.common.share_view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainActivityViewModel:ViewModel() {

    val imageData=MutableLiveData<String>()
    val clickedViewPosition=MutableLiveData<Int>()
    /* 网络状态 */
    val networkState= MutableLiveData<Int>()

}