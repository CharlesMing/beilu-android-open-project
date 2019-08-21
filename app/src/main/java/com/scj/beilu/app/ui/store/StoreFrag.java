package com.scj.beilu.app.ui.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.banner.Banner;
import com.mx.pro.lib.banner.BannerConfig;
import com.mx.pro.lib.banner.listener.OnBannerListener;
import com.mx.pro.lib.banner.loader.ImageLoader;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.StorePre;
import com.scj.beilu.app.mvp.store.bean.GoodsBannerInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.store.adapter.GoodsCategoryListAdapter;
import com.scj.beilu.app.ui.store.adapter.GoodsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:25
 */
public class StoreFrag extends BaseMvpFragment<StorePre.StoreView, StorePre>
        implements StorePre.StoreView, OnItemClickListener, OnBannerListener, View.OnClickListener {

    private RecyclerView mRvStoreNavList;
    private RecyclerView mRvGoodsList;
    private TextView mTvGoodsCount;
    private TextView mTvSearch;
    private Banner mBanner;
    private FrameLayout mFlShoppingCart;
    private ImageButton mBtnBack;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    private GoodsCategoryListAdapter mStoreButtonAdapter;
    private GoodsListAdapter mGoodsListAdapter;
    private int dividerRL = 0;
    private int dividerTB = 0;

    private final int REQUEST_SHOPPING_CART = 0x010;

    private ArrayList<GoodsCategoryInfoBean> mGoodsCategoryInfoBeans;

    @Override
    public StorePre createPresenter() {
        return new StorePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_store;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .titleBar(findViewById(R.id.ll_store_top))
                .statusBarDarkFont(true,0.2f).init();
        mBanner = findViewById(R.id.banner_store);
        mRvStoreNavList = findViewById(R.id.rv_store_nav_list);
        mRvGoodsList = findViewById(R.id.rv_goods_list);
        mTvGoodsCount = findViewById(R.id.tv_store_shopping_car_count);
        mFlShoppingCart = findViewById(R.id.fl_shopping_car);
        mTvSearch = findViewById(R.id.tv_store_search);
        mBtnBack = findViewById(R.id.btn_back);

        mOriginalRequest = GlideApp.with(this).asDrawable().centerCrop();
        mThumbnailRequest = GlideApp.with(this).asDrawable().centerCrop();

        mBanner.getViewPager().setPageMargin(45);
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setOnBannerListener(this);

        mStoreButtonAdapter = new GoodsCategoryListAdapter(this);
        mStoreButtonAdapter.setItemClickListener(this);
        mRvStoreNavList.setAdapter(mStoreButtonAdapter);

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

        mBtnBack.setOnClickListener(this);
        mFlShoppingCart.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCartCount();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getCurrentPageData();
    }


    @Override
    public void onBannerList(List<GoodsBannerInfoBean> bannerList) {
        mBanner.setImages(bannerList);
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                GoodsBannerInfoBean bannerPath = (GoodsBannerInfoBean) path;
                ImageView image = imageView.findViewById(R.id.banner_image);
                mOriginalRequest
                        .load(bannerPath.getAdverPicAddr())
                        .thumbnail(mThumbnailRequest.load(bannerPath.getAdverPicZip()))
                        .dontAnimate()
                        .into(image);
            }
        });
        mBanner.start();
    }

    @Override
    public void onCategoryList(ArrayList<GoodsCategoryInfoBean> categoryList) {
        mGoodsCategoryInfoBeans = categoryList;
        mStoreButtonAdapter.setGoodsCategoryInfoBeans(mGoodsCategoryInfoBeans);
    }

    @Override
    public void onGoodsList(List<GoodsInfoBean> goodsList) {
        mGoodsListAdapter.setGoodsInfoBeans(goodsList);
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_REFRESH && !isShow) {
            mRefreshLayout.finishRefresh(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null)
            mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null)
            mBanner.stopAutoPlay();
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        if (entity instanceof GoodsInfoBean) {
            GoodsInfoBean info = (GoodsInfoBean) entity;
            Intent intent = new Intent(mFragmentActivity, StoreInfoAct.class);
            intent.putExtra(StoreInfoAct.PRODUCT_ID, info.getProductId());
            startActivityForResult(intent, REQUEST_SHOPPING_CART);
        } else if (entity instanceof GoodsCategoryInfoBean) {
            if (mGoodsCategoryInfoBeans == null) return;
            startForResult(StoreGoodsListByTypeFrag.newInstance(mGoodsCategoryInfoBeans, position),
                    REQUEST_SHOPPING_CART);
        }
    }

    @Override
    public void OnBannerClick(int position) {
        // TODO: 2019/3/28
    }

    @Override
    public void onCartCount(int count) {
        mTvGoodsCount.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        mTvGoodsCount.setText(String.valueOf(count));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_shopping_car:
                Intent intent = new Intent(mFragmentActivity, ShoppingCarAct.class);
                startActivityForResult(intent, REQUEST_SHOPPING_CART);
                break;
            case R.id.btn_back:
                mFragmentActivity.onBackPressed();
                break;
            case R.id.tv_store_search:
                if (mGoodsCategoryInfoBeans == null) return;
                startForResult(StoreGoodsListByTypeFrag.newInstance(mGoodsCategoryInfoBeans, 0),
                        REQUEST_SHOPPING_CART);
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOPPING_CART && resultCode == RESULT_OK) {
            getPresenter().getCartCount();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOPPING_CART && resultCode == RESULT_OK) {
            getPresenter().getCartCount();
        }
    }
}
