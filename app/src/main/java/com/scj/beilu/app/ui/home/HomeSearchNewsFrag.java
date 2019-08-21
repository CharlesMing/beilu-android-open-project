package com.scj.beilu.app.ui.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.home.bean.EventSearchTypeBean;
import com.scj.beilu.app.mvp.news.NewsSearchPre;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.ui.news.NewsDetailsAct;
import com.scj.beilu.app.ui.news.adapter.NewsListAdapter;
import com.scj.beilu.app.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:07
 */
public class HomeSearchNewsFrag extends BaseMvpFragment<NewsSearchPre.NewsSearchView, NewsSearchPre>
        implements NewsSearchPre.NewsSearchView, OnItemClickListener {

    private View mLoadingView;
    private LinearLayout mLLEmpty;
    private TextView mTvEmptyHint;
    private ViewStub mLoading;

    private String mKeyName;

    private NewsListAdapter mNewsListAdapter;

    @Override
    public NewsSearchPre createPresenter() {
        return new NewsSearchPre(mFragmentActivity);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onKeyName(EventSearchTypeBean eventSearch) {
        mCurrentPage = 0;
        mKeyName = eventSearch.keyName;
        getPresenter().startSearch(mKeyName, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().startSearch(mKeyName, mCurrentPage);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_home_search_list;
    }

    @Override
    public void initView() {

        super.initView();

        mLoading = findViewById(R.id.view_stu_loading);
        mLLEmpty = findViewById(R.id.ll_load_empty_view);
        mTvEmptyHint = findViewById(R.id.tv_empty_hint);

        mLoading = findViewById(R.id.view_stu_loading);
        mLLEmpty = findViewById(R.id.ll_load_empty_view);
        mTvEmptyHint = findViewById(R.id.tv_empty_hint);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));

        mNewsListAdapter = new NewsListAdapter(mFragmentActivity, GlideApp.with(this));
        mNewsListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mNewsListAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        mTvEmptyHint.setText("暂无搜索结果");
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        NewsInfoBean infoBean = (NewsInfoBean) entity;
        NewsDetailsAct.startNewsDetailsAct(mFragmentActivity, infoBean);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onHistoryList(List<SearchHistoryEntity> historyList) {
        //none
    }

    @Override
    public void onNewsList(List<NewsInfoBean> newsList) {
        if (newsList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mLLEmpty.setVisibility(View.VISIBLE);
        } else {
            hideSoftInput();
            mRecyclerView.setVisibility(View.VISIBLE);
            mLLEmpty.setVisibility(View.GONE);
            mNewsListAdapter.setNewsListEntityList(newsList);
        }
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_REFRESH) {
            if (mCurrentPage == 0) {
                mLLEmpty.setVisibility(View.GONE);
                if (isShow) {
                    mRecyclerView.setVisibility(View.GONE);
                    if (mLoadingView == null) {
                        mLoadingView = mLoading.inflate();
                    } else {
                        mLoading.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (mLoadingView != null) {
                        mLoading.setVisibility(View.GONE);
                    }
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        if (mLoadingView != null) {
            mLoading.setVisibility(View.GONE);
        }
        ToastUtils.showToast(mFragmentActivity, throwableContent);
    }
}
