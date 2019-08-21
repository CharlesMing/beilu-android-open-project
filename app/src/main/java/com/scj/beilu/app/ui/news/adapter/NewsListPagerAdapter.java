package com.scj.beilu.app.ui.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scj.beilu.app.mvp.news.bean.NewsNavBean;
import com.scj.beilu.app.ui.news.NewsAttentionListFrag;
import com.scj.beilu.app.ui.news.NewsListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/11 14:49
 */
public class NewsListPagerAdapter extends FragmentStatePagerAdapter {
    private List<NewsNavBean> mNewsListFrags;

    public NewsListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setNewsListFrags(List<NewsNavBean> newsListFrags) {
        mNewsListFrags = newsListFrags;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new NewsAttentionListFrag();
        } else {
            return NewsListFrag.newInstance(mNewsListFrags.get(position));
        }
    }

    @Override
    public int getCount() {
        return mNewsListFrags == null ? 0 : mNewsListFrags.size();
    }
}
