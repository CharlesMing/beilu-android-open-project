package com.scj.beilu.app.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.NewsAttentionPre;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.ui.news.adapter.NewsAttentionAuthorListAdapter;
import com.scj.beilu.app.ui.news.adapter.NewsAuthorListAdapter;
import com.scj.beilu.app.ui.news.adapter.NewsListAdapter;
import com.scj.beilu.app.widget.DividerItemDecorationToPadding;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/5/11 17:43
 */
public class NewsAttentionListFrag extends BaseMvpFragment<NewsAttentionPre.NewsAttentionView, NewsAttentionPre>
        implements NewsAttentionPre.NewsAttentionView, OnItemClickListener {

    private ViewStub view_stu_not_attention_list, view_stu_attention_list;
    private View mViewAttentionList, mViewNoAttentionList;

    private NewsAuthorListAdapter mNewsRecommendAuthorListAdapter;
    private NewsAttentionAuthorListAdapter mNewsAttentionAuthorListAdapter;
    private NewsListAdapter mNewsListAdapter;

    private final int REQUEST_MY_ATTENTION_AUTHOR = 0xb1;
    private final int REQUEST_NEWS_CONTENT = 0xb2;

    private boolean isHaveAttention;


    @Override
    public NewsAttentionPre createPresenter() {
        return new NewsAttentionPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_news_my_attention_list;
    }

    @Override
    public void initView() {
        super.initView();
        view_stu_not_attention_list = findViewById(R.id.view_stu_not_attention_list);
        view_stu_attention_list = findViewById(R.id.view_stu_attention_list);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().checkUserIsLogin(0);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getRecommendNewsList(isHaveAttention ? 2 : 1, mCurrentPage);
    }

    @Override
    public void userIsLogin(boolean login, int id) {
        if (login) {
            getPresenter().getAttentionList();
        } else {
            getPresenter().getRecommendAuthorList();
        }
    }

    @Override
    public void onAttentionList(List<NewsAuthorInfoBean> authorInfoList) {
        initAttentionList();
        mNewsAttentionAuthorListAdapter.setAuthorInfoList(authorInfoList);
    }

    @Override
    public void onInitRecommendView() {
        initRecommendList();
    }

    @Override
    public void onNotAttentionList(List<NewsAuthorInfoBean> authorInfoList) {
        mNewsRecommendAuthorListAdapter.setAuthorInfoList(authorInfoList);
    }

    @Override
    public void onNewsList(List<NewsInfoBean> newsInfoList) {
        mNewsListAdapter.setNewsListEntityList(newsInfoList);
    }

    /**
     * 在有关注的情况下
     */
    private void initAttentionList() {
        isHaveAttention = true;
        view_stu_not_attention_list.setVisibility(View.GONE);
        if (mViewAttentionList == null) {
            mViewAttentionList = view_stu_attention_list.inflate();
            RecyclerView recyAuthorList = mViewAttentionList.findViewById(R.id.recycler_news_attention_list);
            RecyclerView recycler_news_author_content_list = mViewAttentionList.findViewById(R.id.recycler_news_author_content_list);

            mNewsAttentionAuthorListAdapter = new NewsAttentionAuthorListAdapter(this);
            mNewsAttentionAuthorListAdapter.setOnItemClickListener(this);
            recyAuthorList.setAdapter(mNewsAttentionAuthorListAdapter);

            mNewsListAdapter = new NewsListAdapter(mFragmentActivity, GlideApp.with(this));
            mNewsListAdapter.setOnItemClickListener(this);
            recycler_news_author_content_list.setAdapter(mNewsListAdapter);
            recycler_news_author_content_list.addItemDecoration(new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.VERTICAL));
        } else {
            view_stu_attention_list.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 在没有关注的情况下
     */
    private void initRecommendList() {
        isHaveAttention = false;
        view_stu_attention_list.setVisibility(View.GONE);
        if (mViewNoAttentionList == null) {
            mViewNoAttentionList = view_stu_not_attention_list.inflate();
            RecyclerView recycler_news_recommend_author = mViewNoAttentionList.findViewById(R.id.recycler_news_recommend_author);
            RecyclerView recycler_news_recommend_list = mViewNoAttentionList.findViewById(R.id.recycler_news_recommend_list);
            mViewNoAttentionList.findViewById(R.id.fl_news_author_more)
                    .setOnClickListener(view -> startAuthorInfo(NewsAuthorInfoAct.LOAD_RECOMMEND_AUTHOR));

            mNewsRecommendAuthorListAdapter = new NewsAuthorListAdapter(this);
            mNewsRecommendAuthorListAdapter.setOnItemClickListener(this);
            recycler_news_recommend_author.setAdapter(mNewsRecommendAuthorListAdapter);
            recycler_news_recommend_author.getItemAnimator().setChangeDuration(0);

            mNewsListAdapter = new NewsListAdapter(mFragmentActivity, GlideApp.with(this));
            mNewsListAdapter.setOnItemClickListener(this);
            recycler_news_recommend_list.setAdapter(mNewsListAdapter);
            recycler_news_recommend_list.addItemDecoration(new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.VERTICAL));
        } else {
            view_stu_not_attention_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNotifyAttentionStatus(int position) {
        mNewsRecommendAuthorListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        NewsAuthorInfoBean authorInfoBean = null;
        Intent intent;
        if (entity instanceof NewsAuthorInfoBean) {
            authorInfoBean = (NewsAuthorInfoBean) entity;
        }
        switch (view.getId()) {
            case R.id.tv_news_recommend_attention_status:
                getPresenter().setAttentionAuthor(authorInfoBean.getUserId());
                break;
            case R.id.iv_news_author_avatar:
                startAuthorInfo(authorInfoBean.getUserId());
                break;
            case R.id.iv_news_my_attention_author:
                if (position == 3) {
                    startAuthorInfo(NewsAuthorInfoAct.LOAD_ATTENTION_AUTHOR);
                } else {
                    startAuthorInfo(authorInfoBean.getUserId());
                }
                break;
            case R.id.item_news_view_id:
                NewsInfoBean infoBean = (NewsInfoBean) entity;
                intent = new Intent(mFragmentActivity, NewsDetailsAct.class);
                intent.putExtra(NewsDetailsAct.EXTRA_CONTENT, infoBean);
                startActivityForResult(intent, REQUEST_NEWS_CONTENT);
                break;
        }
    }

    private void startAuthorInfo(int authorId) {
        Intent intent = new Intent(mFragmentActivity, NewsAuthorInfoAct.class);
        intent.putExtra(NewsAuthorInfoAct.AUTHOR_ID, authorId);
        startActivityForResult(intent, REQUEST_MY_ATTENTION_AUTHOR);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_MY_ATTENTION_AUTHOR || requestCode == REQUEST_NEWS_CONTENT) && resultCode == RESULT_OK) {
            mRefreshLayout.autoRefresh();
        } else if (requestCode == LoginAndRegisterAct.LOGIN_REQUEST) {
            mRefreshLayout.autoRefresh();
        }
    }
}
