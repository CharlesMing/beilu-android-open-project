package com.scj.beilu.app.ui.course.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.course.bean.CourseDetailsQ$AInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/22 18:33
 */
public class CourseQ$AListAdapter extends RecyclerView.Adapter<CourseQ$AListAdapter.QAViewHolder> {

    private List<CourseDetailsQ$AInfoBean> mDetailsQ$AInfoList;

    public CourseQ$AListAdapter(Context context) {
    }

    public void setDetailsQ$AInfoList(List<CourseDetailsQ$AInfoBean> detailsQ$AInfoList) {
        mDetailsQ$AInfoList = detailsQ$AInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_q_a_content, parent, false);
        return new QAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QAViewHolder holder, int position) {
        CourseDetailsQ$AInfoBean infoBean = mDetailsQ$AInfoList.get(position);
        holder.mTvQContent.setText(infoBean.getCourseQ());
        holder.mTvAContent.setText(infoBean.getCourseA());
    }

    @Override
    public int getItemCount() {
        return mDetailsQ$AInfoList == null ? 0 : mDetailsQ$AInfoList.size();
    }

    class QAViewHolder extends BaseViewHolder {
        private TextView mTvQContent, mTvAContent;


        public QAViewHolder(View itemView) {
            super(itemView);
            mTvQContent = findViewById(R.id.tv_q_content);
            mTvAContent = findViewById(R.id.tv_a_content);
        }
    }
}
