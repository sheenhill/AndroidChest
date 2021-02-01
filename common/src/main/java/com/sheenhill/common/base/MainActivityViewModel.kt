package com.sheenhill.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainActivityViewModel:ViewModel() {
    val imageData=MutableLiveData<String>()
    val clickedViewPosition=MutableLiveData<Int>()

}