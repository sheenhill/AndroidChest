package com.sheenhill.rusuo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sheenhill.rusuo.fragment.GuideFragment;

import java.util.List;


/**
 * 引导页对应的适配器
 */
public class GuidePagerAdapter extends FragmentPagerAdapter {

    private List<GuideFragment> mFragmentList;

    public GuidePagerAdapter(FragmentManager fm, List<GuideFragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
