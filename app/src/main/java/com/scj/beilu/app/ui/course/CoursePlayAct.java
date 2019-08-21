package com.scj.beilu.app.ui.course;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.orhanobut.logger.Logger;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseVideoMvpActivity;
import com.scj.beilu.app.mvp.course.CoursePlayerPre;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.ui.course.adapter.DescriptionFragAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mingxun
 * @time on 2019/3/25 21:51
 */
public class CoursePlayAct extends BaseVideoMvpActivity<CoursePlayerPre.CoursePlayerView,
        CoursePlayerPre, StandardGSYVideoPlayer>
        implements CoursePlayerPre.CoursePlayerView,
        TabLayout.OnTabSelectedListener, TextWatcher, View.OnClickListener,
        CourseDetailsCatalogueFrag.onVideoPlayer {

    private StandardGSYVideoPlayer mDetailPlayer;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ConstraintLayout mConstraintLayout;
    private EditText mEtCommentContent;
    private Button mBtnComment;

    public static final String EXTRA_COURSE_ID = "course_id";
    public static final String EXTRA_COURSE_VIDEO_ID = "video_id";

    private int mCourseId;
    private int mCourseVideoId;
    private final int defaultVal = -1;

    private DescriptionFragAdapter mDescriptionFragAdapter;

    private CourseDetailsCommentFrag mCourseDetailsCommentFrag;
    private CourseDetailsCatalogueFrag mCourseDetailsCatalogueFrag;

    private CourseVideoInfoBean mVideoInfoBean;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;


    @NonNull
    @Override
    public CoursePlayerPre createPresenter() {
        return new CoursePlayerPre(this);
    }

    @Override
    public int getLayout() {
        return R.layout.act_course_play;
    }

    @Override
    protected void initView() {
        super.initView();

        mDetailPlayer = findViewById(R.id.course_detail_player);
        mViewPager = findViewById(R.id.view_pager_course_player);
        mTabLayout = findViewById(R.id.tab_layout_course);
        mConstraintLayout = findViewById(R.id.cl_comment_layout);
        mEtCommentContent = findViewById(R.id.et_comment_content);
        mBtnComment = findViewById(R.id.btn_send_comment);

        ImmersionBar.with(this)
                .reset()
                .statusBarColor(android.R.color.black)
                .titleBar(findViewById(R.id.view_top))
                .init();


        mTabLayout.setupWithViewPager(mViewPager);

        mDetailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mDetailPlayer.getBackButton().setVisibility(View.VISIBLE);

        mTabLayout.addOnTabSelectedListener(this);

        Intent intent = getIntent();
        mCourseId = intent.getIntExtra(EXTRA_COURSE_ID, defaultVal);
        mCourseVideoId = intent.getIntExtra(EXTRA_COURSE_VIDEO_ID, defaultVal);

        List<SupportFragment> fragments = new ArrayList<>();
        mCourseDetailsCommentFrag = CourseDetailsCommentFrag.newInstance(mCourseId);
        mCourseDetailsCatalogueFrag = CourseDetailsCatalogueFrag.newInstance(mCourseId);
        mCourseDetailsCatalogueFrag.setOnVideoPlayer(this);
        fragments.add(mCourseDetailsCatalogueFrag);
        fragments.add(mCourseDetailsCommentFrag);

        mDescriptionFragAdapter = new DescriptionFragAdapter(getSupportFragmentManager());
        mDescriptionFragAdapter.setFragments(fragments);
        mViewPager.setAdapter(mDescriptionFragAdapter);

        int count = mTabLayout.getTabCount();
        final String[] mTabTitles = {"课程目录", "评论"};

        if (count == mTabTitles.length) {
            for (int i = 0; i < count; i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabLayout(mTabTitles[i]));
            }
            setTabSelector(mTabLayout.getTabAt(0), true);
            mViewPager.setOffscreenPageLimit(count);
        }

        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(60); // 设置分割线的pandding
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.sp_exposure_select));

        mDetailPlayer.getBackButton().setOnClickListener(this);
        mEtCommentContent.addTextChangedListener(this);
        mBtnComment.setOnClickListener(this);

        getPresenter().getVideoInfo(mCourseVideoId);

        mOriginalRequest = GlideApp.with(this).asDrawable().centerCrop();
        mThumbnailRequest = GlideApp.with(this).asDrawable().circleCrop();
    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return mDetailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        GSYVideoOptionBuilder gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        try {
            ImageView imageView = new ImageView(this);

            mOriginalRequest
                    .load(mVideoInfoBean.getCourseVideoPic())
                    .thumbnail(mThumbnailRequest.load(mVideoInfoBean.getCourseVideoPicZip()))
                    .into(imageView);
            Logger.e(mVideoInfoBean.getCourseVideoAddr() + "=====" + mVideoInfoBean.getCourseVideoName());
            Map<String, String> header = new HashMap<>();
            header.put("Referer", "https://coursevideo.cqsanchaji.com");
            gsyVideoOptionBuilder
                    .setThumbImageView(imageView)
                    .setUrl(mVideoInfoBean.getCourseVideoAddr())
                    .setMapHeadData(header)
                    .setCacheWithPlay(true)
                    .setVideoTitle(mVideoInfoBean.getCourseVideoName())
                    .setIsTouchWiget(true)
                    .setRotateViewAuto(false)
                    .setLockLand(false)
                    .setAutoFullWithSize(true)
                    .setShowFullAnimation(true)//打开动画
                    .setNeedLockFull(true)
                    .setSeekRatio(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsyVideoOptionBuilder;
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_layout_tab_course, null);
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
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 1) {
            mConstraintLayout.setVisibility(View.VISIBLE);
        } else {
            if (mConstraintLayout.getVisibility() == View.VISIBLE) {
                mConstraintLayout.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_comment:
                getPresenter().createComment(mCourseId, mEtCommentContent.getText().toString());
                break;
            case R.id.back:
                onBackPressedSupport();
                break;
        }
    }

    @Override
    public void onCreateCommentResult(CourseCommentInfoBean result) {
        mCourseDetailsCommentFrag.getPresenter().createCommentNotify(result);
        hideSoftInput();
        mEtCommentContent.setText("");
        ToastUtils.showToast(this, result.getResult());
    }

    @Override
    public void onVideoInfo(CourseVideoInfoBean videoInfo) {
        mVideoInfoBean = videoInfo;
        initVideoBuilderMode();
        getGSYVideoPlayer().getCurrentPlayer().startPlayLogic();
    }

    @Override
    public void onVideoInfoByCatalogue(int courseVideoId) {
        getPresenter().getVideoInfo(courseVideoId);
    }
}
