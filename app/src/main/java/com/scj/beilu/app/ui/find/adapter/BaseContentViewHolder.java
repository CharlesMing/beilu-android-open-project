package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;

/**
 * @author Mingxun
 * @time on 2019/2/22 17:06
 */
class BaseContentViewHolder extends BaseViewHolder {

    ImageView mIvAvatar;
    TextView mTvUserName, mTvFindContent, mTvAttention;
    TextView mTvPraiseCount, mTvCommentCount, mTvShare;
    LinearLayout mLlPraise, mLlComment, mLlShare;
    private OnItemClickListener<FindInfoBean> mOnItemClickListener;

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

    public void onBind(GlideRequests requests, final FindInfoBean infoFind) {
        GlideRequest<Drawable> glideRequest =
                requests.asDrawable().transform(new CircleCrop()).centerCrop();
        glideRequest
                .load(infoFind.getUserHead())
                .error(R.drawable.ic_default_avatar)
                .transform(new CircleCrop())
                .into(mIvAvatar);

        mTvUserName.setText(infoFind.getUserNickName());

        mTvAttention.setSelected(infoFind.getIsFocus() == 1);
        mTvAttention.setText(infoFind.getIsFocus() == 1 ? "已关注" : "+ 关注");
        mTvAttention.setVisibility(infoFind.getIsOwn() == 0 ? View.VISIBLE : View.GONE);

        if (infoFind.getDynamicDec() == null) {
            mTvFindContent.setVisibility(View.GONE);
        } else {
            mTvFindContent.setVisibility(View.VISIBLE);
            mTvFindContent.setText(infoFind.getDynamicDec());
        }

        mTvPraiseCount.setSelected(infoFind.getIsLike() == 1);
        mTvPraiseCount.setText(String.valueOf(infoFind.getDynamicLikeCount()));
        mTvCommentCount.setText(String.valueOf(infoFind.getCommentCount()));
        mTvShare.setText(String.valueOf(infoFind.getDynamicShareCount()));

        mLlPraise.setOnClickListener(mOnClickListener(infoFind));
        mLlComment.setOnClickListener(mOnClickListener(infoFind));
        mLlShare.setOnClickListener(mOnClickListener(infoFind));
        mTvAttention.setOnClickListener(mOnClickListener(infoFind));
        mIvAvatar.setOnClickListener(mOnClickListener(infoFind));
        mTvFindContent.setOnClickListener(mOnClickListener(infoFind));
    }

    public void setOnItemClickListener(OnItemClickListener<FindInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private View.OnClickListener mOnClickListener(final FindInfoBean findInfo) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), findInfo, v);
                }
            }
        };
    }
}
