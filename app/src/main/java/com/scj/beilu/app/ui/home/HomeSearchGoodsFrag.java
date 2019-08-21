package com.scj.beilu.app.ui.home;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.home.bean.EventSearchTypeBean;
import com.scj.beilu.app.mvp.store.GoodsListBySearchPre;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.store.StoreInfoAct;
import com.scj.beilu.app.ui.store.adapter.GoodsListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:07
 */
public class HomeSearchGoodsFrag extends BaseMvpFragment<GoodsListBySearchPre.GoodsListBySearchView, GoodsListBySearchPre>
        implements GoodsListBySearchPre.GoodsListBySearchView, OnItemClickListener {

    private View mLoadingView;
    private LinearLayout mLLEmpty;
    private TextView mTvEmptyHint;
    private ViewStub mLoading;

    private String mKeyName;

    private GoodsListAdapter mGoodsListAdapter;

    private int dividerRL = 0;
    private int dividerTB = 0;

    private final int REQUEST_SHOPPING_CART = 0x010;

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

        mTvEmptyHint.setText("暂无搜索结果");
        mGoodsListAdapter = new GoodsListAdapter(this);
        mGoodsListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mGoodsListAdapter);

        if (isAdded()) {
            dividerRL = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_4);
            dividerTB = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_7);
        }

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(dividerRL, dividerTB, dividerRL, dividerTB);
            }
        });

    }


    @Override
    public GoodsListBySearchPre createPresenter() {
        return new GoodsListBySearchPre(mFragmentActivity);
    }

    @Override
    public void onGoodsList(List<GoodsInfoBean> goodsInfoBeanList) {
        if (goodsInfoBeanList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mLLEmpty.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mLLEmpty.setVisibility(View.GONE);
            mGoodsListAdapter.setGoodsInfoBeans(goodsInfoBeanList);
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_STATE) {
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
    public void onItemClick(int position, Object entity, View view) {
        if (entity instanceof GoodsInfoBean) {
            GoodsInfoBean info = (GoodsInfoBean) entity;
            Intent intent = new Intent(mFragmentActivity, StoreInfoAct.class);
            intent.putExtra(StoreInfoAct.PRODUCT_ID, info.getProductId());
            startActivityForResult(intent, REQUEST_SHOPPING_CART);
        }
    }
}
