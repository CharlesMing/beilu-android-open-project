package com.scj.beilu.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.home.HomeSearchUserPre;
import com.scj.beilu.app.mvp.home.bean.EventSearchTypeBean;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;
import com.scj.beilu.app.ui.find.FindDetailsFrag;
import com.scj.beilu.app.ui.mine.adapter.MeFocusAdapter;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:07
 */
public class HomeSearchUserFrag extends BaseMvpFragment<HomeSearchUserPre.HomeSearchView, HomeSearchUserPre>
        implements HomeSearchUserPre.HomeSearchView, OnItemClickListener<FocusUserInfoBean> {

    private View mLoadingView;
    private LinearLayout mLLEmpty;
    private TextView mTvEmptyHint;
    private ViewStub mLoading;
    private String mKeyName;

    private MeFocusAdapter mMeFocusAdapter;

    private int mPosition = -1;
    private int mUserId = -1;

    private static final int REQUEST_CODE = 0x001;

    @Override
    public HomeSearchUserPre createPresenter() {
        return new HomeSearchUserPre(mFragmentActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        getPresenter().searchUserInfoList(mKeyName, mCurrentPage);
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

        mLoading = findViewById(R.id.view_stu_loading);
        mLLEmpty = findViewById(R.id.ll_load_empty_view);
        mTvEmptyHint = findViewById(R.id.tv_empty_hint);

        mTvEmptyHint.setText("暂无搜索结果");

        mMeFocusAdapter = new MeFocusAdapter(GlideApp.with(mFragmentActivity));
        mRecyclerView.setAdapter(mMeFocusAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
        mMeFocusAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onUserInfoList(List<FocusUserInfoBean> userInfoList) {
        if (userInfoList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mLLEmpty.setVisibility(View.VISIBLE);
        } else {
            hideSoftInput();
            mRecyclerView.setVisibility(View.VISIBLE);
            mLLEmpty.setVisibility(View.GONE);
            mMeFocusAdapter.setFocusInfoList(userInfoList);
        }
    }

    @Override
    public void onNotifyListChangeResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        if (mPosition >= 0) {
            mMeFocusAdapter.notifyItemChanged(mPosition);
        }
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_REFRESH) {
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
    public void showError(int errorCode, String throwableContent) {
        if (mLoadingView != null) {
            mLoading.setVisibility(View.GONE);
        }
        ToastUtils.showToast(mFragmentActivity, throwableContent);
    }

    @Override
    public void onItemClick(int position, FocusUserInfoBean entity, View view) {
        switch (view.getId()) {
            case R.id.tv_item_attention:
                mPosition = position;
                mUserId = entity.getUserId();
                getPresenter().checkUserIsLogin(view.getId());
                break;
            case R.id.iv_user_avatar:
                Intent intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, entity.getUserId());
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void userStartAction(int viewId) {
        getPresenter().setAttentionUser(mUserId, mPosition);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ModifyRecordBean record = data.getParcelableExtra(FindDetailsFrag.EXTRA_MODIFY_RESULT);

        }
    }
}
