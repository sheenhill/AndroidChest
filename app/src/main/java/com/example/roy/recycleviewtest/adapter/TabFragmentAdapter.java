package com.example.roy.recycleviewtest.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.roy.recycleviewtest.fragment.MainFragment;
import com.example.roy.recycleviewtest.fragment.OtherFragment;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    public TabFragmentAdapter(FragmentManager fm, String[] mTitles){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mTitles=mTitles;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainFragment();
            case 1:
                return new OtherFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
