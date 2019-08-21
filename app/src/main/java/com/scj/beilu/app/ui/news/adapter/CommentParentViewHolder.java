package com.scj.beilu.app.ui.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/2/16 14:54
 */
class CommentParentViewHolder extends BaseViewHolder implements View.OnClickListener {

    TextView mTvUserName, mTvCommentContent,
            mTvCommentDate;
    ImageView mIvAvatar;
    RecyclerView mRvCommentReplyList;


    public CommentParentViewHolder(View itemView) {
        super(itemView);
        mTvUserName = findViewById(R.id.tv_news_user_name);
        mTvCommentContent = findViewById(R.id.tv_news_comment_content);
        mTvCommentDate = findViewById(R.id.tv_news_comment_date);
        mIvAvatar = findViewById(R.id.iv_news_comment_avatar);
        mRvCommentReplyList = findViewById(R.id.rv_child_comment_list);
        mTvCommentContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
