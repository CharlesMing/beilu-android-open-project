package com.scj.beilu.app.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.course.CoursePre;
import com.scj.beilu.app.mvp.course.bean.CourseTypeInfoBean;
import com.scj.beilu.app.ui.course.adapter.CourseTypeListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/11 15:35
 * 課程
 */
public class CourseFrag extends BaseMvpFragment<CoursePre.CourseView, CoursePre>
        implements CoursePre.CourseView, OnItemClickListener<CourseTypeInfoBean> {

    private RecyclerView mRvCourseTypeList;
    private LinearLayout mLlLoading;

    private CourseTypeListAdapter mCourseTypeListAdapter;

    @Override
    public CoursePre createPresenter() {
        return new CoursePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course;
    }

    @Override
    public void initView() {
        super.initView();
        mAppToolbar = findViewById(R.id.toolbar_course);
        mRvCourseTypeList = findViewById(R.id.rv_course_type_list);
        mLlLoading = findViewById(R.id.ll_loading_layout);

        mCourseTypeListAdapter = new CourseTypeListAdapter();
        mCourseTypeListAdapter.setOnItemClickListener(this);
        mRvCourseTypeList.setAdapter(mCourseTypeListAdapter);
        mAppToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCourseTypeList();
    }


    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading != ShowConfig.NONE) {
            super.showLoading(loading, isShow);
            mLlLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        super.showError(errorCode, throwableContent);
        mLlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onReLoad() {
        getPresenter().getCourseTypeList();
    }

    @Override
    public void onCourseTypeListResult(List<CourseTypeInfoBean> courseTypeInfoList) {
        mCourseTypeListAdapter.setCourseTypeInfoList(courseTypeInfoList);
        getPresenter().setSelectCourseType(0);
    }

    @Override
    public void onSelectResult(CourseTypeInfoBean courseTypeInfo) {
        mCourseTypeListAdapter.notifyDataSetChanged();
        loadRootFragment(R.id.fl_course_list_content, CourseListFrag.newInstance(courseTypeInfo.getCourseCateId()));
    }

    @Override
    public void onItemClick(int position, CourseTypeInfoBean entity, View view) {
        if (!entity.isSelect()) {
            getPresenter().setSelectCourseType(position);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        getPresenter().checkUserIsLogin(item.getItemId());
        return super.onMenuItemClick(item);
    }

    @Override
    protected void userStartAction(int viewId) {
        if (viewId == R.id.menu_bought) {
            Intent intent = new Intent(mFragmentActivity, CourseInfoAct.class);
            startActivity(intent);
        }
    }
}
