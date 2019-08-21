package com.scj.beilu.app.mvp.course;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.pay.WXPayPre;
import com.scj.beilu.app.mvp.common.pay.WXPayView;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoListBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;
import com.scj.beilu.app.mvp.course.model.ICourseInfo;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:22
 */
public class CourseDetailsCataloguePre extends WXPayPre<CourseDetailsCataloguePre.CourseDetailsCatalogueView> {

    private ICourseInfo mCourseInfo;

    public CourseDetailsCataloguePre(Activity activity) {
        super(activity);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getCatalogueList(final int courseId) {

        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(new ViewAction<CourseDetailsCatalogueView>() {
            @Override
            public void run(@NonNull final CourseDetailsCatalogueView mvpView) {
                mCourseInfo.getCourseVideoById(courseId)
                        .subscribe(new BaseResponseCallback<CourseVideoListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseVideoListBean courseVideoListBean) {
                                mvpView.onCourseVideoList(courseVideoListBean.getCourseVideos());
                            }
                        });
            }
        });
    }

    public interface CourseDetailsCatalogueView extends WXPayView {
        void onCourseVideoList(List<CourseVideoInfoBean> courseVideoInfo);
    }
}
