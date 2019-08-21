package com.scj.beilu.app.mvp.course;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:22
 */
public class CourseDetailsDescPre extends BaseMvpPresenter<CourseDetailsDescPre.CourseDetailsDescView> {
    public CourseDetailsDescPre(Context context) {
        super(context);
    }

    public interface CourseDetailsDescView extends MvpView {

    }
}
