package com.scj.beilu.app.ui.mine.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoBean;
import com.scj.beilu.app.ui.mine.MineMsgFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:55
 */
public class MsgLikeListAdapter extends RecyclerView.Adapter<MsgLikeListAdapter.ReplyViewHolder> {

    private List<MsgLikeInfoBean> mMsgLikeInfoBeans;
    private GlideRequest<Drawable> mOriginal;
    private GlideRequest<Drawable> mThumbnail;
    private OnItemClickListener mOnItemClickListener;
    private final String mTipContent;

    public MsgLikeListAdapter(MineMsgFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().circleCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().circleCrop();
        mTipContent = frag.getString(R.string.txt_item_comment_tip);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setMsgLikeInfoBeans(List<MsgLikeInfoBean> msgLikeInfoBeans) {
        mMsgLikeInfoBeans = msgLikeInfoBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_reply_content, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        try {
            MsgLikeInfoBean likeInfoBean = mMsgLikeInfoBeans.get(position);
            mOriginal.load(likeInfoBean.getUserHead())
                    .thumbnail(mThumbnail.load(likeInfoBean.getUserHeadZip()))
                    .into(holder.mIvHeader);
            holder.mTvUserName.setText(likeInfoBean.getUserName());
            holder.mTvTip.setText(String.format(mTipContent, likeInfoBean.getLikeContent(), likeInfoBean.mFormatDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mMsgLikeInfoBeans == null ? 0 : mMsgLikeInfoBeans.size();
    }

    public class ReplyViewHolder extends BaseViewHolder {
        private ImageView mIvHeader;
        private TextView mTvUserName;
        private TextView mTvTip;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            mIvHeader = findViewById(R.id.iv_comment_header);
            mTvUserName = findViewById(R.id.tv_comment_user_name);
            mTvTip = findViewById(R.id.tv_reply_tip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mMsgLikeInfoBeans.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
