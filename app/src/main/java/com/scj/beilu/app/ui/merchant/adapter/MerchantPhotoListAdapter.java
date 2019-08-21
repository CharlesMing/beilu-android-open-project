package com.scj.beilu.app.ui.merchant.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoBean;
import com.scj.beilu.app.ui.merchant.MerchantPhotoListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 23:35
 */
public class MerchantPhotoListAdapter extends RecyclerView.Adapter<MerchantPhotoListAdapter.PhotoViewHolder> {

    private List<MerchantPicInfoBean> mPicInfoBeans;
    private GlideRequest<Drawable> mOriginal, mThumbnail;
    private OnItemClickListener<MerchantPicInfoBean> mOnItemClickListener;

    public MerchantPhotoListAdapter(MerchantPhotoListFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().optionalCenterCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().optionalCenterCrop();
    }


    public void setOnItemClickListener(OnItemClickListener<MerchantPicInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setPicInfoBeans(List<MerchantPicInfoBean> picInfoBeans) {
        mPicInfoBeans = picInfoBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_photo_content, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        try {
            MerchantPicInfoBean infoBean = mPicInfoBeans.get(position);
            mOriginal.load(infoBean.getRegionPicAddr())
                    .thumbnail(mThumbnail.load(infoBean.getRegionPicAddrZip()))
                    .into(holder.mIvPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPicInfoBeans == null ? 0 : mPicInfoBeans.size();
    }

    public class PhotoViewHolder extends BaseViewHolder {

        private ImageView mIvPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            mIvPhoto = findViewById(R.id.iv_merchant_image);
            mIvPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mPicInfoBeans.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }
}
