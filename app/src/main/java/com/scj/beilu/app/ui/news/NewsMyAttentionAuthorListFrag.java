package com.scj.beilu.app.ui.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.NewsMyAttentionAuthorListPre;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.ui.news.adapter.NewsAuthorListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/5/13 11:03
 */
public class NewsMyAttentionAuthorListFrag extends BaseMvpFragment<NewsMyAttentionAuthorListPre.NewsMyAttentionAuthorListView,
        NewsMyAttentionAuthorListPre> implements NewsMyAttentionAuthorListPre.NewsMyAttentionAuthorListView, OnItemClickListener {

    private RecyclerView recy_attention_author_list;

    private NewsAuthorListAdapter mNewsAuthorListAdapter;

    private final int REQUEST_MY_ATTENTION_AUTHOR = 0xb1;

    private static final String LOAD_TYPE = "load_type";
    private int mLoadType;

    public static NewsMyAttentionAuthorListFrag newInstance(int load) {

        Bundle args = new Bundle();
        args.putInt(LOAD_TYPE, load);
        NewsMyAttentionAuthorListFrag fragment = new NewsMyAttentionAuthorListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLoadType = arguments.getInt(LOAD_TYPE);
        }
    }

    @Override
    public NewsMyAttentionAuthorListPre createPresenter() {
        return new NewsMyAttentionAuthorListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_news_my_attention_author_list;
    }

    @Override
    public void initView() {
        super.initView();
        recy_attention_author_list = findViewById(R.id.recy_attention_author_list);
        mNewsAuthorListAdapter = new NewsAuthorListAdapter(this);
        recy_attention_author_list.setAdapter(mNewsAuthorListAdapter);
        mNewsAuthorListAdapter.setOnItemClickListener(this);

        mRefreshLayout.autoRefresh();
        if (mLoadType == NewsAuthorInfoAct.LOAD_ATTENTION_AUTHOR) {
            mAppToolbar.setToolbarTitle("我的关注");
        } else {
            mAppToolbar.setToolbarTitle("更多推荐");
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        if (mLoadType == NewsAuthorInfoAct.LOAD_ATTENTION_AUTHOR) {
            getPresenter().getMyAttentionAuthorList(mCurrentPage);
        } else {
            getPresenter().getRecommendAuthorList(mCurrentPage);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        if (mLoadType == NewsAuthorInfoAct.LOAD_ATTENTION_AUTHOR) {
            getPresenter().getMyAttentionAuthorList(mCurrentPage);
        } else {
            getPresenter().getRecommendAuthorList(mCurrentPage);
        }
    }

    @Override
    public void onMyAttentionAuthorList(List<NewsAuthorInfoBean> authorInfoList) {
        mNewsAuthorListAdapter.setAuthorInfoList(authorInfoList);
    }

    @Override
    public void onNotifyAttentionStatus(int position) {
        mNewsAuthorListAdapter.notifyItemChanged(position);
        mFragmentActivity.setResult(Activity.RESULT_OK);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        NewsAuthorInfoBean authorInfoBean = null;
        if (entity instanceof NewsAuthorInfoBean) {
            authorInfoBean = (NewsAuthorInfoBean) entity;
        }
        switch (view.getId()) {
            case R.id.tv_news_recommend_attention_status:
                getPresenter().setAttentionAuthor(authorInfoBean.getUserId());
                break;
            case R.id.iv_news_author_avatar:
                Intent intent = new Intent(mFragmentActivity, NewsAuthorInfoAct.class);
                intent.putExtra(NewsAuthorInfoAct.AUTHOR_ID, authorInfoBean.getUserId());
                startActivityForResult(intent, REQUEST_MY_ATTENTION_AUTHOR);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MY_ATTENTION_AUTHOR && resultCode == RESULT_OK) {
            mRefreshLayout.autoRefresh();
        }
        if (requestCode == LoginAndRegisterAct.LOGIN_REQUEST) {
            mRefreshLayout.autoRefresh();
        }
    }
}
