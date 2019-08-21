package com.scj.beilu.app.ui.course.adapter;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.widget.FlowLayoutManager;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 19:02
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {
    private GlideRequest<Drawable> mOriginalGlideRequest;
    private GlideRequest<Drawable> mThumbnailGlideRequest;
    private List<CourseInfoBean> mCourseInfoList;
    private OnItemClickListener<CourseInfoBean> mOnItemClickListener;
    private final String mCount;

    public CourseListAdapter(SupportFragment fragment) {
        mOriginalGlideRequest = GlideApp.with(fragment).asDrawable().centerCrop();
        mThumbnailGlideRequest = GlideApp.with(fragment).asDrawable().centerCrop();
        mCount = fragment.getResources().getString(R.string.home_course_time_browse);
    }

    public void setCourseInfoList(List<CourseInfoBean> courseInfoList) {
        mCourseInfoList = courseInfoList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<CourseInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_content, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        try {
            CourseInfoBean courseInfo = mCourseInfoList.get(position);
            mOriginalGlideRequest.load(courseInfo.getCoursePic())
                    .thumbnail(mThumbnailGlideRequest.load(courseInfo.getCoursePicZip()).centerCrop())
                    .centerCrop()
                    .into(holder.mIvCourseImage);
            holder.mTvCourseTitle.setText(courseInfo.getCourseName());
            holder.mTvCourseDesc.setText(courseInfo.getCourseBrief());
            CourseTagListAdapter tagListAdapter = new CourseTagListAdapter();
            tagListAdapter.setLabels(courseInfo.getCourseLabel());
            holder.mRvCourseTagList.setAdapter(tagListAdapter);
            String count = String.format(mCount, courseInfo.getCourseVideoCount(), courseInfo.getCourseBrowseCount());
            holder.mTvCourseNum.setText(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mCourseInfoList == null ? 0 : mCourseInfoList.size();
    }

    class CourseViewHolder extends BaseViewHolder {
        private ImageView mIvCourseImage;
        private TextView mTvCourseTitle;
        private TextView mTvCourseDesc;
        private TextView mTvCourseNum;
        private RecyclerView mRvCourseTagList;
        private LinearLayout mLlContent;

        public CourseViewHolder(View itemView) {
            super(itemView);
            mIvCourseImage = findViewById(R.id.iv_home_page_course_img);
            mTvCourseTitle = findViewById(R.id.tv_home_page_course_title);
            mTvCourseDesc = findViewById(R.id.tv_home_page_course_description);
            mRvCourseTagList = findViewById(R.id.rv_home_page_course_tag);
            mTvCourseNum = findViewById(R.id.tv_home_page_course_num);
            mLlContent = findViewById(R.id.ll_item_course_content);
            FlowLayoutManager layoutManager = new FlowLayoutManager();
            mRvCourseTagList.setLayoutManager(layoutManager);
            mRvCourseTagList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(5, 5, 5, 5);
                }
            });
            mLlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mCourseInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
            mRvCourseTagList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mCourseInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
