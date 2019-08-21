package com.scj.beilu.app.ui.find.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindCommentReplyBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/16 14:55
 */
public class FindDetailsChildCommentAdapter extends RecyclerView.Adapter<FindDetailsChildCommentViewHolder> {

    private List<FindCommentReplyBean> mReplayCommentList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public FindDetailsChildCommentAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setReplayCommentList(List<FindCommentReplyBean> replayCommentList) {
        mReplayCommentList = replayCommentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FindDetailsChildCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_comment_reply_content, parent, false);
        return new FindDetailsChildCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindDetailsChildCommentViewHolder holder, final int position) {
        final FindCommentReplyBean commentContent = mReplayCommentList.get(position);
        String format = mContext.getResources().getString(R.string.txt_reply_content);
        String html = String.format(format, "<font color='#999999'>" + commentContent.getFromUserName() + "</font>",
                "<font color='#999999'>" + commentContent.getToUserName() + "</font>", commentContent.getDynamicReplyContent());
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
        return mReplayCommentList == null ? 0 : mReplayCommentList.size();
    }
}
