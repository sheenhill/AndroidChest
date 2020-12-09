package com.sheenhill.lotterydemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sheenhill.lotterydemo.JCrawlerViewModel
import com.sheenhill.lotterydemo.R
import com.sheenhill.lotterydemo.adapter.LotteryDLTAdapter
import com.sheenhill.lotterydemo.adapter.LotterySSQAdapter
import com.sheenhill.lotterydemo.databinding.FragmentDltBinding

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