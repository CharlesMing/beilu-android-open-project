package com.scj.beilu.app.ui.store;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.GoodsListBySearchPre;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.store.adapter.GoodsListAdapter;
import com.scj.beilu.app.ui.store.adapter.GoodsListPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/3 15:57
 */
public class StoreGoodsListByTypeFrag extends BaseMvpFragment<GoodsListBySearchPre.GoodsListBySearchView, GoodsListBySearchPre>
        implements GoodsListBySearchPre.GoodsListBySearchView, TabLayout.OnTabSelectedListener, TextWatcher, OnItemClickListener {

    private EditText mEtSearchContent;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageButton mBtnBack;
    private LinearLayout mLLEmpty;
    private TextView mTvEmptyHint;
    private ViewStub mLoading;
    private RecyclerView mRvGoodsList;
    private View mLoadingView;

    private static final String GOODSCATEGORYLIST = "type_list";
    private static final String POSITION = "position";
    private final int REQUEST_SHOPPING_CART = 0x010;

    private ArrayList<GoodsCategoryInfoBean> mGoodsCategoryInfoBeans;
    private int mPosition = 0;
    private GoodsListAdapter mGoodsListAdapter;

    private int dividerRL = 0;
    private int dividerTB = 0;

    private GoodsListPagerAdapter mGoodsListPagerAdapter;

    public static StoreGoodsListByTypeFrag newInstance(ArrayList<GoodsCategoryInfoBean> goodsCategoryInfoBeans
            , int position) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(GOODSCATEGORYLIST, goodsCategoryInfoBeans);
        args.putInt(POSITION, position);
        StoreGoodsListByTypeFrag fragment = new StoreGoodsListByTypeFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGoodsCategoryInfoBeans = arguments.getParcelableArrayList(GOODSCATEGORYLIST);
            mPosition = arguments.getInt(POSITION, mPosition);
        }
    }

    @Override
    public GoodsListBySearchPre createPresenter() {
        return new GoodsListBySearchPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_store_goods_type;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .titleBar(findViewById(R.id.ll_store_top))
                .statusBarDarkFont(true,0.2f).init();
        mEtSearchContent = findViewById(R.id.et_store_search);
        mViewPager = findViewById(R.id.view_pager_store);
        mTabLayout = findViewById(R.id.tab_store_type_name);
        mBtnBack = findViewById(R.id.btn_back);
        mLoading = findViewById(R.id.view_stu_loading);
        mRvGoodsList = findViewById(R.id.rv_goods_list);
        mLLEmpty = findViewById(R.id.ll_load_empty_view);
        mTvEmptyHint = findViewById(R.id.tv_empty_hint);
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

        mTabLayout.setupWithViewPager(mViewPager);

        mGoodsListPagerAdapter = new GoodsListPagerAdapter(getChildFragmentManager());
        mGoodsListPagerAdapter.setGoodsCategoryInfoBeans(mGoodsCategoryInfoBeans);
        mViewPager.setAdapter(mGoodsListPagerAdapter);

        if (mGoodsCategoryInfoBeans != null) {
            int size = mGoodsCategoryInfoBeans.size();
            for (int i = 0; i < size; i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabLayout(mGoodsCategoryInfoBeans.get(i).getProductCateName()));
            }
        }

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentActivity.onBackPressed();
            }
        });
        mTabLayout.addOnTabSelectedListener(this);
        mEtSearchContent.addTextChangedListener(this);
        mViewPager.setCurrentItem(mPosition);

        mTvEmptyHint.setText("暂无此类商品");
    }


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

    private View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mFragmentActivity).inflate(R.layout.layout_goods_tab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setText(title);
        return view;
    }

    private void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) return;
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        ImageView ivLine = view.findViewById(R.id.iv_tab_bottom_line);
        tvTab.setSelected(selected);
        ivLine.setSelected(selected);
        if (selected) {
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        } else {
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            if (mViewPager.getVisibility() == View.GONE) {
                mRefreshLayout.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
            }
        } else {
            if (mRefreshLayout.getVisibility() == View.GONE) {
                mViewPager.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
            }

            getPresenter().startSearch(mEtSearchContent.getText().toString(), mCurrentPage);
        }
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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().startSearch(mEtSearchContent.getText().toString(), mCurrentPage);
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
    public void onGoodsList(List<GoodsInfoBean> goodsInfoBeanList) {

        if (goodsInfoBeanList.size() == 0) {
            mRvGoodsList.setVisibility(View.GONE);
            mLLEmpty.setVisibility(View.VISIBLE);
        } else {
            mRvGoodsList.setVisibility(View.VISIBLE);
            mLLEmpty.setVisibility(View.GONE);
            mGoodsListAdapter.setGoodsInfoBeans(goodsInfoBeanList);
        }

    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
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
