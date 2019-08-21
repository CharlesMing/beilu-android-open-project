package com.scj.beilu.app.mvp.course;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.course.bean.CourseTypeInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseTypeListBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/11 15:37
 */
public class CoursePre extends BaseMvpPresenter<CoursePre.CourseView> {

    private CourseInfoImpl mCourseInfo;
    private List<CourseTypeInfoBean> mCourseTypeInfoBeans;

    public CoursePre(Context context) {
        super(context, ShowConfig.LOADING_STATE, ShowConfig.ERROR_TOAST);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getCourseTypeList() {
        mBuilder.setLoadType(ShowConfig.LOADING_STATE);
        onceViewAttached(new ViewAction<CourseView>() {
            @Override
            public void run(@NonNull final CourseView mvpView) {
                mCourseInfo.getCourseTypeList()
                        .subscribe(new BaseResponseCallback<CourseTypeListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseTypeListBean courseTypeListBean) {
                                mCourseTypeInfoBeans = courseTypeListBean.getCates();
                                mvpView.onCourseTypeListResult(mCourseTypeInfoBeans);
                            }
                        });
            }
        });
    }

    public void setSelectCourseType(final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(new ViewAction<CourseView>() {
            @Override
            public void run(@NonNull final CourseView mvpView) {
                mCourseInfo.setSelect(mCourseTypeInfoBeans, position)
                        .subscribe(new ObserverCallback<CourseTypeInfoBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(CourseTypeInfoBean courseTypeInfoBean) {
                                mvpView.onSelectResult(courseTypeInfoBean);
                            }
                        });
            }
        });
    }

    public interface CourseView extends MvpView {

        void onCourseTypeListResult(List<CourseTypeInfoBean> courseTypeInfoList);

        void onSelectResult(CourseTypeInfoBean courseTypeInfo);
    }
}
