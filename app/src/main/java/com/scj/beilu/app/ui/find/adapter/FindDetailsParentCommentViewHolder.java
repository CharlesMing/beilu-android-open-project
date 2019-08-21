package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;

/**
 * @author Mingxun
 * @time on 2019/2/25 21:58
 * 动态详情评论列表适配器
 */
public class FindDetailsParentCommentViewHolder extends BaseViewHolder {
    private TextView mTvUserName, mTvCommentContent, mTvCommentDate;
    private ImageView mIvAvatar;
    private RecyclerView mRvCommentReplyList;
    private GlideRequest<Drawable> mGlideRequests;
    private OnItemClickListener mOnItemClickListener;
    private FindCommentBean mCommentBean;

    private FindDetailsChildCommentAdapter mChildCommentAdapter;

    public FindDetailsParentCommentViewHolder(View itemView, GlideRequest<Drawable> glideRequest,
                                              OnItemClickListener itemClickListener) {
        super(itemView);
        mGlideRequests = glideRequest;
        mOnItemClickListener = itemClickListener;

        mTvUserName = findViewById(R.id.tv_news_user_name);
        mTvCommentContent = findViewById(R.id.tv_news_comment_content);
        mTvCommentDate = findViewById(R.id.tv_news_comment_date);
        mIvAvatar = findViewById(R.id.iv_news_comment_avatar);
        mRvCommentReplyList = findViewById(R.id.rv_child_comment_list);
        mTvCommentContent.setOnClickListener(mClickListener);
        mIvAvatar.setOnClickListener(mClickListener);

        mChildCommentAdapter = new FindDetailsChildCommentAdapter(itemView.getContext());
        mChildCommentAdapter.setOnItemClickListener(mOnItemClickListener);
        mRvCommentReplyList.setAdapter(mChildCommentAdapter);
    }

    public void setBind(FindCommentBean commentInfo) {
        mCommentBean = commentInfo;
        String userHead = commentInfo.getComUserHead();
        mGlideRequests.load(userHead == null ? R.drawable.ic_default_avatar : userHead)
                .thumbnail(0.2f)
                .centerCrop()
                .transform(new CircleCrop())
                .into(mIvAvatar);
        mTvUserName.setText(commentInfo.getComUserName());
        mTvCommentContent.setText(commentInfo.getComContent());
        mTvCommentDate.setText(commentInfo.mFormatDate);

        if (commentInfo.getComReplies() == null ||
                (commentInfo.getComReplies() != null && commentInfo.getComReplies().size() == 0)) {
            mRvCommentReplyList.setVisibility(View.GONE);
        } else {
            mRvCommentReplyList.setVisibility(View.VISIBLE);
            mChildCommentAdapter.setReplayCommentList(commentInfo.getComReplies());
        }
    }

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition(), mCommentBean, v);
            }
        }
    };
}
