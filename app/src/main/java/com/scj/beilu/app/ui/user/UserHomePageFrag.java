package com.scj.beilu.app.ui.user;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.user.UserHomePagePre;
import com.scj.beilu.app.mvp.user.bean.AvatarResult;
import com.scj.beilu.app.mvp.user.bean.UserDetailsBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.ui.act.UserInfoEditAct;
import com.scj.beilu.app.ui.dialog.UserHomePageDialog;
import com.scj.beilu.app.ui.find.FindDetailsAct;
import com.scj.beilu.app.ui.find.FindDetailsFrag;
import com.scj.beilu.app.ui.find.adapter.FindInfoListAdapter;
import com.scj.beilu.app.ui.preview.PicImagePreviewAct;
import com.scj.beilu.app.util.ToastUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.scj.beilu.app.ui.act.UserInfoEditAct.getDiskCacheDir;
import static com.scj.beilu.app.ui.user.UserInfoHomePageAct.EXTRA_USER_ID;

/**
 * @author Mingxun
 * @time on 2019/3/4 18:45
 * 我的主页信息</p>
 * 其他人的主页信息
 */
@RuntimePermissions
public class UserHomePageFrag extends BaseMvpFragment<UserHomePagePre.UserHomePageView, UserHomePagePre>
        implements UserHomePagePre.UserHomePageView, View.OnClickListener,
        AppBarLayout.OnOffsetChangedListener, OnItemClickListener<FindInfoBean>,
        UserHomePageDialog.onClickListener, OnGroupItemClickListener<ArrayList<FindImageBean>> {

    private final int REQUEST_COMMENT_CODE = 0x058;
    public static final int REQUEST_PICTURE_CODE = 0x025;
    private final int REQUEST_USER_EDIT = 0xb1;

    private AppBarLayout mAppBarLayout;
    private LinearLayout mLlTopUserInfo;
    private ImageView mIvAvatarLarge, mIvAvatarSmall;
    private ImageView mIvUserBackground;
    private TextView mTvUserNameSmall, mTvUserNameLarge;
    private TextView mTvUserInfoAction;
    private TextView mTvUserDescription;
    private LinearLayout mLlUserPraise, mLlUserMyAttention, mLlUserAttentionToMy, mLlUserDynamic;
    private TextView mTvPraiseCount, mTvMyAttentionCount, mTvAttentionToMyCount, mTvDynamicCount;
    private TextView mTvPraiseTitle, mTvMyAttentionTitle, mTvAttentionToMyTitle, mTvDynamicTitle;
    private TextView mTvDynamicHeader;
    private View mViewElevation;
    private View mViewTransparent;
    private LinearLayout mLlEmptyView;
    private TextView mTvEmptyHint;

    // 详情查看效果图
    private Drawable mDefDrawable;
    private int mWhiteColor;
    //滑动到顶部，白色背景添加阴影的高度
    private int mElevationHeight;

    private Drawable mDrawableBtnAction;
    private int mTextViewActionHeight = 0;
    private int mTextViewActionTextColor;

    private int mUserId = -1;

    private int mAttentionCode = 0;//关注状态码
    private FindInfoListAdapter mFindInfoListAdapter;
    private int mRecordPosition = -1;
    private UserHomePageDialog mUserHomePageDialog;
    private int mDynamicIdByIndex;

    public static UserHomePageFrag newInstance(int userId) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_USER_ID, userId);
        UserHomePageFrag fragment = new UserHomePageFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUserId = arguments.getInt(EXTRA_USER_ID);
        }
    }

    @Override
    public UserHomePagePre createPresenter() {
        return new UserHomePagePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_user_info_home_page;
    }

    @Override
    public void initView() {
        super.initView();
        mAppBarLayout = findViewById(R.id.app_bar_layout_user_home_page);
        mLlTopUserInfo = findViewById(R.id.ll_user_home_page_top_user_info);
        mIvUserBackground = findViewById(R.id.iv_user_home_page_background);
        mIvAvatarSmall = findViewById(R.id.iv_user_home_page_avatar_small);
        mTvUserNameSmall = findViewById(R.id.tv_user_home_page_user_name_small);
        mIvAvatarLarge = findViewById(R.id.iv_user_home_page_avatar_large);
        mTvUserNameLarge = findViewById(R.id.tv_user_home_page_user_name_large);
        mTvUserInfoAction = findViewById(R.id.tv_user_home_page_action);
        mTvUserDescription = findViewById(R.id.tv_user_home_page_description);
        mTvPraiseCount = findViewById(R.id.tv_user_home_page_praise_count);
        mTvMyAttentionCount = findViewById(R.id.tv_user_home_page_my_attention_count);
        mTvAttentionToMyCount = findViewById(R.id.tv_user_home_page_attention_to_my_count);
        mTvDynamicCount = findViewById(R.id.tv_user_home_page_dynamic_count);
        mTvPraiseTitle = findViewById(R.id.tv_user_home_page_praise_title);
        mTvMyAttentionTitle = findViewById(R.id.tv_user_home_page_my_attention_title);
        mTvAttentionToMyTitle = findViewById(R.id.tv_user_home_page_attention_to_my_title);
        mTvDynamicTitle = findViewById(R.id.tv_user_home_page_dynamic_title);
        mViewElevation = findViewById(R.id.view_elevation);
        mTvDynamicHeader = findViewById(R.id.tv_user_home_page_dynamic_header);
        mLlUserMyAttention = findViewById(R.id.ll_user_home_page_attention);
        mLlUserAttentionToMy = findViewById(R.id.ll_user_home_page_attention_to_my);
        mViewTransparent = findViewById(R.id.view_transparent);
        mLlEmptyView = findViewById(R.id.ll_load_empty_view);
        mTvEmptyHint = findViewById(R.id.tv_empty_hint);

        mAppBarLayout.addOnOffsetChangedListener(this);
        mTvUserInfoAction.setOnClickListener(this);
        mLlUserMyAttention.setOnClickListener(this);
        mLlUserAttentionToMy.setOnClickListener(this);

        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(false).init();

        initResource();

        initRecyclerView();

        if (checkIsOwn()) {
            mViewTransparent.setOnClickListener(this);
        }
    }

    private void initResource() {
        if (isAdded()) {
            mElevationHeight = mFragmentActivity.getResources().getDimensionPixelOffset(R.dimen.rect_5);
            mDefDrawable = ContextCompat.getDrawable(mFragmentActivity, R.drawable.shape_dialog_white_bg);
            mWhiteColor = ContextCompat.getColor(mFragmentActivity, android.R.color.white);
            mTextViewActionHeight = mFragmentActivity.getResources().getDimensionPixelOffset(R.dimen.text_view_height);
        }
    }

    private void initRecyclerView() {
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
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {
            if (mLlTopUserInfo.getVisibility() == View.VISIBLE) {
                mLlTopUserInfo.setVisibility(View.GONE);
                mAppToolbar.setNavigationIcon(R.drawable.ic_back_white);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppBarLayout.setElevation(0);
                }
                mViewElevation.setBackground(mDefDrawable);
                ImmersionBar.with(UserHomePageFrag.this).statusBarDarkFont(false).init();
            }

        } else {

            if (offset == scrollRange) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppBarLayout.setElevation(mElevationHeight);
                }
                mViewElevation.setBackgroundColor(mWhiteColor);
            }
            if (mLlTopUserInfo.getVisibility() == View.GONE) {
                mLlTopUserInfo.setVisibility(View.VISIBLE);
                mAppToolbar.setNavigationIcon(R.drawable.ic_back);
                ImmersionBar.with(UserHomePageFrag.this).statusBarDarkFont(true,0.2f).init();

            }
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getUserInfo(mUserId, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getLoadMoreDynamicList(mUserId, mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onUserDetailsResult(UserDetailsBean resultInfo) {
        BindVal(resultInfo);
    }

    @Override
    public void onFindListResult(List<FindInfoBean> findList) {
        if (findList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mLlEmptyView.setVisibility(View.VISIBLE);
            if (checkIsOwn()) {
                mTvEmptyHint.setText("你还没有发布任何动态～");
            } else {
                mTvEmptyHint.setText("TA还没有发布任何动态～");
            }
        } else {
            if (mLlEmptyView.getVisibility() == View.VISIBLE) {
                mLlEmptyView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
            mFindInfoListAdapter.setFindInfoList(findList);
        }
    }

    @Override
    public void onNotifyListChangeResult(String result, int position) {

        ToastUtils.showToast(mFragmentActivity, result);

        if (position != UserHomePagePre.RESULT_POSITION) {//点赞后进入
            mFindInfoListAdapter.notifyItemChanged(position);
        } else {//修改关注状态后进入
            //重新设置关注状态
            mAttentionCode = mAttentionCode == 1 ? 0 : 1;
            setAttentionVal();
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
    public void onNotifyBackgroundImg(AvatarResult result) {
        GlideApp.with(this)
                .load(result.getImgPath())
                .thumbnail(GlideApp.with(this).load(result.getZipFileName()).centerCrop())
                .centerCrop()
                .into(mIvUserBackground);
    }

    private void BindVal(UserDetailsBean userInfo) {

        UserInfoEntity user = userInfo.getUser();

        String avatarPath = user.getUserCompressionHead();
        String nickName = user.getUserNickName();
        String description = user.getUserBrief();
        mAttentionCode = userInfo.getIsFocus();

        GlideApp.with(this)
                .load(user.getUserBackImg())
                .centerCrop()
                .into(mIvUserBackground);


        GlideApp.with(UserHomePageFrag.this)
                .load(avatarPath)
                .error(R.drawable.ic_default_avatar)
                .centerCrop()
                .transform(new CircleCrop())
                .into(mIvAvatarLarge);
        GlideApp.with(UserHomePageFrag.this)
                .load(avatarPath)
                .error(R.drawable.ic_default_avatar)
                .centerCrop()
                .transform(new CircleCrop())
                .into(mIvAvatarSmall);

        mIvAvatarLarge.setVisibility(View.VISIBLE);
        mViewTransparent.setVisibility(View.VISIBLE);

        mTvUserNameLarge.setText(nickName);
        mTvUserNameSmall.setText(nickName);
        initBtnActionVal();
        mTvUserDescription.setText(description == null ? "这个人很懒，什么都没有写~" : description);
        initNavVal(userInfo);
    }

    private void initBtnActionVal() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mTvUserInfoAction.getLayoutParams();
        if (mDrawableBtnAction == null) {
            if (checkIsOwn()) {//证明是查看自己，
                mDrawableBtnAction = ContextCompat.getDrawable(mFragmentActivity, R.drawable.shape_btn_edit_info);
                layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = mTextViewActionHeight;
                mTextViewActionTextColor = ContextCompat.getColor(mFragmentActivity, android.R.color.white);
            } else {//查看他人的详情
                mDrawableBtnAction = ContextCompat.getDrawable(mFragmentActivity, R.drawable.attention_selector);
                layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
                mTextViewActionTextColor = ContextCompat.getColor(mFragmentActivity, R.color.attention_txt_color);
            }
            mTvUserInfoAction.setTextColor(mTextViewActionTextColor);
            mTvUserInfoAction.setLayoutParams(layoutParams);
            mTvUserInfoAction.setBackground(mDrawableBtnAction);
            mTvUserInfoAction.setVisibility(View.VISIBLE);
        }
        if (!checkIsOwn()) {
            setAttentionVal();
        }
    }

    /**
     * 设置关注状态
     */
    private void setAttentionVal() {
        mTvUserInfoAction.setSelected(mAttentionCode == 1);
        mTvUserInfoAction.setText(mAttentionCode == 1 ? "已关注" : "+ 关注");
    }

    private void initNavVal(UserDetailsBean details) {
        mTvPraiseTitle.setText("获得的赞");
        mTvMyAttentionTitle.setText(checkIsOwn() ? "我关注的人" : "TA关注的人");
        mTvAttentionToMyTitle.setText(checkIsOwn() ? "关注我的人" : "关注TA的人");
        mTvDynamicTitle.setText(checkIsOwn() ? "我的动态" : "TA的动态");
        mTvDynamicHeader.setText(mTvDynamicTitle.getText());

        mTvPraiseCount.setText(String.valueOf(details.getUserLikeCount()));
        mTvMyAttentionCount.setText(String.valueOf(details.getUserFocusCount()));
        mTvAttentionToMyCount.setText(String.valueOf(details.getUserFansCount()));
        mTvDynamicCount.setText(String.valueOf(details.getUserDynamicCount()));

    }

    private final boolean checkIsOwn() {
        return mUserId == -1;
    }

    @Override
    public void onItemClick(int position, FindInfoBean entity, View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_item_find_praise:
                getPresenter().setLike(entity.getDynamicId(), position);
                break;
            case R.id.ll_item_find_comment:
            case R.id.tv_item_find_txt:
                mRecordPosition = position;
                GSYVideoManager.releaseAllVideos();
                intent = new Intent(mFragmentActivity, FindDetailsAct.class);
                intent.putExtra(FindDetailsFrag.FIND_ID, entity.getDynamicId());
                startActivityForResult(intent, REQUEST_COMMENT_CODE);
                break;
            case R.id.ll_item_find_share:
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(this);
                }
                mRecordPosition = position;
                mDynamicIdByIndex = entity.getDynamicId();
                getPresenter().startShareFind(this, entity);
                break;
            case R.id.tv_item_find_attention:
                getPresenter().setAttention(entity.getUserId());
                break;
            case R.id.iv_item_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, entity.getDynamicPic(), position);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit_user_info:
            case R.id.tv_user_home_page_action:
                if (checkIsOwn()) {
                    //edit user info
//                    UserInfoEditAct.startUserInfoEditAct(mFragmentActivity);
                    Intent intent = new Intent(mFragmentActivity, UserInfoEditAct.class);
                    startActivityForResult(intent, REQUEST_USER_EDIT);
                } else {
                    //set attention status
                    getPresenter().setAttention(mUserId);
                }
                break;
            case R.id.ll_user_home_page_attention:
                break;
            case R.id.ll_user_home_page_attention_to_my:
                break;
            case R.id.view_transparent:
                if (mUserHomePageDialog == null) {
                    mUserHomePageDialog = new UserHomePageDialog();
                    mUserHomePageDialog.setOnClickListener(this);
                }
                mUserHomePageDialog.show(getChildFragmentManager(), mUserHomePageDialog.getClass().getName());
                break;
            case R.id.tv_edit_picture:
                UserHomePageFragPermissionsDispatcher.NeedsPermissionWithPermissionCheck(this);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_COMMENT_CODE && resultCode == RESULT_OK && data != null) {
            ModifyRecordBean record = data.getParcelableExtra(FindDetailsFrag.EXTRA_MODIFY_RESULT);
            getPresenter().modifyRecord(record, mRecordPosition);
        }
        if (requestCode == REQUEST_PICTURE_CODE && resultCode == RESULT_OK) {
            UCrop.Options options = new UCrop.Options();

            options.setStatusBarColor(ContextCompat.getColor(mFragmentActivity, android.R.color.black));
            options.setToolbarColor(ContextCompat.getColor(mFragmentActivity, android.R.color.black));
            options.setShowCropFrame(true);

            options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
            UCrop.of(AlbumManager.obtainResult(data).get(0), Uri.fromFile(new File(getDiskCacheDir(mFragmentActivity),
                    System.currentTimeMillis() + ".jpg")))
                    .withOptions(options)
                    .withAspectRatio(3, 2)
                    .start(mFragmentActivity, UserHomePageFrag.this);
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            String path = resultUri.getPath();
            getPresenter().updatePicture(path);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();
        }
        if (requestCode == REQUEST_USER_EDIT) {
            getPresenter().getUserInfo(mUserId);
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

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void NeedsPermission() {
        getPresenter().startSelectPicture(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserHomePageFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_camera_rationale, request);
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


}
