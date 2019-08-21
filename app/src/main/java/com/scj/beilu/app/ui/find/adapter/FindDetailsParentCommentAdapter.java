package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/25 21:58
 * 动态详情评论列表适配器
 */
public class FindDetailsParentCommentAdapter extends RecyclerView.Adapter<FindDetailsParentCommentViewHolder> {
    private GlideRequest<Drawable> mGlideRequests;
    private OnItemClickListener mItemClickListener;
    private List<FindCommentBean> mFindCommentBeans;

    public FindDetailsParentCommentAdapter(GlideRequest<Drawable> glideRequests) {
        mGlideRequests = glideRequests;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setFindCommentBeans(List<FindCommentBean> findCommentBeans) {
        mFindCommentBeans = findCommentBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FindDetailsParentCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_news_comment_content, parent, false);
        return new FindDetailsParentCommentViewHolder(view, mGlideRequests, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FindDetailsParentCommentViewHolder holder, int position) {
        holder.setBind(mFindCommentBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mFindCommentBeans == null ? 0 : mFindCommentBeans.size();
    }
}
