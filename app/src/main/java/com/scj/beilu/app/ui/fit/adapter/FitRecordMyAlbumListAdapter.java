package com.scj.beilu.app.ui.fit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.util.TimeUtil;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/14 21:21
 */
public class FitRecordMyAlbumListAdapter extends RecyclerView.Adapter<FitRecordMyAlbumListAdapter.AlbumViewHolder> {
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    private List<FitRecordImgInfoBean> mFitRecordImgInfoBeans;
    private int mOverrideSize;
    private OnItemClickListener<FitRecordImgInfoBean> mOnItemClickListener;

    public FitRecordMyAlbumListAdapter(GlideRequests glideRequests, Context context) {
        mOriginalRequest = glideRequests.asDrawable().centerCrop();
        mThumbnailRequest = glideRequests.asDrawable().centerCrop();
        mOverrideSize = context.getResources().getDimensionPixelSize(R.dimen.grid_expected_size);
    }

    public void setFitRecordImgInfoBeans(List<FitRecordImgInfoBean> fitRecordImgInfoBeans) {
        mFitRecordImgInfoBeans = fitRecordImgInfoBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<FitRecordImgInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fit_record_my_album, parent, false);
        view.post(new Runnable() {
            @Override
            public void run() {
                if (view.getWidth() != 0) {
                    mOverrideSize = view.getWidth();
                }
            }
        });
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        FitRecordImgInfoBean imgInfoBean = mFitRecordImgInfoBeans.get(position);
        mOriginalRequest.load(imgInfoBean.getPicOrgAddr())
                .override(mOverrideSize)
                .thumbnail(mThumbnailRequest.load(imgInfoBean.getPicComAddr()).override(mOverrideSize))
                .into(holder.mIvPhoto);
        if (imgInfoBean.isShow()) {
            holder.mIvSelect.setVisibility(View.VISIBLE);
            holder.mIvSelect.setSelected(imgInfoBean.isSelected());
        } else {
            holder.mIvSelect.setVisibility(View.GONE);
        }
        holder.mTvPhotoDate.setText(TimeUtil.getDate(imgInfoBean.getRecordDate(), "yyyy-MM-dd"));
    }

    @Override
    public int getItemCount() {
        return mFitRecordImgInfoBeans == null ? 0 : mFitRecordImgInfoBeans.size();
    }

    class AlbumViewHolder extends BaseViewHolder {
        private ImageView mIvPhoto;
        private ImageView mIvSelect;
        private TextView mTvPhotoDate;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            mIvPhoto = findViewById(R.id.iv_fit_record_my_album);
            mTvPhotoDate = findViewById(R.id.tv_fit_record_my_album_date);
            mIvSelect = findViewById(R.id.iv_album_selector);
            mIvSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FitRecordImgInfoBean imgInfoBean = mFitRecordImgInfoBeans.get(getAdapterPosition());
                    if (mOnItemClickListener != null && imgInfoBean != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), imgInfoBean, v);
                    }
                }
            });
            mIvPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FitRecordImgInfoBean imgInfoBean = mFitRecordImgInfoBeans.get(getAdapterPosition());
                    if (mOnItemClickListener != null && imgInfoBean != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), imgInfoBean, v);
                    }
                }
            });
        }
    }

}
