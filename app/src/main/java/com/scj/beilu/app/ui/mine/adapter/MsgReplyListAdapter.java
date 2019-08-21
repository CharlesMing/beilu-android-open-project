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
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoBean;
import com.scj.beilu.app.ui.mine.MineMsgFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:55
 */
public class MsgReplyListAdapter extends RecyclerView.Adapter<MsgReplyListAdapter.ReplyViewHolder> {
    private List<MsgReplyInfoBean> mMsgReplyInfoBeans;
    private GlideRequest<Drawable> mOriginal;
    private GlideRequest<Drawable> mThumbnail;
    private OnItemClickListener mOnItemClickListener;
    private final String mTipContent;

    public MsgReplyListAdapter(MineMsgFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().circleCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().circleCrop();
        mTipContent = frag.getString(R.string.txt_item_reply_content);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setMsgReplyInfoBeans(List<MsgReplyInfoBean> msgReplyInfoBeans) {
        mMsgReplyInfoBeans = msgReplyInfoBeans;
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
            MsgReplyInfoBean msgReplyInfoBean = mMsgReplyInfoBeans.get(position);
            mOriginal.load(msgReplyInfoBean.getUserHead())
                    .thumbnail(mThumbnail.load(msgReplyInfoBean.getUserHeadZip()))
                    .into(holder.mIvHeader);
            holder.mTvUserName.setText(msgReplyInfoBean.getUserName());
            holder.mTvTip.setText(String.format(mTipContent, msgReplyInfoBean.getReplyContent(), msgReplyInfoBean.mFormatDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mMsgReplyInfoBeans == null ? 0 : mMsgReplyInfoBeans.size();
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
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mMsgReplyInfoBeans.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
