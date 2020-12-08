package com.sheenhill.lotterydemo.ssq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sheenhill.lotterydemo.R
import com.sheenhill.lotterydemo.databinding.FragmentDltBinding

class DLTFragment : Fragment(){
    lateinit var binding:FragmentDltBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_dlt, container, false);
        return binding.root
    }
}