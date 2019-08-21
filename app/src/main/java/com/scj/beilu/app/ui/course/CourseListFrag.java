package com.scj.beilu.app.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.course.CourseListPre;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.ui.course.adapter.CourseListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 17:15
 */
public class CourseListFrag extends BaseMvpFragment<CourseListPre.CourseListView, CourseListPre>
        implements CourseListPre.CourseListView, OnItemClickListener<CourseInfoBean> {

    private static final String COURSE_TYPE_ID = "type_id";

    private int mCourseTypeId;
    private CourseListAdapter mHomeCourseListAdapter;

    public static CourseListFrag newInstance(int courseTypeId) {

        Bundle args = new Bundle();
        args.putInt(COURSE_TYPE_ID, courseTypeId);
        CourseListFrag fragment = new CourseListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCourseTypeId = arguments.getInt(COURSE_TYPE_ID);
        }
    }

    @Override
    public CourseListPre createPresenter() {
        return new CourseListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_list;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView = findViewById(R.id.rv_course_list);
        mHomeCourseListAdapter = new CourseListAdapter(this);
        mHomeCourseListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mHomeCourseListAdapter);

        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getCourseList(mCourseTypeId, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getCourseList(mCourseTypeId, mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onCourseListResult(List<CourseInfoBean> courseInfoBeanList) {
        mHomeCourseListAdapter.setCourseInfoList(courseInfoBeanList);
    }

    @Override
    public void onItemClick(int position, CourseInfoBean entity, View view) {
        Intent intent = new Intent(mFragmentActivity, CourseInfoAct.class);
        intent.putExtra(CourseInfoAct.EXTRA_COURSE_ID, entity.getCourseId());
        startActivity(intent);
    }
}

