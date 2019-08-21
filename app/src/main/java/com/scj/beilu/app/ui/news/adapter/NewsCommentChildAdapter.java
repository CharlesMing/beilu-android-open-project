package com.scj.beilu.app.ui.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.bean.NewsReplyCommentBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/16 14:55
 */
public class NewsCommentChildAdapter extends RecyclerView.Adapter<CommentChildViewHolder> {

    private List<NewsReplyCommentBean> mNewsReplyCommentBeans;
    private Context mContext;
    private OnItemClickListener<NewsReplyCommentBean> mOnItemClickListener;

    public NewsCommentChildAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener<NewsReplyCommentBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setNewsReplyCommentBeans(List<NewsReplyCommentBean> newsReplyCommentBeans) {
        mNewsReplyCommentBeans = newsReplyCommentBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_comment_reply_content, parent, false);
        return new CommentChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentChildViewHolder holder, final int position) {
        final NewsReplyCommentBean commentContent = mNewsReplyCommentBeans.get(position);
        String format = mContext.getResources().getString(R.string.txt_reply_content);
        String html = String.format(format, "<font color='#999999'>" + commentContent.getFromUserName() + "</font>",
                "<font color='#999999'>" + commentContent.getToUserName() + "</font>", commentContent.getNewsReplyContent());
        holder.mTvReplyCommentContent.setText(Html.fromHtml(html));
        holder.mTvReplyCommentContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, commentContent, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsReplyCommentBeans == null ? 0 : mNewsReplyCommentBeans.size();
    }
}
