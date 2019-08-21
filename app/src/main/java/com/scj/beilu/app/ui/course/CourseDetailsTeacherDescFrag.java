package com.scj.beilu.app.ui.course;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.course.CourseDetailsTeacherDescPre;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherBean;
import com.scj.beilu.app.ui.course.adapter.CourseTagListAdapter;
import com.scj.beilu.app.ui.course.adapter.CourseTeacherImgListAdapter;
import com.scj.beilu.app.widget.FlowLayoutManager;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:21
 */
public class CourseDetailsTeacherDescFrag extends BaseMvpFragment<CourseDetailsTeacherDescPre.CourseDetailsTeacherDescView, CourseDetailsTeacherDescPre>
        implements CourseDetailsTeacherDescPre.CourseDetailsTeacherDescView {

    private ImageView mIvAvatar;
    private TextView mTvTeacherName;
    private RecyclerView mRvTeacherTagList;
    private TextView mTvTeacherDescription;
    private RecyclerView mRvTeacherImgList;
    private TextView mTvTeacherSay;

    private static final String COURSE_ID = "course_id";
    private int mCourseId;

    private CourseTagListAdapter mCourseTagListAdapter;
    private CourseTeacherImgListAdapter mCourseTeacherImgListAdapter;

    public static CourseDetailsTeacherDescFrag newInstance(int courseId) {

        Bundle args = new Bundle();
        args.putInt(COURSE_ID, courseId);
        CourseDetailsTeacherDescFrag fragment = new CourseDetailsTeacherDescFrag();
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
    public CourseDetailsTeacherDescPre createPresenter() {
        return new CourseDetailsTeacherDescPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_course_teacher_desc;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this).statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();
        mIvAvatar = findViewById(R.id.iv_course_teacher_avatar);
        mTvTeacherName = findViewById(R.id.tv_course_teacher_name);
        mRvTeacherTagList = findViewById(R.id.rv_course_teacher_tag_list);
        mTvTeacherDescription = findViewById(R.id.tv_course_teacher_description);
        mRvTeacherImgList = findViewById(R.id.rv_course_teacher_img_list);
        mTvTeacherSay = findViewById(R.id.tv_course_teacher_say);

        mCourseTeacherImgListAdapter = new CourseTeacherImgListAdapter(this);
        mRvTeacherImgList.setAdapter(mCourseTeacherImgListAdapter);

        int rectSize = 0;
        if (isAdded()) {
            rectSize = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_6);
        }

        final int finalRectSize = rectSize;
        mRvTeacherImgList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, finalRectSize, 0);
            }
        });

        mCourseTagListAdapter = new CourseTagListAdapter();
        mRvTeacherTagList.setAdapter(mCourseTagListAdapter);

        FlowLayoutManager layoutManager = new FlowLayoutManager();
        mRvTeacherTagList.setLayoutManager(layoutManager);
        mRvTeacherTagList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5, 5, 5, 5);
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getTeacherInfo(mCourseId);
    }

    @Override
    public void onTeacherInfo(CourseTeacherBean teacherBean) {
        try {

            GlideApp.with(this)
                    .load(teacherBean.getCourseTeacherHead())
                    .thumbnail(GlideApp.with(this).load(teacherBean.getCourseTeacherHeadZip()).centerCrop())
                    .placeholder(R.drawable.ic_default_avatar)
                    .circleCrop()
                    .into(mIvAvatar);

            mTvTeacherName.setText(teacherBean.getCourseTeacherName());
            mTvTeacherDescription.setText(teacherBean.getCourseTeacherBrief());
            mTvTeacherSay.setText(teacherBean.getCourseTeacherWords());

            mCourseTagListAdapter.setLabels(teacherBean.getCourseTeacherLabel());
            mCourseTeacherImgListAdapter.setCourseTeacherImgList(teacherBean.getPics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
