package com.scj.beilu.app.ui.merchant.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoBean;
import com.scj.beilu.app.ui.merchant.MerchantListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/13 19:11
 */
public class MerchantListAdapter extends RecyclerView.Adapter<MerchantListAdapter.MerchantInfoViewHolder> {

    private List<MerchantInfoBean> mMerchantInfoList;
    private OnItemClickListener<MerchantInfoBean> mOnItemClickListener;
    private GlideRequest<Drawable> mOriginal;
    private GlideRequest<Drawable> mThumbnail;

    public MerchantListAdapter(MerchantListFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().optionalCenterCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().optionalCenterCrop();
    }

    public void setOnItemClickListener(OnItemClickListener<MerchantInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setMerchantInfoList(List<MerchantInfoBean> merchantInfoList) {
        mMerchantInfoList = merchantInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MerchantInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_content, parent, false);
        return new MerchantInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantInfoViewHolder holder, int position) {
        try {
            MerchantInfoBean merchantInfo = mMerchantInfoList.get(position);
            mOriginal.load(merchantInfo.getMerchantPicAddr())
                    .thumbnail(mThumbnail.load(merchantInfo.getMerchantPicAddrZip()))
                    .into(holder.mIvMerchantImg);
            holder.mTvMerchantName.setText(merchantInfo.getMerchantName());
            holder.mTvMerchantAddress.setText(merchantInfo.getMerchantAddr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mMerchantInfoList == null ? 0 : mMerchantInfoList.size();
    }

    public class MerchantInfoViewHolder extends BaseViewHolder {

        private ConstraintLayout mClMerchantParent;
        private ImageView mIvMerchantImg;
        private TextView mTvMerchantName, mTvMerchantAddress;

        public MerchantInfoViewHolder(View itemView) {
            super(itemView);
            mClMerchantParent = findViewById(R.id.cl_merchant_parent);
            mIvMerchantImg = findViewById(R.id.iv_merchant_img);
            mTvMerchantName = findViewById(R.id.tv_merchant_name);
            mTvMerchantAddress = findViewById(R.id.tv_merchant_address);
            mClMerchantParent.setOnClickListener(v -> {

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), mMerchantInfoList.get(getAdapterPosition()), v);
                }
            });
        }
    }
}
