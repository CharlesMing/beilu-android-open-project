package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;

import java.util.List;

/**
 * date:2019/1/30
 * :推荐关注的人
 */
public class ReAttentionAdapter extends RecyclerView.Adapter<ReAttentionAdapter.AttentionViewHolder> {
    private List<RecommendUserInfoBean> mUserList;
    private GlideRequest<Drawable> mOriginal;
    private GlideRequest<Drawable> mThumbnail;
    private OnItemClickListener mOnItemClickListener;

    public ReAttentionAdapter(SupportFragment frag) {
        mOriginal = GlideApp.with(frag).asDrawable().circleCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().circleCrop();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setUserList(List<RecommendUserInfoBean> userList) {
        mUserList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttentionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_attention, parent, false);
        return new AttentionViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AttentionViewHolder holder, int position) {
        try {
            RecommendUserInfoBean userInfo = mUserList.get(position);
            holder.mTvUserName.setText(userInfo.getUserNickName());
            mOriginal.load(userInfo.getUserOriginalHead())
                    .thumbnail(mThumbnail.load(userInfo.getUserCompressionHead()))
                    .into(holder.mIvAvatar);
            boolean attention = userInfo.isAttention();
            holder.mTvAttention.setText(attention ? "已关注" : "+ 关注");
            holder.mTvAttention.setSelected(attention);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    class AttentionViewHolder extends BaseViewHolder {
        private ImageView mIvAvatar;
        private TextView mTvUserName;
        private TextView mTvAttention;

        public AttentionViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = findViewById(R.id.iv_user_avatar);
            mTvUserName = findViewById(R.id.tv_user_info_name);
            mTvAttention = findViewById(R.id.tv_item_attention);
            mTvAttention.setOnClickListener(mOnClickListener);
            mIvAvatar.setOnClickListener(mOnClickListener);
        }

        private final View.OnClickListener mOnClickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(getAdapterPosition(), mUserList.get(getAdapterPosition()), v);
                        }
                    }
                };
    }
}
