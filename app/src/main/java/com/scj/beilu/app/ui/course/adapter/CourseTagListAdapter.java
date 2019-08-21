package com.scj.beilu.app.ui.course.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/3/13 11:29
 */
public class CourseTagListAdapter extends RecyclerView.Adapter<CourseTagListAdapter.CourseTagViewHolder> {

    private String[] mLabels;

    public void setLabels(String labels) {
        try {
            if (labels.contains(",")) {
                mLabels = labels.split(",");
            } else {
                mLabels = new String[]{labels};
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CourseTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page_course_tag, parent, false);
        return new CourseTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTagViewHolder holder, int position) {
        String label = mLabels[position];
        holder.mTvCourseTag.setText(label);
    }

    @Override
    public int getItemCount() {
        return mLabels == null ? 0 : mLabels.length;
    }

    class CourseTagViewHolder extends BaseViewHolder {
        private TextView mTvCourseTag;

        public CourseTagViewHolder(View itemView) {
            super(itemView);
            mTvCourseTag = findViewById(R.id.tv_home_page_goods_tag);
        }
    }
}
