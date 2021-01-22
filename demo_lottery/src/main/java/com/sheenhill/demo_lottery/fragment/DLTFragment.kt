package com.sheenhill.demo_lottery.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sheenhill.demo_lottery.JCrawlerViewModel
import com.sheenhill.demo_lottery.R
import com.sheenhill.demo_lottery.adapter.LotteryDLTAdapter
import com.sheenhill.demo_lottery.databinding.FragmentDltBinding

class DLTFragment : Fragment(){
    lateinit var binding:FragmentDltBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_dlt, container, false);
        val vm= parentFragment?.let { ViewModelProvider(it).get(JCrawlerViewModel::class.java) }
        binding.lifecycleOwner=this
        binding.viewModel=vm
        binding.adapterDLT=LotteryDLTAdapter()
        return binding.root
    }
}