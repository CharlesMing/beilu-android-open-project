package com.scj.beilu.app.ui.course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.course.CourseDetailsCommentPre;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.ui.course.adapter.CourseCommentListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:21
 */
public class CourseDetailsCommentFrag extends BaseMvpFragment<CourseDetailsCommentPre.CourseDetailsCommentView, CourseDetailsCommentPre>
        implements CourseDetailsCommentPre.CourseDetailsCommentView {

    private LinearLayout mLlLoadEmpty;

    private static final String COURSE_ID = "course_id";
    private int mCourseId;
    private CourseCommentListAdapter mCourseCommentListAdapter;

    public static CourseDetailsCommentFrag newInstance(int courseId) {

        Bundle args = new Bundle();
        args.putInt(COURSE_ID, courseId);
        CourseDetailsCommentFrag fragment = new CourseDetailsCommentFrag();
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
    }

    @Override
    public CourseDetailsCommentPre createPresenter() {
        return new CourseDetailsCommentPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_comment_list;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();
        mLlLoadEmpty = findViewById(R.id.ll_load_empty_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));

        mCourseCommentListAdapter = new CourseCommentListAdapter(this);
        mRecyclerView.setAdapter(mCourseCommentListAdapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCommentList(mCourseId, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getCommentList(mCourseId, mCurrentPage);
    }

    @Override
    public void onCommentList(List<CourseCommentInfoBean> commentList) {

        if (commentList.size() == 0) {
            mLlLoadEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mLlLoadEmpty.setVisibility(View.GONE);
            mCourseCommentListAdapter.setCourseCommentInfoList(commentList);
        }

    }

    @Override
    public void onCreateNotifyResult() {
        if (mRecyclerView.getVisibility() == View.GONE) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mLlLoadEmpty.setVisibility(View.GONE);
        }
        mCourseCommentListAdapter.notifyItemInserted(0);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }
}
