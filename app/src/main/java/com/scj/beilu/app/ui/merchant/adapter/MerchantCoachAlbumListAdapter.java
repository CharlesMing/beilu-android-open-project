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
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoBean;
import com.scj.beilu.app.ui.merchant.MerchantCoachAlbumListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/16 17:27
 */
public class MerchantCoachAlbumListAdapter extends RecyclerView.Adapter<MerchantCoachAlbumListAdapter.CoachAlbumViewHolder> {

    private List<MerchantInfoCoachPicInfoBean> mPicInfoBeans;
    private GlideRequest<Drawable> mOriginal, mThumbnail;
    private OnItemClickListener<MerchantInfoCoachPicInfoBean> mOnItemClickListener;

    public MerchantCoachAlbumListAdapter(MerchantCoachAlbumListFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().optionalCenterCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().optionalCenterCrop();
    }

    public void setPicInfoBeans(List<MerchantInfoCoachPicInfoBean> picInfoBeans) {
        mPicInfoBeans = picInfoBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<MerchantInfoCoachPicInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CoachAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_coach_album, parent, false);
        return new CoachAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachAlbumViewHolder holder, int position) {
        try {
            MerchantInfoCoachPicInfoBean picInfoBean = mPicInfoBeans.get(position);

            mOriginal.load(picInfoBean.getCoachPicAddr())
                    .thumbnail(mThumbnail.load(picInfoBean.getCoachPicAddrZip()))
                    .into(holder.iv_merchant_coach_img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPicInfoBeans == null ? 0 : mPicInfoBeans.size();
    }

    public class CoachAlbumViewHolder extends BaseViewHolder {
        private ImageView iv_merchant_coach_img;

        public CoachAlbumViewHolder(View itemView) {
            super(itemView);
            iv_merchant_coach_img = findViewById(R.id.iv_merchant_coach_img);
            iv_merchant_coach_img.setOnClickListener(new View.OnClickListener() {
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
