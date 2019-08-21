package com.scj.beilu.app.ui.fit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/14 17:48
 */
public class FitRecordImgListAdapter extends RecyclerView.Adapter<FitRecordImgListAdapter.ImgListViewHolder> {

    private List<FitRecordImgInfoBean> mFitRecordImgInfoList;

    private GlideRequest<Drawable> mOriginalRequests;
    private GlideRequest<Drawable> mThumbnailRequest;
    private int mViewSize;
    private int mTotalCount;

    private OnItemClickListener<FitRecordImgInfoBean> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<FitRecordImgInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public FitRecordImgListAdapter(GlideRequests glideRequests, Context context) {
        mOriginalRequests = glideRequests.asDrawable().centerCrop();
        mThumbnailRequest = glideRequests.asDrawable().centerCrop();
        mViewSize = context.getResources().getDimensionPixelSize(R.dimen.grid_expected_size);
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    public void setFitRecordImgInfoList(List<FitRecordImgInfoBean> fitRecordImgInfoList) {
        mFitRecordImgInfoList = fitRecordImgInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImgListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fit_record_img, parent, false);
        view.post(new Runnable() {
            @Override
            public void run() {
                mViewSize = view.getWidth();
            }
        });
        return new ImgListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgListViewHolder holder, int position) {
        FitRecordImgInfoBean imgInfoBean = mFitRecordImgInfoList.get(position);

        mOriginalRequests.load(imgInfoBean.getPicOrgAddr())
                .override(mViewSize)
                .thumbnail(mThumbnailRequest.load(imgInfoBean.getPicComAddr()).override(mViewSize))
                .into(holder.mIvImg);

        //显示透明度，并且显示图片总数量
        int index = position + 1;
        if (getItemCount() != 1 && (index == getItemCount())) {
            holder.mLlCount.setVisibility(View.VISIBLE);
            holder.mTvImgCount.setText(String.valueOf(mTotalCount));
            holder.mTvImgCount.setVisibility(View.VISIBLE);
            holder.mIvTranslucence.setVisibility(View.VISIBLE);
        } else {
            holder.mLlCount.setVisibility(View.GONE);
            holder.mIvTranslucence.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mFitRecordImgInfoList == null) {
            return 0;
        } else if (mFitRecordImgInfoList.size() >= 3) {
            return 3;
        } else {
            return mFitRecordImgInfoList.size();
        }
    }

    class ImgListViewHolder extends BaseViewHolder {

        private ImageView mIvImg;
        private ImageView mIvTranslucence;
        private TextView mTvImgCount;
        private LinearLayout mLlCount;

        public ImgListViewHolder(View itemView) {
            super(itemView);
            mIvImg = findViewById(R.id.iv_fit_record_img);
            mIvTranslucence = findViewById(R.id.iv_fit_record_translucence);
            mTvImgCount = findViewById(R.id.tv_fit_record_img_count);
            mLlCount = findViewById(R.id.ll_fit_record_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FitRecordImgInfoBean imgInfoBean = mFitRecordImgInfoList.get(getAdapterPosition());
                    if (mOnItemClickListener != null && imgInfoBean != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), imgInfoBean,
                                ((getAdapterPosition() + 1) == getItemCount()) ? null : v);
                    }
                }
            });
        }
    }


}
