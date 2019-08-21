package com.scj.beilu.app.ui.news;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.news.NewsSearchPre;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.ui.news.adapter.HistoryListAdapter;
import com.scj.beilu.app.ui.news.adapter.NewsListAdapter;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.ClearEditText;
import com.scj.beilu.app.widget.DividerItemDecorationToPadding;
import com.scj.beilu.app.widget.FlowLayoutManager;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/18 11:26
 * 资讯搜索
 */
public class SearchNewsFrag extends BaseMvpFragment<NewsSearchPre.NewsSearchView, NewsSearchPre>
        implements NewsSearchPre.NewsSearchView, View.OnClickListener, OnItemClickListener {

    private ClearEditText mEtSearchContent;
    private TextView mTvCancel;
    private RecyclerView mRvHistoryList, mRvNewsList;
    private LinearLayout mLlHistoryView, mLlNewsListView;

    private HistoryListAdapter mHistoryListAdapter;
    private NewsListAdapter mNewsListAdapter;

    private GlideRequests mGlideOriginal;

    public static SearchNewsFrag newInstance() {

        Bundle args = new Bundle();

        SearchNewsFrag fragment = new SearchNewsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NewsSearchPre createPresenter() {
        return new NewsSearchPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_news_search;
    }

    @Override
    public void initView() {
        super.initView();

        ImmersionBar.with(this)
                .statusBarDarkFont(true,0.2f)
                .titleBar(R.id.top_view)
                .keyboardEnable(true)
                .init();

        mTvCancel = findViewById(R.id.tv_cancel);
        mEtSearchContent = findViewById(R.id.et_search_content);
        mRvHistoryList = findViewById(R.id.rv_history_list);
        mRvNewsList = findViewById(R.id.rv_news_list);
        mLlHistoryView = findViewById(R.id.ll_search_history_view);
        mLlNewsListView = findViewById(R.id.ll_search_news_list_view);

        FlowLayoutManager layoutManager = new FlowLayoutManager();
        mRvHistoryList.setLayoutManager(layoutManager);
        mRvHistoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });

        mTvCancel.setOnClickListener(this);

        mHistoryListAdapter = new HistoryListAdapter();
        mRvHistoryList.setAdapter(mHistoryListAdapter);
        mHistoryListAdapter.setItemClickListener(this);

        mGlideOriginal = GlideApp.with(this);

        mNewsListAdapter = new NewsListAdapter(mFragmentActivity, mGlideOriginal);
        mNewsListAdapter.setOnItemClickListener(this);
        mRvNewsList.setAdapter(mNewsListAdapter);

        mRefreshLayout.setEnableRefresh(false);

        getPresenter().getSearchHistoryList();
        mLlHistoryView.setVisibility(View.VISIBLE);
        mLlNewsListView.setVisibility(View.GONE);

        mRvNewsList.addItemDecoration(new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.VERTICAL));

        mEtSearchContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == 100) {
                try {
                    String content = mEtSearchContent.getText().toString();
                    CheckUtils.checkStringIsNull(content, "请输入搜索内容");
                    mLlHistoryView.setVisibility(View.GONE);
                    mLlNewsListView.setVisibility(View.VISIBLE);
                    mCurrentPage = 0;
                    getPresenter().startSearch(content, mCurrentPage);
                } catch (RuntimeException e) {
                    ToastUtils.showToast(mFragmentActivity, e.getMessage());
                }
            }
            return true;
        });
        mEtSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mLlHistoryView.setVisibility(View.VISIBLE);
                    mLlNewsListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mLlHistoryView.setVisibility(View.VISIBLE);
                    mLlNewsListView.setVisibility(View.GONE);
                } else {
                    mLlHistoryView.setVisibility(View.GONE);
                    mLlNewsListView.setVisibility(View.VISIBLE);
                    mCurrentPage = 0;
                    getPresenter().startSearch(s.toString(), mCurrentPage);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                mFragmentActivity.onBackPressed();
                break;

        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().startSearch(mEtSearchContent.getText().toString(), mCurrentPage);
    }

    @Override
    public void onHistoryList(List<SearchHistoryEntity> historyList) {
        mHistoryListAdapter.setHistoryList(historyList);
    }

    @Override
    public void onNewsList(List<NewsInfoBean> newsList) {
        mNewsListAdapter.setNewsListEntityList(newsList);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        switch (view.getId()) {
            case R.id.tv_history_tag:
                SearchHistoryEntity history = (SearchHistoryEntity) entity;
                mEtSearchContent.setText(history.getTagName());
                hideSoftInput();
                break;
            default:
                NewsInfoBean infoBean = (NewsInfoBean) entity;
                NewsDetailsAct.startNewsDetailsAct(mFragmentActivity, infoBean);
                break;
        }
    }
}
