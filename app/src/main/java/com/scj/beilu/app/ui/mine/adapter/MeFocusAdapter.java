package com.scj.beilu.app.ui.mine.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;

import java.util.List;

/**
 * author: SunGuiLan
 * date:2019/2/1
 * descriptin:查找更多用户
 */
public class MeFocusAdapter extends RecyclerView.Adapter<MeFocusAdapter.FindMoreUserViewHolder> {
    private List<FocusUserInfoBean> mFocusInfoList;
    private GlideRequests mGlideRequests;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<FocusUserInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MeFocusAdapter(GlideRequests glideRequests) {
        mGlideRequests = glideRequests;
    }

    @NonNull
    @Override
    public FindMoreUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_focus_user, parent, false);
        return new MeFocusAdapter.FindMoreUserViewHolder(view);
    }

    public void setFocusInfoList(List<FocusUserInfoBean> focusInfoList) {
        mFocusInfoList = focusInfoList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull FindMoreUserViewHolder holder, int position) {
        FocusUserInfoBean mFocusUserInfoBean = mFocusInfoList.get(position);
        holder.mTvUserName.setText(mFocusUserInfoBean.getUserNickName());
        if (mFocusUserInfoBean.getIsFans() == 1 && mFocusUserInfoBean.getIsFocus() == 1) {
            holder.mTvAttention.setSelected(true);
            holder.mTvAttention.setText("互相关注");
        } else if (mFocusUserInfoBean.getIsFocus() == 1) {
            holder.mTvAttention.setSelected(true);
            holder.mTvAttention.setText("已关注");
        } else {
            holder.mTvAttention.setSelected(false);
            holder.mTvAttention.setText("+ 关注");
        }

        mGlideRequests.
                load(mFocusUserInfoBean.getUserCompressionHead())
                .centerCrop()
                .transform(new CircleCrop())
                .into(holder.mIvUserAvatar);
    }

    @Override
    public int getItemCount() {
        return mFocusInfoList == null ? 0 : mFocusInfoList.size();
    }

    class FindMoreUserViewHolder extends BaseViewHolder {
        private ImageView mIvUserAvatar;
        private TextView mTvAttention;
        private TextView mTvUserName;

        public FindMoreUserViewHolder(View itemView) {
            super(itemView);
            mTvAttention = findViewById(R.id.tv_item_attention);
            mTvUserName = findViewById(R.id.tv_user_name);
            mIvUserAvatar = findViewById(R.id.iv_user_avatar);
            mTvAttention.setOnClickListener(mOnClickListener);
            mIvUserAvatar.setOnClickListener(mOnClickListener);
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null && mFocusInfoList != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), mFocusInfoList.get(getAdapterPosition()), v);
                }
            }
        };
    }
}
