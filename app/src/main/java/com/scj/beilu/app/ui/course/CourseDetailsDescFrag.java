package com.scj.beilu.app.ui.course;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.course.CourseDetailsDescPre;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.ui.course.adapter.CourseOutlineListAdapter;
import com.scj.beilu.app.ui.course.adapter.CourseQ$AListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:21
 */
public class CourseDetailsDescFrag extends BaseMvpFragment<CourseDetailsDescPre.CourseDetailsDescView, CourseDetailsDescPre>
        implements CourseDetailsDescPre.CourseDetailsDescView {

    private ImageView mIvCourseImg;
    private TextView mTvLongTime, mTvCourseCount, mTvCourseDesc;
    private RecyclerView mRvQAList;
    private TextView mTvBuyerGuide;

    private CourseInfoBean mCourseInfo;
    private List<CourseVideoInfoBean> mCourseVideoInfoList;
    private CourseOutlineListAdapter mCourseOutlineListAdapter;
    private CourseQ$AListAdapter mCourseQ$AListAdapter;

    public void setCourseInfo(CourseInfoBean courseInfo) {
        mCourseInfo = courseInfo;
    }

    public void setCourseVideoInfoList(List<CourseVideoInfoBean> courseVideoInfoList) {
        mCourseVideoInfoList = courseVideoInfoList;
    }

    @Override
    public CourseDetailsDescPre createPresenter() {
        return new CourseDetailsDescPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_desc;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();
        mIvCourseImg = findViewById(R.id.iv_course_img);
        mTvLongTime = findViewById(R.id.tv_long_time_val);
        mTvCourseCount = findViewById(R.id.tv_course_count);
        mTvCourseDesc = findViewById(R.id.tv_course_desc);
        mRecyclerView = findViewById(R.id.rv_course_outline_list);
        mRvQAList = findViewById(R.id.rv_q_a_list);
        mTvBuyerGuide = findViewById(R.id.tv_buyers_guide);

        mCourseOutlineListAdapter = new CourseOutlineListAdapter();
        mRecyclerView.setAdapter(mCourseOutlineListAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 10, 0);
            }
        });

        mCourseQ$AListAdapter = new CourseQ$AListAdapter(mFragmentActivity);
        mRvQAList.setAdapter(mCourseQ$AListAdapter);

        mRvQAList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 20);
            }
        });
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        try {
            GlideApp.with(this)
                    .load(mCourseInfo.getCoursePic())
                    .thumbnail(GlideApp.with(this)
                            .load(mCourseInfo.getCoursePicZip()).circleCrop())
                    .circleCrop()
                    .into(mIvCourseImg);
            mTvLongTime.setText(mCourseInfo.getCourseVideoTime());
            mTvCourseCount.setText(String.valueOf(mCourseInfo.getCourseVideoCount()));
            mTvCourseDesc.setText(mCourseInfo.getCourseDec());

            mCourseOutlineListAdapter.setVideoInfoBeanList(mCourseVideoInfoList);

            mCourseQ$AListAdapter.setDetailsQ$AInfoList(mCourseInfo.getQas());

            mTvBuyerGuide.setText(Html.fromHtml(mCourseInfo.getCourseNotice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
