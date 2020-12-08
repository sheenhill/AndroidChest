package com.sheenhill.lotterydemo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sheenhill.lotterydemo.dlt.SSQFragment
import com.sheenhill.lotterydemo.ssq.DLTFragment

open class LotteryVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SSQFragment()
            else -> DLTFragment()
        }
    }

}