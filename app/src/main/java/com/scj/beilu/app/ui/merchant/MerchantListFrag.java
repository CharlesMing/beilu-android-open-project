package com.scj.beilu.app.ui.merchant;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.MerchantListPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoBean;
import com.scj.beilu.app.ui.merchant.adapter.MerchantListAdapter;
import com.scj.beilu.app.widget.ClearEditText;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:23
 */
public class MerchantListFrag extends BaseMvpFragment<MerchantListPre.MerchantListView, MerchantListPre>
        implements MerchantListPre.MerchantListView, View.OnClickListener, OnItemClickListener<MerchantInfoBean>, TextWatcher {

    private ClearEditText mEtSearchContent;
    private ImageButton mBtnBack;
    private ViewStub mViewEmpty;

    private static final String CITY_NAME = "city_name";

    private String mCityName;
    private String mKeyName;

    private MerchantListAdapter mMerchantListAdapter;

    public static MerchantListFrag newInstance(String cityName) {

        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);
        MerchantListFrag fragment = new MerchantListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCityName = arguments.getString(CITY_NAME);
        }
    }

    @Override
    public MerchantListPre createPresenter() {
        return new MerchantListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_list;
    }

    @Override
    public void initView() {
        super.initView();

        ImmersionBar.with(this)
                .statusBarDarkFont(true,0.2f)
                .titleBar(R.id.top_view)
                .keyboardEnable(true)
                .init();

        mEtSearchContent = findViewById(R.id.et_search_content);
        mBtnBack = findViewById(R.id.btn_back);

        mMerchantListAdapter = new MerchantListAdapter(this);
        mMerchantListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMerchantListAdapter);

        mBtnBack.setOnClickListener(this);
        mEtSearchContent.addTextChangedListener(this);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 10, 0, 10);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getMerchantList(mCurrentPage, mKeyName, mCityName);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getMerchantList(mCurrentPage, mKeyName, mCityName);
    }

    @Override
    public void onMerchantList(List<MerchantInfoBean> merchantInfoList) {
        if (merchantInfoList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            if (mViewEmpty == null) {
                mViewEmpty = findViewById(R.id.view_stu_empty);
                View inflate = mViewEmpty.inflate();
                TextView tvEmptyHint = inflate.findViewById(R.id.tv_empty_hint);
                tvEmptyHint.setText("城市开通中～");
            }
            mViewEmpty.setVisibility(View.VISIBLE);
        } else {
            if (mViewEmpty != null) {
                mViewEmpty.setVisibility(View.GONE);
            }
            mRecyclerView.setVisibility(View.VISIBLE);
            mMerchantListAdapter.setMerchantInfoList(merchantInfoList);
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                mFragmentActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(int position, MerchantInfoBean entity, View view) {
        switch (view.getId()) {
            case R.id.cl_merchant_parent:
                Intent intent = new Intent(mFragmentActivity, MerchantAct.class);
                intent.putExtra(MerchantAct.MERCHANT_ID, entity.getMerchantId());
                startActivity(intent);
                break;
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
        mKeyName = s.toString();
        mCurrentPage = 0;
        if (mKeyName.length() == 0) {
            mRefreshLayout.autoRefresh();
        } else {
            getPresenter().searchMerchant(mKeyName, mCityName);
        }
    }
}
