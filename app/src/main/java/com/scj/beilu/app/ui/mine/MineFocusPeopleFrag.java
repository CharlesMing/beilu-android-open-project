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
import com.scj.beilu.app.mvp.mine.MineFocusPre;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;
import com.scj.beilu.app.ui.mine.adapter.MeFocusAdapter;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.util.ToastUtils;

import java.util.List;

/**
 * author: SunGuiLan
 * date:2019/3/2
 * descriptin:我关注的人
 */

public class MineFocusPeopleFrag extends BaseMvpFragment<MineFocusPre.MeAttentionView, MineFocusPre>
        implements MineFocusPre.MeAttentionView, OnItemClickListener<FocusUserInfoBean> {

    private RecyclerView mRvMeFocusPeople;
    private MeFocusAdapter mMeFocusAdapter;
    private int mPosition = -1;

    private static final int REQUEST_CODE = 0x001;

    @Override
    public void initView() {
        super.initView();
        mRvMeFocusPeople = findViewById(R.id.rv_me_focus_people);
        mMeFocusAdapter = new MeFocusAdapter(GlideApp.with(mFragmentActivity));
        mRvMeFocusPeople.setAdapter(mMeFocusAdapter);
        mRvMeFocusPeople.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
        mMeFocusAdapter.setOnItemClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.frag_me_atttention_people;
    }

    @NonNull
    @Override
    public MineFocusPre createPresenter() {
        return new MineFocusPre(mFragmentActivity);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getMyFocusNumberList(mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getMyFocusNumberList(mCurrentPage);
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
    public void onMyFocusNumberList(List<FocusUserInfoBean> focusList) {
        mMeFocusAdapter.setFocusInfoList(focusList);
    }

    @Override
    public void onNotifyListChangeResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        if (mPosition >= 0) {
            mMeFocusAdapter.notifyItemChanged(mPosition);
        }
    }

    @Override
    public void onItemClick(int position, FocusUserInfoBean focusUserInfoBean, View view) {
        switch (view.getId()) {
            case R.id.tv_item_attention:
                mPosition = position;
                getPresenter().setAttentionUser(focusUserInfoBean.getUserId(), position);
                break;
            case R.id.iv_user_avatar:
                Intent intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, focusUserInfoBean.getUserId());
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
