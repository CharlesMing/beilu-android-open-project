package com.scj.beilu.app.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.MineFansPre;
import com.scj.beilu.app.mvp.mine.bean.MeFansInfoBean;
import com.scj.beilu.app.ui.mine.adapter.MeFansAdapter;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.util.ToastUtils;

import java.util.List;

public class MineFansFrag extends BaseMvpFragment<MineFansPre.MeFansView, MineFansPre> implements MineFansPre.MeFansView, OnItemClickListener<MeFansInfoBean> {

    private RecyclerView mRvMeFans;
    private MeFansAdapter mMeFansAdapter;
    private int mPosition=-1;

    private static final int REQUEST_CODE = 0x001;

    @Override
    public MineFansPre createPresenter() {
        return new MineFansPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_me_fans;
    }

    @Override
    public void initView() {
        super.initView();
        mRvMeFans = findViewById(R.id.rv_me_fans);
        mMeFansAdapter = new MeFansAdapter(GlideApp.with(mFragmentActivity));
        mRvMeFans.setAdapter(mMeFansAdapter);
        mMeFansAdapter.setOnItemClickListener(this);
        mRvMeFans.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getMyFansList(mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getMyFansList(mCurrentPage);
    }

    @Override
    public void onMyFansResult(List<MeFansInfoBean> fansList) {
        mMeFansAdapter.setFansInfoList(fansList);
    }

    @Override
    public void onNotifyListChangeResult(String result) {
        ToastUtils.showToast(mFragmentActivity,result);
        if (mPosition >= 0) {
            mMeFansAdapter.notifyItemChanged(mPosition);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, MeFansInfoBean meFansInfoBean, View view) {
        switch (view.getId()) {
            case R.id.tv_item_attention:
                mPosition = position;
                getPresenter().setAttentionUser(meFansInfoBean.getUserId(), mPosition);
                break;
            case R.id.iv_user_avatar:
                Intent intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, meFansInfoBean.getUserId());
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mRefreshLayout.autoRefresh();
        }
    }
}
