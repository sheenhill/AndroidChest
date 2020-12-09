package com.sheenhill.lotterydemo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sheenhill.lotterydemo.fragment.SSQFragment
import com.sheenhill.lotterydemo.fragment.DLTFragment

open class LotteryVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SSQFragment()
            1 -> DLTFragment()
            else -> DLTFragment()
        }
    }

}