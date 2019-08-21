package com.scj.beilu.app.ui.find;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.find.SearchInfoPre;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;
import com.scj.beilu.app.ui.preview.PicImagePreviewAct;
import com.scj.beilu.app.ui.find.adapter.FindInfoListAdapter;
import com.scj.beilu.app.ui.find.adapter.ReAttentionAdapter;
import com.scj.beilu.app.ui.news.adapter.HistoryListAdapter;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.FlowLayoutManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/10 18:02
 */
public class SearchFindFrag extends BaseMvpFragment<SearchInfoPre.SearchLoadHistoryView, SearchInfoPre>
        implements SearchInfoPre.SearchLoadHistoryView, OnItemClickListener,
        OnGroupItemClickListener<ArrayList<FindImageBean>>, TextWatcher {

    private EditText mEtSearchContent;
    private TextView mTvCancel;
    private RecyclerView mRvSearchHistoryList;
    private RecyclerView mRvRecommendList;
    private View mLoadingView;
    private LinearLayout mLLEmpty;
    private TextView mTvEmptyHint;
    private ViewStub mLoading;
    private LinearLayout mLlShowHistory;

    private HistoryListAdapter mHistoryListAdapter;
    private ReAttentionAdapter mAttentionAdapter;
    private FindInfoListAdapter mFindInfoListAdapter;

    private int mRecordPosition = -1;
    private static final int REQUEST_CODE = 0x001;
    private int mDynamicIdByIndex;
    private String mKeyName;


    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_find_search;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarDarkFont(true,0.2f)
                .titleBar(R.id.top_view)
                .keyboardEnable(true)
                .init();
        mRvSearchHistoryList = findViewById(R.id.rv_search_list);
        mRvRecommendList = findViewById(R.id.find_search_recycler);
        mEtSearchContent = findViewById(R.id.et_search_content);
        mTvCancel = findViewById(R.id.tv_cancel);
        mLlShowHistory = findViewById(R.id.ll_search_history_view);

        mAttentionAdapter = new ReAttentionAdapter(this);
        mAttentionAdapter.setOnItemClickListener(this);
        mRvRecommendList.setAdapter(mAttentionAdapter);

        mRvRecommendList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 0);
            }
        });

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mRvSearchHistoryList.setLayoutManager(flowLayoutManager);
        mRvSearchHistoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });

        mHistoryListAdapter = new HistoryListAdapter();
        mRvSearchHistoryList.setAdapter(mHistoryListAdapter);
        mHistoryListAdapter.setItemClickListener(this);

        //***********************************************start search
        mLoading = findViewById(R.id.view_stu_loading);
        mLLEmpty = findViewById(R.id.ll_load_empty_view);
        mTvEmptyHint = findViewById(R.id.tv_empty_hint);

        mFindInfoListAdapter = new FindInfoListAdapter(GlideApp.with(this), mFragmentActivity);
        mFindInfoListAdapter.setOnItemClickListener(this);
        mFindInfoListAdapter.setOnGroupItemClickListener(this);
        mRecyclerView.setAdapter(mFindInfoListAdapter);

        final LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.getItemAnimator().setChangeDuration(0);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(FindInfoListAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if (!GSYVideoManager.isFullState(mFragmentActivity)) {
                            GSYVideoManager.releaseAllVideos();
                            mFindInfoListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        mTvEmptyHint.setText("暂无搜索结果");

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentActivity.onBackPressed();
            }
        });

        mEtSearchContent.addTextChangedListener(this);
        mRefreshLayout.setVisibility(View.GONE);
        mLlShowHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCurrentPageData();
    }


    @Override
    public SearchInfoPre createPresenter() {
        return new SearchInfoPre(mFragmentActivity);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().startSearch(mKeyName, mCurrentPage);
    }

    @Override
    public void onSearchHistoryList(List<SearchHistoryEntity> searchHistoryList) {
        mHistoryListAdapter.setHistoryList(searchHistoryList);
    }

    @Override
    public void onUserList(List<RecommendUserInfoBean> userInfoList) {
        mAttentionAdapter.setUserList(userInfoList);
    }

    @Override
    public void onFindListResult(List<FindInfoBean> findList) {

        if (findList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mLLEmpty.setVisibility(View.VISIBLE);
        } else {
            hideSoftInput();
            mRecyclerView.setVisibility(View.VISIBLE);
            mLLEmpty.setVisibility(View.GONE);
            mFindInfoListAdapter.setFindInfoList(findList);
        }
    }

    @Override
    public void onAttentionResult(String result, int position) {
        ToastUtils.showToast(mFragmentActivity, result);
        mAttentionAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        Intent intent;

        FindInfoBean findInfo = null;
        if (entity instanceof FindInfoBean) {
            findInfo = (FindInfoBean) entity;
        }
        RecommendUserInfoBean userInfo = null;
        if (entity instanceof RecommendUserInfoBean) {
            userInfo = (RecommendUserInfoBean) entity;
        }

        switch (view.getId()) {
            case R.id.tv_history_tag://历史记录
                SearchHistoryEntity history = (SearchHistoryEntity) entity;
                mEtSearchContent.setText(history.getTagName());
                mEtSearchContent.setSelection(history.getTagName().length());
                break;
            case R.id.tv_item_attention://推荐 点击关注
                getPresenter().setAttention(userInfo.getUserId(), position);
                break;
            case R.id.iv_user_avatar://推荐 点击头像进入用户主页
                intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, (int) userInfo.getUserId());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_item_find_praise:
                getPresenter().setLike(findInfo.getDynamicId(), position);
                break;
            case R.id.ll_item_find_comment:
            case R.id.tv_item_find_txt:
                mRecordPosition = position;
                GSYVideoManager.releaseAllVideos();
                intent = new Intent(mFragmentActivity, FindDetailsAct.class);
                intent.putExtra(FindDetailsFrag.FIND_ID, findInfo.getDynamicId());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_item_find_share:
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(this);
                }
                mRecordPosition = position;
                mDynamicIdByIndex = findInfo.getDynamicId();
                getPresenter().startShareFind(this, findInfo);
                break;
            case R.id.tv_item_find_attention:
                getPresenter().setFocusUser(findInfo.getUserId());
                break;
            case R.id.iv_item_find_avatar:
                intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                int userId = findInfo.getIsOwn() == 0 ? findInfo.getUserId() : -1;
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, userId);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_item_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, findInfo.getDynamicPic(), position);
                break;
        }
    }

    @Override
    public void onItemClick(int groupPosition, int childPosition, ArrayList<FindImageBean> object, View view) {
        switch (view.getId()) {
            case R.id.iv_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, object, childPosition);
                break;
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onNotifyListChangeResult(String result, int position) {
        ToastUtils.showToast(mFragmentActivity, result);
        if (position != -1) {
            mFindInfoListAdapter.notifyItemChanged(position);
        } else {
            mFindInfoListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onModifyResult(int position) {
        if (position == mRecordPosition) {
            mFindInfoListAdapter.notifyItemChanged(mRecordPosition);
        } else {
            mFindInfoListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddForResult(int position) {
        //none
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (GSYVideoManager.backFromWindowFull(mFragmentActivity)) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            GSYVideoManager.onPause();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ModifyRecordBean record = data.getParcelableExtra(FindDetailsFrag.EXTRA_MODIFY_RESULT);
            getPresenter().modifyRecord(record, mRecordPosition);
        }
    }


    @Subscribe
    public void onShareResult(BaseResp resp) {
        getPresenter().setFindShareCount(mDynamicIdByIndex);
    }

    @Override
    public void onFindDel() {
        getPresenter().delFind(mDynamicIdByIndex);
    }

    @Override
    public void onShareCountResult() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onFindDelResult(ResultMsgBean result) {
        mFindInfoListAdapter.notifyItemRemoved(mRecordPosition);
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        mRecordPosition = -1;
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_REFRESH) {
            if (mCurrentPage == 0) {
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() == 0) {
            mLlShowHistory.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        } else {
            mLlShowHistory.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mCurrentPage = 0;
            mKeyName = s.toString();
            getPresenter().startSearch(mKeyName);
        }
    }
}
