package com.scj.beilu.app.ui.store.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scj.beilu.app.mvp.store.bean.GoodsCategoryInfoBean;
import com.scj.beilu.app.ui.store.StoreGoodsListFrag;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/3 16:53
 */
public class GoodsListPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<GoodsCategoryInfoBean> mGoodsCategoryInfoBeans;

    public void setGoodsCategoryInfoBeans(ArrayList<GoodsCategoryInfoBean> goodsCategoryInfoBeans) {
        mGoodsCategoryInfoBeans = goodsCategoryInfoBeans;
        notifyDataSetChanged();
    }

    public GoodsListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return StoreGoodsListFrag.newInstance(mGoodsCategoryInfoBeans.get(i).getProductCateId());
    }

    @Override
    public int getCount() {
        return mGoodsCategoryInfoBeans == null ? 0 : mGoodsCategoryInfoBeans.size();
    }
}
