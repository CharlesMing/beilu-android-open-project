package com.scj.beilu.app.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scj.beilu.app.ui.home.HomeSearchFindInfoFrag;
import com.scj.beilu.app.ui.home.HomeSearchGoodsFrag;
import com.scj.beilu.app.ui.home.HomeSearchNewsFrag;
import com.scj.beilu.app.ui.home.HomeSearchUserFrag;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:49
 */
public class HomeSearchPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = null;

    public HomeSearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new HomeSearchNewsFrag();
            case 2:
                return new HomeSearchGoodsFrag();
            case 3:
                return new HomeSearchUserFrag();
            default:
                return new HomeSearchFindInfoFrag();
        }
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.length;
    }
}
