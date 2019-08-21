package com.scj.beilu.app.ui.find;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.FindInfoPre;
import com.scj.beilu.app.mvp.find.FindInfoView;
import com.scj.beilu.app.mvp.find.bean.EventAttentionNotify;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;
import com.scj.beilu.app.ui.preview.PicImagePreviewAct;
import com.scj.beilu.app.ui.find.adapter.FindInfoListAdapter;
import com.scj.beilu.app.ui.find.adapter.ReAttentionAdapter;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.util.ToastUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-热门/关注
 */
public class FindInfoAttentionListFrag extends BaseMvpFragment<FindInfoView, FindInfoPre<FindInfoView>>
        implements FindInfoView, OnItemClickListener, OnGroupItemClickListener<ArrayList<FindImageBean>> {

    private int mRecordPosition = -1;
    private static final int REQUEST_CODE = 0x001;
    private int mDynamicIdByIndex;
    private ViewStub mFindInfoAttentionEmpty;
    private RecyclerView mRvRecommendList;

    private static final String FIND_INDEX = "INDEX";
    private int mIndex = 0;

    private FindInfoListAdapter mFindInfoListAdapter;
    private ReAttentionAdapter mAttentionAdapter;

    private AttentionListener mAttentionListener;
    private int mAttentionUserId = -1;

    public void setAttentionListener(AttentionListener attentionListener) {
        mAttentionListener = attentionListener;
    }

    public static FindInfoAttentionListFrag newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(FIND_INDEX, position);
        FindInfoAttentionListFrag fragment = new FindInfoAttentionListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mIndex = arguments.getInt(FIND_INDEX, mIndex);
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    @Override
    public FindInfoPre createPresenter() {
        return new FindInfoPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_find_main_rv;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
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
            getPresenter().getHotList(mCurrentPage);
        } else {
            getPresenter().getAttentionFindList(mCurrentPage);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        if (mIndex == 0) {
            getPresenter().getHotList(mCurrentPage);
        } else {
            getPresenter().getAttentionFindList(mCurrentPage);
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onFindListResult(List<FindInfoBean> findList) {

        if (mIndex == 0) {
            //none
        } else {

            //是否显示推荐
            if (findList.size() == 0) {

                if (mFindInfoAttentionEmpty == null) {
                    mFindInfoAttentionEmpty = findViewById(R.id.view_find_info_attention_empty);
                    View inflate = mFindInfoAttentionEmpty.inflate();
                    mRvRecommendList = inflate.findViewById(R.id.find_search_recycler);
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
                }
                mFindInfoAttentionEmpty.setVisibility(View.VISIBLE);
                getPresenter().getRecommendUserList();
            } else {
                if (mFindInfoAttentionEmpty != null) {
                    mFindInfoAttentionEmpty.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }

        }

        mFindInfoListAdapter.setFindInfoList(findList);

    }

    @Override
    public void onUserList(List<RecommendUserInfoBean> userInfoList) {
        mRecyclerView.setVisibility(View.GONE);
        mAttentionAdapter.setUserList(userInfoList);
    }

    @Override
    public void onAttentionResult(String result, int position) {
        ToastUtils.showToast(mFragmentActivity, result);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onNotifyListChangeResult(String result, int position) {

        if (mAttentionListener != null && mAttentionUserId != -1) {
            mAttentionListener.onAttention(mIndex, mAttentionUserId);
            mAttentionUserId = -1;
        }

        if (result != null) {
            ToastUtils.showToast(mFragmentActivity, result);
        }

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
        mFindInfoListAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(position);
    }


    @Override
    public void onItemClick(int position, Object object, View view) {
        Intent intent;

        FindInfoBean entity = null;
        if (object instanceof FindInfoBean) {
            entity = (FindInfoBean) object;
            if (entity.getDynamicId() == 0 && view.getId() != R.id.iv_item_find_pic) {
                ToastUtils.showToast(mFragmentActivity, "文件正在上传中，请稍后再试");
                return;
            }
        }


        RecommendUserInfoBean userInfo = null;
        if (object instanceof RecommendUserInfoBean) {
            userInfo = (RecommendUserInfoBean) object;
        }

        switch (view.getId()) {

            case R.id.tv_item_attention://推荐 点击关注
                getPresenter().setAttention(userInfo.getUserId(), position);
                break;
            case R.id.iv_user_avatar://推荐 点击头像进入用户主页
                intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, (int) userInfo.getUserId());
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.ll_item_find_praise:
                getPresenter().setLike(entity.getDynamicId(), position);
                break;
            case R.id.ll_item_find_comment:
            case R.id.tv_item_find_txt:
                mRecordPosition = position;
                GSYVideoManager.releaseAllVideos();
                intent = new Intent(mFragmentActivity, FindDetailsAct.class);
                intent.putExtra(FindDetailsFrag.FIND_ID, entity.getDynamicId());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_item_find_share:
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(this);
                }
                mRecordPosition = position;
                mDynamicIdByIndex = entity.getDynamicId();
                getPresenter().startShareFind(this, entity);
                break;
            case R.id.tv_item_find_attention://设置关注状态:

                int attentionUserId = entity.getUserId();
                mAttentionUserId = attentionUserId;
                getPresenter().setFocusUser(attentionUserId);
                break;
            case R.id.iv_item_find_avatar:
                intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                int userId = entity.getIsOwn() == 0 ? entity.getUserId() : -1;
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, userId);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_item_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, entity.getDynamicPic(), position);
                break;

        }

    }

    @Override
    public void onItemClick(int groupPosition, int childPosition, ArrayList<FindImageBean> picList, View view) {
        switch (view.getId()) {
            case R.id.iv_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, picList, childPosition);
                break;
        }
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
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
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
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyAttention(EventAttentionNotify notify) {
        if (notify.actionAttentionIndex == 1) {
            mCurrentPage = 0;
            getPresenter().getAttentionFindList(mCurrentPage);
        }
    }
}
