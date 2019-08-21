package com.scj.beilu.app.ui.course.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/22 17:06
 */
public class CourseOutlineListAdapter extends RecyclerView.Adapter<CourseOutlineListAdapter.OutlineViewHolder> {

    private List<CourseVideoInfoBean> mVideoInfoBeanList;

    public void setVideoInfoBeanList(List<CourseVideoInfoBean> videoInfoBeanList) {
        mVideoInfoBeanList = videoInfoBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OutlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_outline, parent, false);
        return new OutlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutlineViewHolder holder, int position) {
        CourseVideoInfoBean infoBean = mVideoInfoBeanList.get(position);

        holder.mTvNum.setText("第" + (position + 1) + "课");
        holder.mTvName.setText(infoBean.getCourseVideoName());
        holder.mTvDesc.setText(infoBean.getCourseVideoBrief());
    }

    @Override
    public int getItemCount() {
        return mVideoInfoBeanList == null ? 0 : mVideoInfoBeanList.size();
    }

    class OutlineViewHolder extends BaseViewHolder {
        private TextView mTvNum, mTvName, mTvDesc;

        public OutlineViewHolder(View itemView) {

            super(itemView);
            mTvNum = findViewById(R.id.tv_course_num);
            mTvName = findViewById(R.id.tv_course_video_name);
            mTvDesc = findViewById(R.id.tv_course_description);
        }
    }

}
