package com.scj.beilu.app.mvp.course;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.course.bean.CourseBoughtListResult;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;
import com.scj.beilu.app.mvp.course.model.ICourseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/26 02:08
 */
public class CourseBoughtPre extends BaseMvpPresenter<CourseBoughtPre.CourseBoughtView> {

    private ICourseInfo mCourseInfo;
    private final List<CourseVideoInfoBean> mCourseVideoInfoBeans = new ArrayList<>();

    public CourseBoughtPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getBoughtCourseList(final int currentPage) {

        onceViewAttached(new ViewAction<CourseBoughtView>() {
            @Override
            public void run(@NonNull final CourseBoughtView mvpView) {
                mCourseInfo.getBoughtList(currentPage)
                        .subscribe(new BaseResponseCallback<CourseBoughtListResult>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseBoughtListResult courseBoughtListResult) {
                                try {
                                    List<CourseVideoInfoBean> list = courseBoughtListResult.getPage().getList();
                                    if (currentPage == 0) {
                                        mCourseVideoInfoBeans.clear();
                                    }
                                    mCourseVideoInfoBeans.addAll(list);
                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onVideoListResult(mCourseVideoInfoBeans);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface CourseBoughtView extends BaseCheckArrayView {
        void onVideoListResult(List<CourseVideoInfoBean> courseVideoList);
    }
}
