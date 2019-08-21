package com.scj.beilu.app.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.course.CourseDetailsCataloguePre;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.ui.comment.SelectPaymentDialog;
import com.scj.beilu.app.ui.course.adapter.CourseCatalogueListAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:21
 */
public class CourseDetailsCatalogueFrag extends BaseMvpFragment<CourseDetailsCataloguePre.CourseDetailsCatalogueView, CourseDetailsCataloguePre>
        implements CourseDetailsCataloguePre.CourseDetailsCatalogueView,
        OnItemClickListener<CourseVideoInfoBean>,
        SelectPaymentDialog.onStartPay {

    private CourseCatalogueListAdapter mCourseCatalogueListAdapter;
    private List<CourseVideoInfoBean> mVideoInfoBeanList;
    private static final String COURSE_ID = "course_id";
    private int mCourseId = -1;

    private onVideoPlayer mOnVideoPlayer;
    private SelectPaymentDialog mSelectPaymentDialog;
    private int mVideoId;

    public interface onVideoPlayer {
        void onVideoInfoByCatalogue(int courseVideoId);
    }

    public void setOnVideoPlayer(onVideoPlayer onVideoPlayer) {
        mOnVideoPlayer = onVideoPlayer;
    }

    public static CourseDetailsCatalogueFrag newInstance(int courseId) {

        Bundle args = new Bundle();
        args.putInt(COURSE_ID, courseId);
        CourseDetailsCatalogueFrag fragment = new CourseDetailsCatalogueFrag();
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

    public void setVideoInfoBeanList(List<CourseVideoInfoBean> videoInfoBeanList) {
        mVideoInfoBeanList = videoInfoBeanList;
    }

    @Override
    public CourseDetailsCataloguePre createPresenter() {
        return new CourseDetailsCataloguePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_catalogue_list;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();
        mCourseCatalogueListAdapter = new CourseCatalogueListAdapter(this);
        mCourseCatalogueListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCourseCatalogueListAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mCourseId != -1) {
            getPresenter().getCatalogueList(mCourseId);
        } else {
            mCourseCatalogueListAdapter.setVideoInfoBeanList(mVideoInfoBeanList);
        }
    }

    @Override
    public void onItemClick(int position, CourseVideoInfoBean entity, View view) {
        switch (view.getId()) {
            case R.id.iv_course_img://课程图片点击
            case R.id.tv_course_unit_price://价格点击:
            case R.id.iv_course_player://播放图片点击
                if (entity.getCourseVideoPrice() == null || entity.getCourseVideoIsPurchase() == 1) {
                    //直接播放
                    if (mOnVideoPlayer != null) {
                        mOnVideoPlayer.onVideoInfoByCatalogue(entity.getCourseVideoId());
                    } else {
                        Intent intent = new Intent(mFragmentActivity, CoursePlayAct.class);
                        intent.putExtra(CoursePlayAct.EXTRA_COURSE_ID, entity.getCourseId());
                        intent.putExtra(CoursePlayAct.EXTRA_COURSE_VIDEO_ID, entity.getCourseVideoId());
                        startActivity(intent);
                    }
                } else {
                    //提购买
                    if (mSelectPaymentDialog != null) {
                        mSelectPaymentDialog = null;
                    }
                    mSelectPaymentDialog = SelectPaymentDialog.newInstance(String.valueOf(entity.getCourseVideoPrice()));
                    mSelectPaymentDialog.setOnStartPay(this);
                    mVideoId = entity.getCourseVideoId();
                    mSelectPaymentDialog.show(getChildFragmentManager(), mSelectPaymentDialog.getClass().getName());
                }
                break;
        }
    }

    @Override
    public void onCourseVideoList(List<CourseVideoInfoBean> courseVideoInfo) {
        mCourseCatalogueListAdapter.setVideoInfoBeanList(courseVideoInfo);
    }

    @Subscribe
    public void payResult(BaseResp resp) {
        if (mCourseId != -1) {
            if (resp.errCode == 0) {
                ToastUtils.showToast(mFragmentActivity, "支付成功");
                getPresenter().getCatalogueList(mCourseId);
            } else {
                ToastUtils.showToast(mFragmentActivity, "支付失败");
            }
        }
    }

    @Override
    public void onAliPaySuccess() {
        ToastUtils.showToast(mFragmentActivity, "支付成功");
        getPresenter().getCatalogueList(mCourseId);
    }

    @Override
    public void startPay(boolean mSelectPayment) {
        getPresenter().startPayCourse(mSelectPayment ? 1 : 2, mVideoId, -1);
        if (mSelectPaymentDialog != null) {
            mSelectPaymentDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
