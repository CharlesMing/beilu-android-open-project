package com.scj.beilu.app.ui.course.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mx.pro.lib.fragmentation.support.SupportFragment;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:27
 */
public class DescriptionFragAdapter extends FragmentPagerAdapter {

    private List<SupportFragment> mFragments;

    public DescriptionFragAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<SupportFragment> fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
