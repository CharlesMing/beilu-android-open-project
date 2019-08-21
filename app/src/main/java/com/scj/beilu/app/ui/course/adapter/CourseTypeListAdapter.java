package com.scj.beilu.app.ui.course.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.course.bean.CourseTypeInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 16:02
 */
public class CourseTypeListAdapter extends RecyclerView.Adapter<CourseTypeListAdapter.CourseTypeViewHolder> {

    private List<CourseTypeInfoBean> mCourseTypeInfoList;
    private OnItemClickListener<CourseTypeInfoBean> mOnItemClickListener;

    public void setCourseTypeInfoList(List<CourseTypeInfoBean> courseTypeInfoList) {
        mCourseTypeInfoList = courseTypeInfoList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<CourseTypeInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CourseTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_type_content, parent, false);
        return new CourseTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTypeViewHolder holder, int position) {
        try {
            CourseTypeInfoBean infoBean = mCourseTypeInfoList.get(position);

            holder.mTvCourseTypeVal.setText(infoBean.getCourseCateName());
            holder.mTvCourseTypeVal.setSelected(infoBean.isSelect());
            holder.mView.setVisibility(infoBean.isSelect() ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mCourseTypeInfoList == null ? 0 : mCourseTypeInfoList.size();
    }

    public class CourseTypeViewHolder extends BaseViewHolder {
        private TextView mTvCourseTypeVal;
        private View mView;

        public CourseTypeViewHolder(View itemView) {
            super(itemView);
            mTvCourseTypeVal = findViewById(R.id.tv_course_type_val);
            mView = findViewById(R.id.view_show_rect);
            mTvCourseTypeVal.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    try {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mCourseTypeInfoList.get(getAdapterPosition()), v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
