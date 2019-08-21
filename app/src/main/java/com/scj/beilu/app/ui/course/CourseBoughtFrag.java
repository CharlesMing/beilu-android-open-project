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
import com.scj.beilu.app.mvp.course.CourseBoughtPre;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.ui.course.adapter.CourseCatalogueListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/26 02:03
 * 已购课程
 */
public class CourseBoughtFrag extends BaseMvpFragment<CourseBoughtPre.CourseBoughtView, CourseBoughtPre>
        implements CourseBoughtPre.CourseBoughtView, OnItemClickListener<CourseVideoInfoBean> {

    private CourseCatalogueListAdapter mCourseCatalogueListAdapter;

    @Override
    public CourseBoughtPre createPresenter() {
        return new CourseBoughtPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_bought;
    }

    @Override
    public void initView() {
        super.initView();
        mCourseCatalogueListAdapter = new CourseCatalogueListAdapter(this);
        mCourseCatalogueListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCourseCatalogueListAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getBoughtCourseList(mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getBoughtCourseList(mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, CourseVideoInfoBean entity, View view) {
        Intent intent = new Intent(mFragmentActivity, CoursePlayAct.class);
        intent.putExtra(CoursePlayAct.EXTRA_COURSE_ID, entity.getCourseId());
        intent.putExtra(CoursePlayAct.EXTRA_COURSE_VIDEO_ID, entity.getCourseVideoId());
        startActivity(intent);

    }

    @Override
    public void onVideoListResult(List<CourseVideoInfoBean> courseVideoList) {
        mCourseCatalogueListAdapter.setVideoInfoBeanList(courseVideoList);
    }
}
