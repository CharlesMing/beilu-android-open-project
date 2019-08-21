package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/2/21 15:37
 */
class FindListContentImageAdapter extends RecyclerView.Adapter<FindListContentImageAdapter.ImageViewHolder> {
    private ArrayList<FindImageBean> mFindImageList;

    private GlideRequest<Drawable> mThumbnailRequest;
    private GlideRequest<Drawable> mOriginalRequest;


    private OnGroupItemClickListener mOnGroupItemClickListener;
    private int mGroupPosition;

    public FindListContentImageAdapter(GlideRequests glideRequests, int groupPosition) {
        mThumbnailRequest = glideRequests.asDrawable().centerCrop();
        mOriginalRequest = glideRequests.asDrawable().centerCrop();
        mGroupPosition = groupPosition;
    }

    public void setOnGroupItemClickListener(OnGroupItemClickListener onGroupItemClickListener) {
        mOnGroupItemClickListener = onGroupItemClickListener;
    }

    public void setFindImageList(ArrayList<FindImageBean> findImageList) {
        mFindImageList = findImageList;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_find_pic_img, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        FindImageBean image = mFindImageList.get(position);
        mOriginalRequest
                .load(image.getDynamicPicAddr())
                .thumbnail(mThumbnailRequest.load(image.getDynamicPicZip()))
                .into(holder.mIvPic);
    }

    @Override
    public int getItemCount() {
        return mFindImageList == null ? 0 : mFindImageList.size();
    }

    public class ImageViewHolder extends BaseViewHolder {
        private ImageView mIvPic;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mIvPic = findViewById(R.id.iv_find_pic);
            mIvPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnGroupItemClickListener != null) {
                        mOnGroupItemClickListener.onItemClick(mGroupPosition, getAdapterPosition(), mFindImageList, v);
                    }
                }
            });
        }
    }

}
