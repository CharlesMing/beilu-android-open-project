package com.scj.beilu.app.ui.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.bean.NewsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsReplyCommentBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/16 14:54
 */
public class NewsCommentListParentAdapter extends RecyclerView.Adapter<CommentParentViewHolder> {

    private List<NewsCommentBean> mCommentParentList;
    private GlideRequests mGlideRequests;
    private OnItemClickListener mItemClickListener;
    private Context mContext;

    public NewsCommentListParentAdapter(GlideRequests requests, Context context) {
        mContext = context;
        mGlideRequests = requests;
    }

    public void setCommentParentList(List<NewsCommentBean> commentParentList) {
        mCommentParentList = commentParentList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CommentParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_news_comment_content, parent, false);
        return new CommentParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentParentViewHolder holder, final int position) {
        final NewsCommentBean comment = mCommentParentList.get(position);
        mGlideRequests.load(comment.getUserHead())
                .thumbnail(0.2f)
                .transform(new CircleCrop())
                .into(holder.mIvAvatar);
        holder.mTvUserName.setText(comment.getUserName());
        holder.mTvCommentContent.setText(comment.getComContent());
        holder.mTvCommentDate.setText(comment.getComDate());
        if (comment.getNewsComReplyList().size() == 0) {
            holder.mRvCommentReplyList.setVisibility(View.GONE);
        } else {
            holder.mRvCommentReplyList.setVisibility(View.VISIBLE);
            NewsCommentChildAdapter childAdapter = new NewsCommentChildAdapter(mContext);
            childAdapter.setOnItemClickListener(new OnItemClickListener<NewsReplyCommentBean>() {
                @Override
                public void onItemClick(int pos, NewsReplyCommentBean entity, View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(position, entity, view);
                    }
                }
            });
            holder.mRvCommentReplyList.setAdapter(childAdapter);
            childAdapter.setNewsReplyCommentBeans(comment.getNewsComReplyList());
        }
        holder.mTvCommentContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, comment, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommentParentList == null ? 0 : mCommentParentList.size();
    }


}
