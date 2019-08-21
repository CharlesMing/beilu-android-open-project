package com.scj.beilu.app.mvp.course;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;
import com.scj.beilu.app.mvp.course.model.ICourseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 17:15
 */
public class CourseListPre extends BaseMvpPresenter<CourseListPre.CourseListView> {


    private ICourseInfo mCourseInfo;
    private final List<CourseInfoBean> mCourseInfoBeans = new ArrayList<>();

    public CourseListPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getCourseList(final int courseTypeId, final int currentPage) {
        onceViewAttached(new ViewAction<CourseListView>() {
            @Override
            public void run(@NonNull final CourseListView mvpView) {
                mCourseInfo.getCourseList(courseTypeId, currentPage)
                        .subscribe(new BaseResponseCallback<CourseListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseListBean courseListBean) {
                                try {
                                    List<CourseInfoBean> courses = courseListBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mCourseInfoBeans.clear();
                                    }
                                    mCourseInfoBeans.addAll(courses);
                                    mvpView.onCheckLoadMore(courses);
                                    mvpView.onCourseListResult(mCourseInfoBeans);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface CourseListView extends BaseCheckArrayView {
        void onCourseListResult(List<CourseInfoBean> courseInfoBeanList);
    }
}
