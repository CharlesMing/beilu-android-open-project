package com.scj.beilu.app.ui.course.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.ui.course.CourseDetailsCommentFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/25 20:30
 */
public class CourseCommentListAdapter extends RecyclerView.Adapter<CourseCommentListAdapter.CommentListViewHolder> {

    private List<CourseCommentInfoBean> mCourseCommentInfoList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    public CourseCommentListAdapter(CourseDetailsCommentFrag frag) {
        mOriginalRequest = GlideApp.with(frag).asDrawable().circleCrop();
        mThumbnailRequest = GlideApp.with(frag).asDrawable().circleCrop();
    }

    public void setCourseCommentInfoList(List<CourseCommentInfoBean> courseCommentInfoList) {
        mCourseCommentInfoList = courseCommentInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_comment_content, parent, false);
        return new CommentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, int position) {
        try {
            CourseCommentInfoBean commentContent = mCourseCommentInfoList.get(position);
            mOriginalRequest.load(commentContent.getUserHead())
                    .thumbnail(mThumbnailRequest.load(commentContent.getUserHeadCompression()))
                    .into(holder.mIvAvatar);
            holder.mTvUserName.setText(commentContent.getUserName());
            holder.mTvContent.setText(commentContent.getComContent());
            holder.mTvTime.setText(commentContent.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mCourseCommentInfoList == null ? 0 : mCourseCommentInfoList.size();
    }

    public class CommentListViewHolder extends BaseViewHolder {
        private ImageView mIvAvatar;
        private TextView mTvUserName;
        private TextView mTvContent;
        private TextView mTvTime;

        public CommentListViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = findViewById(R.id.iv_course_comment_avatar);
            mTvUserName = findViewById(R.id.tv_course_comment_user);
            mTvContent = findViewById(R.id.tv_course_comment_content);
            mTvTime = findViewById(R.id.tv_course_comment_time);
        }
    }

}
