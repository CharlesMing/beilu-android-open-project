package com.scj.beilu.app.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.MyCollectPre;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.mine.adapter.CollectGoodsListAdapter;
import com.scj.beilu.app.ui.mine.adapter.CollectNewsListAdapter;
import com.scj.beilu.app.ui.news.NewsDetailsAct;
import com.scj.beilu.app.ui.store.StoreInfoAct;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 16:46
 */
public class MineCollectFrag extends BaseMvpFragment<MyCollectPre.MyCollectView, MyCollectPre>
        implements MyCollectPre.MyCollectView, OnItemClickListener {

    private static final String INDEX = "index";
    private int mIndex;
    private final int REQUEST_NEWS_DETAILS = 0x010;
    private final int REQUEST_GOODS = 0x011;

    private CollectNewsListAdapter mCollectNewsListAdapter;
    private CollectGoodsListAdapter mCollectGoodsListAdapter;

    public static MineCollectFrag newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        MineCollectFrag fragment = new MineCollectFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mIndex = arguments.getInt(INDEX);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        if (mIndex == 0) {
            getPresenter().getMyCollectNews(mCurrentPage);
        } else {
            getPresenter().getMyCollectGoods(mCurrentPage);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        if (mIndex == 0) {
            getPresenter().getMyCollectNews(mCurrentPage);
        } else {
            getPresenter().getMyCollectGoods(mCurrentPage);
        }
    }

    @Override
    public MyCollectPre createPresenter() {
        return new MyCollectPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_collect_list;
    }

    @Override
    public void initView() {
        super.initView();

        if (mIndex == 0) {
            mCollectNewsListAdapter = new CollectNewsListAdapter(this);
            mCollectNewsListAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mCollectNewsListAdapter);
        } else {
            mCollectGoodsListAdapter = new CollectGoodsListAdapter(this);
            mCollectGoodsListAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mCollectGoodsListAdapter);
        }

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onNewsList(List<NewsInfoBean> newsList) {
        mCollectNewsListAdapter.setNewsInfoList(newsList);
    }

    @Override
    public void onGoodsList(List<GoodsInfoBean> goodsInfoBeanList) {
        mCollectGoodsListAdapter.setGoodsInfoList(goodsInfoBeanList);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        if (entity instanceof NewsInfoBean) {
            NewsInfoBean infoBean = (NewsInfoBean) entity;
//            NewsDetailsAct.startNewsDetailsAct(mFragmentActivity, infoBean);
            Intent intent = new Intent(mFragmentActivity, NewsDetailsAct.class);
            intent.putExtra(NewsDetailsAct.EXTRA_CONTENT, infoBean);
            startActivityForResult(intent, REQUEST_NEWS_DETAILS);
        } else if (entity instanceof GoodsInfoBean) {
            GoodsInfoBean info = (GoodsInfoBean) entity;
            Intent intent = new Intent(mFragmentActivity, StoreInfoAct.class);
            intent.putExtra(StoreInfoAct.PRODUCT_ID, info.getProductId());
            startActivityForResult(intent, REQUEST_GOODS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mRefreshLayout.autoRefresh();
        }
    }
}
