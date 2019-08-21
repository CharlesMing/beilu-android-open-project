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
import com.scj.beilu.app.mvp.mine.bean.MeFansInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/14 21:13
 */
public class MeFansAdapter extends RecyclerView.Adapter<MeFansAdapter.FansViewHolder> {
    private List<MeFansInfoBean> mFansInfoList;
    private GlideRequests mGlideRequests;
    private OnItemClickListener<MeFansInfoBean> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<MeFansInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fans, parent, false);
        return new FansViewHolder(view);
    }

    public MeFansAdapter(GlideRequests glideRequests) {
        mGlideRequests = glideRequests;
    }

    public void setFansInfoList(List<MeFansInfoBean> fansInfoList) {
        mFansInfoList = fansInfoList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FansViewHolder holder, int position) {
        try {
            MeFansInfoBean mMeFansInfoBean = mFansInfoList.get(position);
            holder.mTvName.setText(mMeFansInfoBean.getUserNickName());
            if (mMeFansInfoBean.getIsFans() == 1 && mMeFansInfoBean.getIsFocus() == 1) {
                holder.mTvAttention.setSelected(true);
                holder.mTvAttention.setText("互相关注");
            } else if (mMeFansInfoBean.getIsFocus() == 1) {
                holder.mTvAttention.setSelected(true);
                holder.mTvAttention.setText("已关注");
            } else {
                holder.mTvAttention.setSelected(false);
                holder.mTvAttention.setText("+ 关注");
            }

            mGlideRequests
                    .load(mMeFansInfoBean.getUserCompressionHead())
                    .centerCrop()
                    .transform(new CircleCrop())
                    .into(holder.mIvAvatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mFansInfoList == null ? 0 : mFansInfoList.size();
    }

    class FansViewHolder extends BaseViewHolder {
        private TextView mTvAttention;
        private TextView mTvName;
        private ImageView mIvAvatar;

        public FansViewHolder(View itemView) {
            super(itemView);
            mTvName = findViewById(R.id.tv_name);
            mIvAvatar = findViewById(R.id.iv_user_avatar);
            mTvAttention = findViewById(R.id.tv_item_attention);
            mTvAttention.setOnClickListener(mOnClickListener);
            mIvAvatar.setOnClickListener(mOnClickListener);
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeFansInfoBean mMeFansInfoBean = mFansInfoList.get(getAdapterPosition());
                if (mOnItemClickListener != null && mMeFansInfoBean != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(),mMeFansInfoBean, v);
                }
            }
        };
    }
}
