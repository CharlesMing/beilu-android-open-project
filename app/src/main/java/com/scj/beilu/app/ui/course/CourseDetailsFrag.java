package com.scj.beilu.app.ui.course;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.course.CourseDetailsPre;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoListBean;
import com.scj.beilu.app.ui.comment.SelectPaymentDialog;
import com.scj.beilu.app.ui.course.adapter.DescriptionFragAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mingxun
 * @time on 2019/3/20 19:41
 */
@RuntimePermissions
public class CourseDetailsFrag extends BaseMvpFragment<CourseDetailsPre.CourseDetailsView, CourseDetailsPre>
        implements CourseDetailsPre.CourseDetailsView, TabLayout.OnTabSelectedListener,
        AppBarLayout.OnOffsetChangedListener, View.OnClickListener, TextWatcher,
        SelectPaymentDialog.onStartPay {

    private AppBarLayout mAppBarLayout;
    private TabLayout mTabCourseTitle;
    private ViewPager mViewPagerContent;
    private LinearLayout mLlStartPlay, mLlFreePlay, mLlBuy, mLlSingle;
    private ConstraintLayout mClComment;
    private TextView mTvStartPlay;
    private ImageView mIvBackground;
    private TextView mTvCourseName, mTvCourseSmallTitle;
    private TextView mTvTotalPrice, mTvDescription, mTvUnitPrice, mTvBuySingle, mTvCourseCount, mTvBuyCount;
    private TextView mTvFreePlay;
    private FrameLayout mFlConsult;
    private TextView mTvStartBuyAll;
    private EditText mEtCommentContent;
    private Button mBtnComment;

    private final String[] mTabTitles = {"老师简介", "课程简介", "课程目录", "评论"};

    private DescriptionFragAdapter mDescriptionFragAdapter;

    private final static String COURSE_ID = "course_id";
    private int mCourseId;

    //是否修改状态栏颜色为白色 默认true
    private boolean isChangeWhite = true;
    //是否修改状态栏颜色为黑色 默认false
    private boolean isChangeBlack = false;
    private String mRMB;
    private String phoneNum;
    private String mTotalPrice;

    private CourseDetailsDescFrag mCourseDetailsDescFrag;
    private CourseDetailsCatalogueFrag mCourseDetailsCatalogueFrag;
    private CourseDetailsCommentFrag mCourseDetailsCommentFrag;

    private List<CourseVideoInfoBean> mCourseVideoInfoBeans;
    private SelectPaymentDialog mSelectPaymentDialog;

    public static CourseDetailsFrag newInstance(int courseId) {

        Bundle args = new Bundle();
        args.putInt(COURSE_ID, courseId);
        CourseDetailsFrag fragment = new CourseDetailsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCourseId = arguments.getInt(COURSE_ID);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public CourseDetailsPre createPresenter() {
        return new CourseDetailsPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_details;
    }

    @Override
    public void initView() {
        super.initView();

        mIvBackground = findViewById(R.id.iv_course_background_img);

        mTvStartPlay = findViewById(R.id.tv_top_start_play);
        mAppBarLayout = findViewById(R.id.app_bar_layout_course);
        mTabCourseTitle = findViewById(R.id.tab_layout_course);
        mTvCourseName = findViewById(R.id.tv_course_name);
        mTvCourseSmallTitle = findViewById(R.id.tv_course_small_title);
        mTvTotalPrice = findViewById(R.id.tv_course_total_price);
        mTvDescription = findViewById(R.id.tv_course_desc);
        mTvUnitPrice = findViewById(R.id.tv_course_unit_price);
        mTvBuySingle = findViewById(R.id.tv_course_buy_single);
        mTvCourseCount = findViewById(R.id.tv_course_count);
        mTvBuyCount = findViewById(R.id.tv_course_buy_num);
        mViewPagerContent = findViewById(R.id.view_pager_course_description);
        mLlStartPlay = findViewById(R.id.ll_course_start_play);
        mLlFreePlay = findViewById(R.id.ll_course_free_play_video);
        mClComment = findViewById(R.id.cl_comment_layout);
        mLlBuy = findViewById(R.id.ll_course_buy);
        mLlSingle = findViewById(R.id.ll_course_single);
        mTvFreePlay = findViewById(R.id.tv_course_free_play);
        mFlConsult = findViewById(R.id.fl_course_consult);
        mTvStartBuyAll = findViewById(R.id.tv_course_buy_all);
        mEtCommentContent = findViewById(R.id.et_comment_content);
        mBtnComment = findViewById(R.id.btn_send_comment);

        initTabs();

        mAppBarLayout.addOnOffsetChangedListener(this);
        mTvStartPlay.setOnClickListener(this);
        mLlFreePlay.setOnClickListener(this);
        mTvBuySingle.setOnClickListener(this);
        mFlConsult.setOnClickListener(this);
        mTvStartBuyAll.setOnClickListener(this);
        mEtCommentContent.addTextChangedListener(this);
        mBtnComment.setOnClickListener(this);
    }

    private void initTabs() {
        if (isAdded()) {
            mRMB = getResources().getString(R.string.txt_rmb);
        }

        mTabCourseTitle.setupWithViewPager(mViewPagerContent);


        mCourseDetailsDescFrag = new CourseDetailsDescFrag();
        mCourseDetailsCatalogueFrag = new CourseDetailsCatalogueFrag();
        mCourseDetailsCommentFrag = CourseDetailsCommentFrag.newInstance(mCourseId);

        List<SupportFragment> fragments = new ArrayList<>();
        fragments.add(CourseDetailsTeacherDescFrag.newInstance(mCourseId));
        fragments.add(mCourseDetailsDescFrag);
        fragments.add(mCourseDetailsCatalogueFrag);
        fragments.add(mCourseDetailsCommentFrag);

        mTabCourseTitle.addOnTabSelectedListener(this);

        mDescriptionFragAdapter = new DescriptionFragAdapter(getChildFragmentManager());
        mDescriptionFragAdapter.setFragments(fragments);
        mViewPagerContent.setAdapter(mDescriptionFragAdapter);

        int count = mTabCourseTitle.getTabCount();
        for (int i = 0; i < count; i++) {
            mTabCourseTitle.getTabAt(i).setCustomView(getTabLayout(mTabTitles[i]));
        }

        setTabSelector(mTabCourseTitle.getTabAt(0), true);
        mViewPagerContent.setOffscreenPageLimit(count);


        LinearLayout linearLayout = (LinearLayout) mTabCourseTitle.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(60); // 设置分割线的pandding
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.sp_exposure_select));
    }

    private View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mFragmentActivity).inflate(R.layout.item_layout_tab_course, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item_title);
        tvTab.setText(title);
        return view;
    }

    private void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) return;
        TextView tvTab = view.findViewById(R.id.tv_tab_item_title);
        View indicator = view.findViewById(R.id.tv_tab_item_indicator);

        tvTab.setSelected(selected);
        indicator.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getCourseDetails(mCourseId);
        getPresenter().getCourseVideoList(mCourseId);
    }

    @Override
    public void onCourseInfoResult(CourseInfoBean courseInfo) {
        try {


            mCourseDetailsDescFrag.setCourseInfo(courseInfo);

            GlideApp.with(this)
                    .load(courseInfo.getCourseBackPic())
                    .centerCrop()
                    .thumbnail(GlideApp.with(this)
                            .load(courseInfo.getCourseBackPicZip())
                            .centerCrop())
                    .into(mIvBackground);
            mTotalPrice = courseInfo.getCourseTotalPrice();
            mTvCourseName.setText(courseInfo.getCourseName());
            mTvCourseSmallTitle.setText(courseInfo.getCourseTitle());
            mTvTotalPrice.setText(String.format(mRMB, mTotalPrice));
            mTvDescription.setText(courseInfo.getCourseBrief());
            if (courseInfo.getCourseSinglePrice() != null) {
                mTvUnitPrice.setText(String.format(mRMB, courseInfo.getCourseSinglePrice()));
                mTvFreePlay.setText("首节免费");
                mLlSingle.setVisibility(View.VISIBLE);
            } else {
                mTvFreePlay.setText("全部免费");
                mLlSingle.setVisibility(View.GONE);
            }
            mTvCourseCount.setText("已更新：" + courseInfo.getCourseVideoCount() + "期");
            mTvBuyCount.setText("购买人：" + courseInfo.getCourseSaleCount() + "人");
            if (courseInfo.getCourseIsPurchase() == 0) {
                mTvStartBuyAll.setText("学习所有课程 ¥" + mTotalPrice);
            } else {
                mTvStartBuyAll.setVisibility(View.GONE);
                mLlSingle.setVisibility(View.GONE);
            }

            phoneNum = courseInfo.getCourseAdvisoryTel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mRefreshLayout.finishRefresh(true);
        }
    }

    @Override
    public void onVideoList(CourseVideoListBean courseVideoList) {
        try {
            mCourseVideoInfoBeans = courseVideoList.getCourseVideos();
            mCourseDetailsDescFrag.setCourseVideoInfoList(mCourseVideoInfoBeans);
            mCourseDetailsCatalogueFrag.setVideoInfoBeanList(mCourseVideoInfoBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateCommentResult(CourseCommentInfoBean result) {
        mCourseDetailsCommentFrag.getPresenter().createCommentNotify(result);
        hideSoftInput();
        mEtCommentContent.setText("");
        ToastUtils.showToast(mFragmentActivity, result.getResult());
    }

    @Override
    public void onClick(View v) {
        getPresenter().checkUserIsLogin(v.getId());
    }

    @Override
    protected void userStartAction(int viewId) {
        switch (viewId) {
            case R.id.tv_top_start_play:
            case R.id.ll_course_free_play_video:
                // 默认播放第一个视频
                if (mCourseVideoInfoBeans != null && mCourseVideoInfoBeans.size() > 0) {
                    CourseVideoInfoBean videoInfoBean = mCourseVideoInfoBeans.get(0);
                    Intent intent = new Intent(mFragmentActivity, CoursePlayAct.class);
                    intent.putExtra(CoursePlayAct.EXTRA_COURSE_ID, mCourseId);
                    intent.putExtra(CoursePlayAct.EXTRA_COURSE_VIDEO_ID, videoInfoBean.getCourseVideoId());
                    startActivity(intent);
                }
                break;
            case R.id.fl_course_consult:
                CourseDetailsFragPermissionsDispatcher.needCallPhoneWithPermissionCheck(this);
                break;
            case R.id.tv_course_buy_all:
                if (mSelectPaymentDialog != null) {
                    mSelectPaymentDialog = null;
                }
                mSelectPaymentDialog = SelectPaymentDialog.newInstance(String.valueOf(mTotalPrice));
                mSelectPaymentDialog.setOnStartPay(this);
                mSelectPaymentDialog.show(getChildFragmentManager(), mSelectPaymentDialog.getClass().getName());
                break;
            case R.id.tv_course_buy_single:
                mViewPagerContent.setCurrentItem(2);
                break;
            case R.id.btn_send_comment:
                getPresenter().createComment(mCourseId, mEtCommentContent.getText().toString());
                break;

        }
    }

    @Override
    public void startPay(boolean mSelectPayment) {
        getPresenter().startPayCourse(mSelectPayment ? 1 : 2, -1, mCourseId);
        if (mSelectPaymentDialog != null) {
            mSelectPaymentDialog.dismiss();
        }
    }

    @Subscribe
    public void payResult(BaseResp resp) {
        if (resp.errCode == 0) {
            ToastUtils.showToast(mFragmentActivity, "支付成功");
            mRefreshLayout.autoRefresh();
        } else {
            ToastUtils.showToast(mFragmentActivity, "支付失败");
        }
    }

    @Override
    public void onAliPaySuccess() {
        ToastUtils.showToast(mFragmentActivity, "支付成功");
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 3) {
            mLlBuy.setVisibility(View.GONE);
            mClComment.setVisibility(View.VISIBLE);
        } else {
            if (mLlBuy.getVisibility() == View.GONE) {
                mClComment.setVisibility(View.GONE);
                mLlBuy.setVisibility(View.VISIBLE);
            }
        }
        hideSoftInput();
        setTabSelector(tab, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        setTabSelector(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int totalScrollRange = -(appBarLayout.getTotalScrollRange() / 2);
        if (totalScrollRange >= verticalOffset) {
            if (isChangeWhite) {
                mFragmentActivity.runOnUiThread(() -> {
                    ImmersionBar.with(CourseDetailsFrag.this)
                            .statusBarDarkFont(true,0.2f)
                            .keyboardEnable(true)
                            .init();
                    isChangeBlack = true;
                    isChangeWhite = false;
                    mAppToolbar.setNavigationIcon(ContextCompat.getDrawable(mFragmentActivity, R.drawable.ic_back));
                    mLlStartPlay.setVisibility(View.VISIBLE);
                });
            }
        } else {
            if (isChangeBlack) {
                mFragmentActivity.runOnUiThread(() -> {
                    ImmersionBar.with(CourseDetailsFrag.this)
                            .statusBarDarkFont(false)
                            .keyboardEnable(true)
                            .init();

                    isChangeBlack = false;
                    isChangeWhite = true;
                    mAppToolbar.setNavigationIcon(ContextCompat.getDrawable(mFragmentActivity, R.drawable.ic_back_white));
                    mLlStartPlay.setVisibility(View.GONE);
                });
            }
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
            mBtnComment.setEnabled(false);
        } else {
            if (!mBtnComment.isEnabled()) {
                mBtnComment.setEnabled(true);
            }
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void needCallPhone() {

        if (phoneNum != null) {
            new AlertDialog.Builder(mFragmentActivity)
                    .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + phoneNum);
                            intent.setData(data);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage(phoneNum)
                    .show();
        } else {
            ToastUtils.showToast(mFragmentActivity, "暂时无法联系客服哦~");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CourseDetailsFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void rationalePhone(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_call_phone, request);
    }


}
