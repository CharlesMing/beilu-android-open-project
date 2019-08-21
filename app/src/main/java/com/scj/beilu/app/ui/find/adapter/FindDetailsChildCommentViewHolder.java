package com.scj.beilu.app.ui.find.adapter;

import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/2/28 11:58
 */
class FindDetailsChildCommentViewHolder extends BaseViewHolder {
    TextView mTvReplyCommentContent;

    public FindDetailsChildCommentViewHolder(View itemView) {
        super(itemView);
        mTvReplyCommentContent = findViewById(R.id.tv_comment_reply);
    }
}
