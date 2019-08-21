package com.scj.beilu.app.ui.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scj.beilu.app.ui.order.OrderListFragment;

/**
 * @author Mingxun
 * @time on 2019/1/14 20:00
 */
public class OrderManagerPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabs;

    public OrderManagerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTabs(String[] tabs) {
        mTabs = tabs;
        notifyDataSetChanged();

    }

    @Override
    public Fragment getItem(int position) {
        return OrderListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.length;
    }
}
