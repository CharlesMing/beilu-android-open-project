package com.scj.beilu.app.ui.course.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherImgBean;
import com.scj.beilu.app.ui.course.CourseDetailsTeacherDescFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/21 22:15
 */
public class CourseTeacherImgListAdapter extends RecyclerView.Adapter<CourseTeacherImgListAdapter.TeacherImgViewHolder> {

    private List<CourseTeacherImgBean> mCourseTeacherImgList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    public CourseTeacherImgListAdapter(CourseDetailsTeacherDescFrag frag) {
        mOriginalRequest = GlideApp.with(frag).asDrawable().centerCrop();
        mThumbnailRequest = GlideApp.with(frag).asDrawable().centerCrop();
    }

    public void setCourseTeacherImgList(List<CourseTeacherImgBean> courseTeacherImgList) {
        mCourseTeacherImgList = courseTeacherImgList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeacherImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_teacher_img, parent, false);
        return new TeacherImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherImgViewHolder holder, int position) {
        CourseTeacherImgBean teacherImgBean = mCourseTeacherImgList.get(position);
        mOriginalRequest.load(teacherImgBean.getCourseTeacherPicAddr())
                .thumbnail(mThumbnailRequest.load(teacherImgBean.getCourseTeacherPicAddrZip()))
                .into(holder.mIvImg);
    }

    @Override
    public int getItemCount() {
        return mCourseTeacherImgList == null ? 0 : mCourseTeacherImgList.size();
    }

    class TeacherImgViewHolder extends BaseViewHolder {
        private ImageView mIvImg;

        public TeacherImgViewHolder(View itemView) {
            super(itemView);
            mIvImg = findViewById(R.id.iv_teacher_img);
        }
    }

}
