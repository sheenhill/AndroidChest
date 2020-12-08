package com.sheenhill.lotterydemo.dlt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sheenhill.lotterydemo.R
import com.sheenhill.lotterydemo.databinding.FragmentDltBinding
import com.sheenhill.lotterydemo.databinding.FragmentSsqBinding

class SSQFragment : Fragment(){
    lateinit var binding:FragmentSsqBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_ssq, container, false);
        return binding.root
    }
}