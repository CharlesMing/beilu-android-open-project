package com.scj.beilu.app.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.ImageView;

import com.mx.pro.lib.banner.Banner;
import com.mx.pro.lib.banner.loader.ImageLoader;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.NewsListPre;
import com.scj.beilu.app.mvp.news.bean.NewsBannerBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsNavBean;
import com.scj.beilu.app.ui.news.adapter.NewsListAdapter;
import com.scj.beilu.app.widget.DividerItemDecorationToPadding;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/11 14:12
 * 新闻列表 只有推荐带Banner
 */
public class NewsListFrag extends BaseMvpFragment<NewsListPre.NewsListView, NewsListPre>
        implements NewsListPre.NewsListView, OnItemClickListener {

    private Banner mBanner;

    private NewsListAdapter mNewsListAdapter;

    private static final String NAV_BEAN = "NAV_BEAN";
    private NewsNavBean mNewsNavBean;
    private boolean mLoadBanner;
    private GlideRequests mGlideOriginal;
    private GlideRequests mGlideThumbnail;

    public static NewsListFrag newInstance(NewsNavBean navContent) {

        Bundle args = new Bundle();
        args.putParcelable(NAV_BEAN, navContent);
        NewsListFrag fragment = new NewsListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mNewsNavBean = arguments.getParcelable(NAV_BEAN);
            mLoadBanner = mNewsNavBean.getNewsCateId() == 1;
        }
    }

    @Override
    public NewsListPre createPresenter() {
        return new NewsListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return mLoadBanner ? R.layout.frag_news_banner_list : R.layout.frag_news_list;
    }

    @Override
    public void initView() {
        super.initView();

        if (mLoadBanner) {
            mBanner = findViewById(R.id.banner_news);
        }

        mGlideOriginal = GlideApp.with(this);
        mGlideThumbnail = GlideApp.with(this);

        mRecyclerView.addItemDecoration(new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.VERTICAL));
        mNewsListAdapter = new NewsListAdapter(mFragmentActivity, mGlideOriginal);
        mNewsListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mNewsListAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        mRefreshLayout.autoRefresh();
        mRefreshLayout.setEnableLoadMore(true);

    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        mRefreshLayout.finishRefresh(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }


    @Override
    public void onNewsList(List<NewsInfoBean> newsList) {
        mRefreshLayout.finishRefresh(true);
        mNewsListAdapter.setNewsListEntityList(newsList);
    }


    @Override
    public void onBannerList(List<NewsBannerBean> bannerBeanList) {
        mRefreshLayout.finishRefresh(true);
        if (bannerBeanList == null) return;
        mBanner.setImages(bannerBeanList);
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                NewsBannerBean bannerPath = (NewsBannerBean) path;
                ImageView image = imageView.findViewById(R.id.banner_image);
                mGlideOriginal
                        .load(bannerPath.getAdverPicAddr())
                        .thumbnail(mGlideThumbnail.load(bannerPath.getAdverPicZip()))
                        .dontAnimate()
                        .into(image);
            }

        });
        mBanner.start();
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        NewsInfoBean infoBean = (NewsInfoBean) entity;
        NewsDetailsAct.startNewsDetailsAct(mFragmentActivity, infoBean);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getNewsList(mNewsNavBean, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getNewsList(mNewsNavBean, mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }
}
