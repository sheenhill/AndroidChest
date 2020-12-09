package com.sheenhill.lotterydemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sheenhill.lotterydemo.databinding.FragmentLotteryBinding

/* 集成模式下的彩票模块入口，直接用了Navigation组件来导航 */
class LotteryFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentLotteryBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_lottery, container, false)
        return binding.root
    }
}