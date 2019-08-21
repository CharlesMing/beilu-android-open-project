package com.scj.beilu.app.ui.mine.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/2/22 17:06
 */
class BaseContentViewHolder extends BaseViewHolder {

    ImageView mIvAvatar;
    TextView mTvUserName, mTvFindContent, mTvAttention;
    TextView mTvPraiseCount, mTvCommentCount, mTvShare;
    LinearLayout mLlPraise, mLlComment, mLlShare;

    public BaseContentViewHolder(View itemView) {
        super(itemView);
        mIvAvatar = findViewById(R.id.iv_item_find_avatar);
        mTvUserName = findViewById(R.id.tv_item_find_user_name);
        mTvFindContent = findViewById(R.id.tv_item_find_txt);
        mTvAttention = findViewById(R.id.tv_item_find_attention);
        mTvPraiseCount = findViewById(R.id.tv_item_find_praise);
        mTvCommentCount = findViewById(R.id.tv_item_find_comment);
        mTvShare = findViewById(R.id.tv_item_find_share);
        mLlPraise = findViewById(R.id.ll_item_find_praise);
        mLlComment = findViewById(R.id.ll_item_find_comment);
        mLlShare = findViewById(R.id.ll_item_find_share);

    }


}
