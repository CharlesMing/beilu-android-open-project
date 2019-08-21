package com.scj.beilu.app.ui.merchant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/16 14:17
 */
public class MerchantPicPreviewManagerListAdapter extends RecyclerView.Adapter<MerchantPicPreviewManagerListAdapter.PreviewTypeViewHolder> {

    private ArrayList<MerchantPicTypeBean> mMerchantPicTypeBeans;
    private OnItemClickListener mOnItemClickListener;

    public void setMerchantPicTypeBeans(ArrayList<MerchantPicTypeBean> merchantPicTypeBeans) {
        mMerchantPicTypeBeans = merchantPicTypeBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PreviewTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_pre_view_type, parent, false);
        return new PreviewTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewTypeViewHolder holder, int position) {
        try {
            MerchantPicTypeBean typeBean = mMerchantPicTypeBeans.get(position);
            holder.mTvName.setText(typeBean.getRegionName());
            holder.mTvName.setSelected(typeBean.isSelected());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mMerchantPicTypeBeans == null ? 0 : mMerchantPicTypeBeans.size();
    }

    public class PreviewTypeViewHolder extends BaseViewHolder {

        private TextView mTvName;

        public PreviewTypeViewHolder(View itemView) {
            super(itemView);
            mTvName = findViewById(R.id.tv_pre_view_type);
            mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mMerchantPicTypeBeans.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }
}
