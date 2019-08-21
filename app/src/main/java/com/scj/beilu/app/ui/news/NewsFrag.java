package com.scj.beilu.app.ui.news;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.news.NewsPre;
import com.scj.beilu.app.mvp.news.bean.NewsNavBean;
import com.scj.beilu.app.mvp.news.bean.NewsNavListBean;
import com.scj.beilu.app.ui.act.SearchNewsAct;
import com.scj.beilu.app.ui.news.adapter.NewsListPagerAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/11 15:35
 * 资讯
 */
public class NewsFrag extends BaseMvpFragment<NewsPre.NewsView, NewsPre>
        implements NewsPre.NewsView {


    private FrameLayout mFlStartSearch;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private LinearLayout mLlLoading;

    private NewsListPagerAdapter mNewsListPagerAdapter;
    private boolean isLoad = true;
    private boolean reLoad = false;
    private final String LOAD_STATE = "load_state";
    private int mLineWidth;

    @Override
    public NewsPre createPresenter() {
        return new NewsPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_news;
    }

    @Override
    public void initView() {
        super.initView();
        mFlStartSearch = findViewById(R.id.fl_start_search);
        mViewPager = findViewById(R.id.view_pager_news);
        mTabLayout = findViewById(R.id.tab_news_manager);
        mLlLoading = findViewById(R.id.ll_loading_layout);

        mNewsListPagerAdapter = new NewsListPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mNewsListPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
        if (reLoad) {
            getPresenter().initNav();
        }
        if (isAdded()) {
            mLineWidth = getResources().getDimensionPixelOffset(R.dimen.tab_indicator_width);
        }
        mFlStartSearch.setOnClickListener(v -> SearchNewsAct.startSearchNewsAct(mFragmentActivity));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isLoad) {
            isLoad = false;
            getPresenter().initNav();
        }
    }

    @Override
    public void onNewsList(NewsNavListBean viewsBean) {
        List<NewsNavBean> cates = viewsBean.getCates();
        mNewsListPagerAdapter.setNewsListFrags(cates);
        int size = cates.size();
        for (int i = 0; i < size; i++) {
            NewsNavBean navBean = cates.get(i);
            mTabLayout.getTabAt(i).setCustomView(getTabLayout(navBean.getNewsCateName()));
        }
        initNavTab();
        if (mViewPager.getVisibility() == View.GONE) {
            mViewPager.setVisibility(View.VISIBLE);
        }
    }

    private void initNavTab() {

        setTabSelector(mTabLayout.getTabAt(0), true);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabSelector(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabSelector(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            reLoad = savedInstanceState.getBoolean(LOAD_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOAD_STATE, true);
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading != ShowConfig.NONE) {
            super.showLoading(loading, isShow);
            mLlLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        super.showError(errorCode, throwableContent);
        mLlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onReLoad() {
        getPresenter().initNav();
    }

    public View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mFragmentActivity).inflate(R.layout.layout_news_tab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setText(title);
        return view;
    }

    public void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) return;
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        ImageView ivLine = view.findViewById(R.id.iv_tab_bottom_line);
        tvTab.setSelected(selected);
        ivLine.setSelected(selected);
        if (selected) {
            int width = tvTab.getWidth();
            if (width == 0) {
                tvTab.measure(0, 0);
                width = tvTab.getMeasuredWidth();
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivLine.getLayoutParams();
            params.width = width + mLineWidth;
            ivLine.setLayoutParams(params);
            ivLine.invalidate();
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }
}
