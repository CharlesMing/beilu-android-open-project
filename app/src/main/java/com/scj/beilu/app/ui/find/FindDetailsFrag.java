package com.scj.beilu.app.ui.find;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.kpswitch.util.KeyboardUtil;
import com.mx.pro.lib.kpswitch.widget.KPSwitchPanelLinearLayout;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.FindDetailsPre;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsViewBean;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.ui.preview.PicImagePreviewAct;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.ui.find.adapter.FindDetailsImagePagerAdapter;
import com.scj.beilu.app.ui.find.adapter.FindDetailsParentCommentAdapter;
import com.scj.beilu.app.ui.find.adapter.FindInfoListAdapter;
import com.scj.beilu.app.ui.find.adapter.FindLikeAvatarAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.AppToolbar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/22 21:03
 * 动态详情
 */
public class FindDetailsFrag extends BaseMvpFragment<FindDetailsPre.FindDetailsView, FindDetailsPre>
        implements FindDetailsPre.FindDetailsView, OnItemClickListener, View.OnClickListener {

    public static final String FIND_ID = "find_id";
    public static final String EXTRA_MODIFY_RESULT = "modify_result";
    private static final int REQUEST_CODE = 0x0901;

    private AppToolbar mDefAppToolbar;
    private NestedScrollView mNestedScrollView;
    private StandardGSYVideoPlayer mCoverVideo;
    private FrameLayout mFlImageContent;
    private ViewPager mViewImagePage;
    private TextView mTvImageNum;
    private LinearLayout mLlLikeContent;
    private TextView mTvLikeCount;
    private RecyclerView mRvLikeAvatarList;
    private ImageView mIvUserAvatar;
    private TextView mTvUserName;
    private TextView mTvAttention;
    private TextView mTvDetailsContent;
    private TextView mTvCommentCount;
    private ImageView mIvVideoThumbnail;
    private RecyclerView mRvCommentList;
    private KPSwitchPanelLinearLayout mPanelRoot;
    private EditText mEtComment;
    private TextView mTvCommentDetails;
    private Button mBtSendComment;
    private LinearLayout mLlLoadEmpty;

    //视频播放器的构造器
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;
    //初始化图片加载器
    private GlideRequest<Drawable> mOriginalGlideRequest;
    private GlideRequest<Drawable> mThumbnailGlideRequests;
    //多图数组
    private List<FindImageBean> mFindImageList;
    //初始化多图加载适配器
    private FindDetailsImagePagerAdapter mImagePagerAdapter;
    //初始化点赞的成员头像适配器
    private FindLikeAvatarAdapter mFindLikeAvatarAdapter;
    //一级评论适配器
    private FindDetailsParentCommentAdapter mCommentAdapter;

    // 获取到点赞的View位置，将Y轴位置保存
    // 用户滑动到当前Y值，改变状态栏颜色为黑色
    private int mRvLikeLocationY = 0;
    // 获取到用于显示评论数量的View位置,用户评论成功后
    // 自动滑动到该位置，用于显示评论列表
    private int mTvCommentCountLocationY = 0;
    //是否修改状态栏颜色为白色 默认true
    private boolean isChangeWhite = true;
    //是否修改状态栏颜色为黑色 默认false
    private boolean isChangeBlack = false;

    private int mFindId;
    private FindCommentBean mToReplyComment;
    // 请求类型码用于更新列表
    // 0 创建评论 1回复评论
    private int mRequestCode = 0;
    //点击一级评论获取下标
    private int mPosition = -1;
    //评论总数量 默认0
    private int mCommentCount = -1;
    private int mFocusUserId = -1;
    private int mLikeCount = -1;
    //是否开启滑动改变状态栏的字体颜色
    private boolean StatusBarSwitch = true;
    private FindInfoBean mFindInfo;

    public static FindDetailsFrag newInstance(int findId) {

        Bundle args = new Bundle();
        args.putInt(FIND_ID, findId);
        FindDetailsFrag fragment = new FindDetailsFrag();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mFindId = arguments.getInt(FIND_ID);
        }
    }

    @Override
    public FindDetailsPre createPresenter() {
        return new FindDetailsPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_find_details;
    }

    @Override
    public void initView() {
        super.initView();
//        ImmersionBar.with(this).statusBarView(R.id.top_view)
//                .addTag("PicAndColor")
//                .keyboardEnable(true)
//                .init();
//        ImmersionBar.with(FindDetailsFrag.this)
//                .statusBarView(R.id.top_view)
//                .statusBarDarkFont(true,0.2f)
//                .init();

        mOriginalGlideRequest = GlideApp.with(this).asDrawable();
        mThumbnailGlideRequests = GlideApp.with(this).asDrawable();

        mDefAppToolbar = findViewById(R.id.def_toolbar);
        mLlLikeContent = findViewById(R.id.ll_like_content);
        mNestedScrollView = findViewById(R.id.nested_scroll_view);
        mCoverVideo = findViewById(R.id.video_item_player);
        mViewImagePage = findViewById(R.id.view_img);
        mFlImageContent = findViewById(R.id.fl_image_content);
        mTvImageNum = findViewById(R.id.tv_img_num);
        mTvLikeCount = findViewById(R.id.tv_find_details_like_count);
        mRvLikeAvatarList = findViewById(R.id.rv_find_details_like_avatar_list);
        mIvUserAvatar = findViewById(R.id.iv_find_details_avatar);
        mTvUserName = findViewById(R.id.tv_find_details_user_name);
        mTvAttention = findViewById(R.id.tv_find_details_attention);
        mTvDetailsContent = findViewById(R.id.tv_find_details_content);
        mTvCommentCount = findViewById(R.id.tv_find_comment_count);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        mIvVideoThumbnail = new ImageView(mFragmentActivity);
        mRvCommentList = findViewById(R.id.rv_comment_list);
        mEtComment = findViewById(R.id.et_comment_content);
        mTvCommentDetails = findViewById(R.id.tv_comment_detail);
        mBtSendComment = findViewById(R.id.btn_send_comment);
        mPanelRoot = findViewById(R.id.panel_root);
        mLlLoadEmpty = findViewById(R.id.ll_load_empty_view);

        KeyboardUtil.attach(mFragmentActivity, mPanelRoot);

        mRvLikeAvatarList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5, 0, 5, 0);
            }
        });
        mRvCommentList.addItemDecoration(new DividerItemDecoration(mRvCommentList.getContext(), DividerItemDecoration.VERTICAL));

        mImagePagerAdapter = new FindDetailsImagePagerAdapter(mOriginalGlideRequest, mThumbnailGlideRequests);
        mImagePagerAdapter.setItemClickListener(this);
        mViewImagePage.setAdapter(mImagePagerAdapter);


        mViewImagePage.addOnPageChangeListener(mOnPageChangeListener);

        mCommentAdapter = new FindDetailsParentCommentAdapter(GlideApp.with(this).asDrawable().centerCrop());
        mCommentAdapter.setItemClickListener(this);
        mRvCommentList.setAdapter(mCommentAdapter);

        mRefreshLayout.autoRefresh();

        mBtSendComment.setOnClickListener(this);
        mTvAttention.setOnClickListener(this);
        mTvLikeCount.setOnClickListener(this);
        mTvCommentDetails.setOnClickListener(this);
        mIvUserAvatar.setOnClickListener(this);
        mEtComment.addTextChangedListener(mTextWatcher);
        mAppToolbar.setNavigationOnClickListener(mNavClick);
        mDefAppToolbar.setNavigationOnClickListener(mNavClick);
        mAppToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);
        mDefAppToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);

        mNestedScrollView.setOnScrollChangeListener(mScrollChangeListener);

    }

    private final View.OnClickListener mNavClick = v -> {
        setUpdateResult();
        mFragmentActivity.finish();
    };

    private final Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.menu_share) {
                if (!EventBus.getDefault().isRegistered(FindDetailsFrag.this)) {
                    EventBus.getDefault().register(FindDetailsFrag.this);
                }
                getPresenter().startShareFind(FindDetailsFrag.this, mFindInfo);
            }
            return false;
        }
    };

    /**
     * 滑动监听
     */
    private final NestedScrollView.OnScrollChangeListener mScrollChangeListener = (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
        if (StatusBarSwitch) {
            if (mRvLikeLocationY <= scrollY) {
                if (isChangeWhite) {
                    mFragmentActivity.runOnUiThread(() -> {
                        ImmersionBar.with(FindDetailsFrag.this)
                                .statusBarDarkFont(true, 0.2f)
                                .init();
                        isChangeBlack = true;
                        isChangeWhite = false;
                    });

                }
            } else {
                if (isChangeBlack) {
                    mFragmentActivity.runOnUiThread(() -> {
                        ImmersionBar.with(FindDetailsFrag.this).statusBarDarkFont(false)
                                .init();
                        isChangeBlack = false;
                        isChangeWhite = true;
                    });


                }
            }
        }

    };

    /**
     * ViewPager滑动
     */
    private final ViewPager.OnPageChangeListener mOnPageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (mFindImageList != null) {
                        position = position + 1;
                        String num = position + "/" + mFindImageList.size();
                        mTvImageNum.setText(num);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                mTvCommentDetails.setVisibility(View.GONE);
                mBtSendComment.setVisibility(View.VISIBLE);
            } else {
                mBtSendComment.setVisibility(View.GONE);
                mTvCommentDetails.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getDetails(mFindId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getCommentLit(mCurrentPage);
    }

    @Override
    public void onDetailsResult(FindDetailsViewBean findDetailsInfo, List<FindCommentBean> commentList) {
        mRefreshLayout.finishRefresh(true);

        FindDetailsBean detailsBean = findDetailsInfo.getFindDetailsBean();
        bindDate(detailsBean);

        if (commentList.size() == 0) {
            mLlLoadEmpty.setVisibility(View.VISIBLE);
            mRvCommentList.setVisibility(View.GONE);
        } else {
            mRvCommentList.setVisibility(View.VISIBLE);
            mLlLoadEmpty.setVisibility(View.GONE);
            mCommentAdapter.setFindCommentBeans(commentList);
        }

    }

    /**
     * 绑定数据
     */
    private void bindDate(@NonNull FindDetailsBean details) {
        if (details == null) return;
        //动态详情的内容
        mFindInfo = details.getDynamic();
        if (mFindInfo == null) return;
        try {
            //图片数组
            final List<FindImageBean> dynamicPicList = mFindInfo.getDynamicPic();
            if (dynamicPicList != null && dynamicPicList.size() != 0) {
                //没有视频并且有图片 直接隐藏相关View
                mCoverVideo.setVisibility(View.GONE);
                mFlImageContent.setVisibility(View.VISIBLE);
                mAppToolbar.setVisibility(View.VISIBLE);
                bindImage(dynamicPicList);

            } else if (mFindInfo.getDynamicVideoAddr() != null) {
                //视频的地址存在时，显示视频的View
                mFlImageContent.setVisibility(View.GONE);
                mCoverVideo.setVisibility(View.VISIBLE);
                mAppToolbar.setVisibility(View.VISIBLE);
                bindVideo();
            } else {
                ImmersionBar.with(this).reset();
                mDefAppToolbar.setVisibility(View.VISIBLE);
                mAppToolbar.setVisibility(View.GONE);
                //默认隐藏所有顶部（图片、视频）的View
                mCoverVideo.setVisibility(View.GONE);
                mFlImageContent.setVisibility(View.GONE);
                StatusBarSwitch = false;
                ImmersionBar.with(this)
                        .statusBarDarkFont(true, 0.2f)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        mTvAttention.setVisibility(mFindInfo.getIsOwn() == 0 ? View.VISIBLE : View.GONE);
        mTvAttention.setSelected(mFindInfo.getIsFocus() == 1);
        mTvAttention.setText(mFindInfo.getIsFocus() == 1 ? "已关注" : "+ 关注");
        mTvLikeCount.setText(String.valueOf(mFindInfo.getDynamicLikeCount()));
        mTvUserName.setText(mFindInfo.getUserNickName());
        mCommentCount = mFindInfo.getCommentCount();
        setCommentCount();
        if (mFindInfo.getDynamicDec() == null) {
            mTvDetailsContent.setVisibility(View.GONE);
        } else {
            mTvDetailsContent.setVisibility(View.VISIBLE);
            mTvDetailsContent.setText(mFindInfo.getDynamicDec());
        }

        //关注的人
        mOriginalGlideRequest
                .load(mFindInfo.getUserHead())
                .transform(new CircleCrop())
                .into(mIvUserAvatar);
        mFindLikeAvatarAdapter = new FindLikeAvatarAdapter(details.getLikeNumbers(), GlideApp.with(this));
        mRvLikeAvatarList.setAdapter(mFindLikeAvatarAdapter);

        mRvLikeAvatarList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvLikeAvatarList.getLayoutManager();
        layoutManager.setReverseLayout(true);


        mTvCommentCount.post(() -> {

            int[] location = new int[2];
            // 获取在当前窗口内的绝对坐标，含toolBar
            // mTvCommentCount.getLocationInWindow(location);
            // 获取在整个屏幕内的绝对坐标，含statusBar
            // mTvCommentCount.getLocationOnScreen(location);
            mTvCommentCount.getLocationOnScreen(location);
            mTvCommentCountLocationY = location[1] - mTvCommentCount.getHeight();

        });
        mLlLikeContent.post(() -> {
            int[] location = new int[2];
            mLlLikeContent.getLocationInWindow(location);
            mRvLikeLocationY = location[1];
        });

    }

    private void setCommentCount() {
        mTvCommentCount.setText(mCommentCount + "条");
        mTvCommentDetails.setText(String.valueOf(mCommentCount));
    }

    /**
     * 图片
     */
    private void bindImage(final List<FindImageBean> dynamicPicList) {
        mFindImageList = dynamicPicList;
        String num = "1/" + mFindImageList.size();
        mTvImageNum.setText(num);
        mImagePagerAdapter.setFindImageBeanList(mFindImageList);
    }

    /**
     * 视频
     */
    private void bindVideo() {
        mOriginalGlideRequest
                .load(mFindInfo.getDynamicVideoPic())
                .centerCrop()
                .into(mIvVideoThumbnail);
        if (mIvVideoThumbnail.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) mIvVideoThumbnail.getParent();
            viewGroup.removeView(mIvVideoThumbnail);
        }
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(mIvVideoThumbnail)
                .setUrl(mFindInfo.getDynamicVideoAddr())
                .setVideoTitle(mFindInfo.getDynamicDec() == null ? "" : mFindInfo.getDynamicDec())
                .setCacheWithPlay(false)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setPlayTag(FindInfoListAdapter.TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
//                .setPlayPosition(getAdapterPosition())
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!mCoverVideo.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(false);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        mCoverVideo.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                })
                .build(mCoverVideo);

        //增加title
        mCoverVideo.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        mCoverVideo.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        mCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(mCoverVideo);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(@NonNull final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(mFragmentActivity, true, true);
    }

    @Override
    public void onCommentListResult(List<FindCommentBean> commentList) {

        if (commentList.size() == 0) {
            mLlLoadEmpty.setVisibility(View.VISIBLE);
            mRvCommentList.setVisibility(View.GONE);
            mRefreshLayout.setEnableLoadMore(false);
        } else {
            mRvCommentList.setVisibility(View.VISIBLE);
            mLlLoadEmpty.setVisibility(View.GONE);
            mCommentAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onUpdateAttentionResult(FindDetailsBean result) {
        FindInfoBean dynamic = result.getDynamic();
        mFocusUserId = dynamic.getUserId();
        mTvAttention.setSelected(dynamic.getIsFocus() == 1);
        mTvAttention.setText(dynamic.getIsFocus() == 1 ? "已关注" : "+ 关注");
        ToastUtils.showToast(mFragmentActivity, result.getResult());
    }

    @Override
    public void onUpdateLikeResult(FindDetailsBean result) {
        FindInfoBean dynamic = result.getDynamic();
        mLikeCount = dynamic.getDynamicLikeCount();
        mTvLikeCount.setText(String.valueOf(mLikeCount));
        mFindLikeAvatarAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCommentResult(String result, List<FindCommentBean> commentList) {
        if (mRequestCode == 0) {//更新一级评论列表
            mCommentCount++;
            setCommentCount();
            mNestedScrollView.fling(mTvCommentCountLocationY);
            mNestedScrollView.smoothScrollTo(0, mTvCommentCountLocationY);
            mCommentAdapter.notifyItemInserted(0);

            if (mRvCommentList.getVisibility() == View.GONE) {
                mCommentAdapter.setFindCommentBeans(commentList);
                mRvCommentList.setVisibility(View.VISIBLE);
                mLlLoadEmpty.setVisibility(View.GONE);
            }
        } else {
            mCommentAdapter.notifyItemChanged(mPosition);
            mPosition = -1;
        }


        if (mToReplyComment != null) {
            mToReplyComment = null;
        }

        ToastUtils.showToast(mFragmentActivity, result);
        mEtComment.setHint("说点什么呗~");
        mEtComment.setText("");
        //隐藏软键盘
        hideSoftInput();
    }

    @Override
    public void showEditTextHint(ResultMsgBean result) {
        mRequestCode = result.getCode();
        mEtComment.setHint(result.getResult());
        showSoftInput(mEtComment);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
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
        } else {
            setUpdateResult();
            return false;
        }
    }


    @Override
    public void onItemClick(int position, Object entity, View view) {
        switch (view.getId()) {
            case R.id.imageView://查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, mFindInfo.getDynamicPic(), position);
                break;
            case R.id.tv_news_comment_content://点击评论内容显示对应回复的人
                mToReplyComment = (FindCommentBean) entity;
                if (mToReplyComment == null) return;
                mPosition = position;
                getPresenter().checkIsOwn(mToReplyComment);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_comment://评论-发布/回复
                String content = mEtComment.getText().toString();
                getPresenter().createComment(mToReplyComment, content, mPosition);
                break;
            case R.id.tv_find_details_like_count://设置点赞
                getPresenter().setLike();
                break;
            case R.id.tv_find_details_attention://设置关注
                getPresenter().setAttention();
                break;
            case R.id.tv_comment_detail://点击自动滑到评论区
                mNestedScrollView.fling(mTvCommentCountLocationY);
                mNestedScrollView.smoothScrollTo(0, mTvCommentCountLocationY);
                break;
            case R.id.iv_find_details_avatar:
                Intent intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                int userId = mFindInfo.getIsOwn() == 0 ? mFindInfo.getUserId() : -1;
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, userId);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    private void setUpdateResult() {
        ModifyRecordBean record = new ModifyRecordBean(mLikeCount, mCommentCount, mFocusUserId);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MODIFY_RESULT, record);
        mFragmentActivity.setResult(RESULT_OK, intent);
    }

    @Subscribe
    public void onShareResult(BaseResp resp) {
        if (mFindInfo == null) return;
        getPresenter().setFindShareCount(mFindInfo.getDynamicId());
    }

    @Override
    public void onFindDel() {
        if (mFindInfo == null) return;
        getPresenter().delFind(mFindInfo.getDynamicId());
    }

    @Override
    public void onShareCountResult() {
        if (EventBus.getDefault().isRegistered(FindDetailsFrag.this)) {
            EventBus.getDefault().unregister(FindDetailsFrag.this);
        }
    }

    @Override
    public void onFindDelResult(ResultMsgBean result) {
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        mFragmentActivity.setResult(Activity.RESULT_OK);
        mFragmentActivity.finish();

    }
}
