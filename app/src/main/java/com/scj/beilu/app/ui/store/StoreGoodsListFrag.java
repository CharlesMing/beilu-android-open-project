package com.scj.beilu.app.ui.store;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.GoodsListPre;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.store.adapter.GoodsListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/3 16:13
 * 根据分类查询商品列表
 */
public class StoreGoodsListFrag extends BaseMvpFragment<GoodsListPre.GoodsListView, GoodsListPre>
        implements GoodsListPre.GoodsListView, OnItemClickListener {

    private final int REQUEST_SHOPPING_CART = 0x010;

    private ViewStub mLoading;
    private RecyclerView mRvGoodsList;
    private View mLoadingView;

    private GoodsListAdapter mGoodsListAdapter;

    private int dividerRL = 0;
    private int dividerTB = 0;

    private static final String PRODUCTCATEID = "productCateId";

    private int mProductCateId;

    public static StoreGoodsListFrag newInstance(int productCateId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCTCATEID, productCateId);
        StoreGoodsListFrag fragment = new StoreGoodsListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mProductCateId = arguments.getInt(PRODUCTCATEID);
        }
    }

    @Override
    public GoodsListPre createPresenter() {
        return new GoodsListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_goods_list;
    }

    @Override
    public void initView() {
        super.initView();

        mLoading = findViewById(R.id.view_stu_loading);
        mRvGoodsList = findViewById(R.id.rv_goods_list);

        mGoodsListAdapter = new GoodsListAdapter(this);
        mGoodsListAdapter.setOnItemClickListener(this);
        mRvGoodsList.setAdapter(mGoodsListAdapter);

        if (isAdded()) {
            dividerRL = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_4);
            dividerTB = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_7);
        }

        mRvGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(dividerRL, dividerTB, dividerRL, dividerTB);
            }
        });


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getGoodsList(mProductCateId, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getGoodsList(mProductCateId, mCurrentPage);
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_STATE) {

            if (mCurrentPage == 0) {
                if (isShow) {
                    mRvGoodsList.setVisibility(View.GONE);
                    if (mLoadingView == null) {
                        mLoadingView = mLoading.inflate();
                    } else {
                        mLoading.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (mLoadingView != null) {
                        mLoading.setVisibility(View.GONE);
                    }
                    mRvGoodsList.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    public void onGoodsList(List<GoodsInfoBean> goodsInfoBeanList) {
        mGoodsListAdapter.setGoodsInfoBeans(goodsInfoBeanList);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
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


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOPPING_CART && resultCode == RESULT_OK) {
            setFragmentResult(resultCode, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOPPING_CART && resultCode == RESULT_OK) {
            mFragmentActivity.setResult(resultCode);
        }
    }

}
