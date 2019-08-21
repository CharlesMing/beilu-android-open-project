package com.scj.beilu.app.ui.news.adapter;

import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/2/16 14:54
 */
class CommentChildViewHolder extends BaseViewHolder {
     TextView mTvReplyCommentContent;
    public CommentChildViewHolder(View itemView) {
        super(itemView);
        mTvReplyCommentContent = findViewById(R.id.tv_comment_reply);
    }
}
