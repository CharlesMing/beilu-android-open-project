package com.scj.beilu.app.ui.home.adapter.seller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mx.pro.lib.fragmentation.support.SupportFragment;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/2/18
 * descriptin:
 */
public class ShopPhotoManagerPagerAdapter extends FragmentPagerAdapter {

    private List<SupportFragment> mFragments;

    public ShopPhotoManagerPagerAdapter(FragmentManager fm) {
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
