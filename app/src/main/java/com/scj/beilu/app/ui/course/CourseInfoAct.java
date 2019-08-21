package com.scj.beilu.app.ui.course;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/3/20 19:27
 */
public class CourseInfoAct extends BaseSupportAct {

    public static final String EXTRA_COURSE_ID = "course_id";

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();

        Intent intent = getIntent();
        if (intent == null) return;
        int courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);
        if (courseId == -1) {
            loadRootFragment(R.id.fl_content, new CourseBoughtFrag());
        } else {
            loadRootFragment(R.id.fl_content, CourseDetailsFrag.newInstance(courseId));
        }
    }
}
