package com.scj.beilu.app.ui.find.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/2/2
 * descriptin:编辑
 */
public class EditPhotoAdapter extends RecyclerView.Adapter<EditPhotoAdapter.EditPhotoHolder> {

    private List<Uri> mImagePath = null;
    private GlideRequests mGlideRequests;
    private OnItemClickListener mOnItemClickListener;
    private final int mMaxCount;
    private onNotifyListener mOnNotifyListener;

    public interface onNotifyListener {
        void onImageSize(int size);
    }

    public EditPhotoAdapter(onNotifyListener notifyListener, GlideRequests requests, int maxCount) {
        mOnNotifyListener = notifyListener;
        mGlideRequests = requests;
        this.mMaxCount = maxCount;
    }

    public void setImagePath(List<Uri> imagePath) {
        mImagePath = imagePath;
        if (mOnNotifyListener != null) {
            mOnNotifyListener.onImageSize(imagePath.size());
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EditPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_editephoto, parent, false);
        return new EditPhotoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EditPhotoHolder holder, int position) {
        int size = position + 1;
        if (mImagePath == null || ((mImagePath != null && getItemCount() == (size)))) {
            if (mImagePath.size() == mMaxCount) {
                holder.mIvDel.setVisibility(View.VISIBLE);
                mGlideRequests.load(mImagePath.get(position))
                        .optionalCenterCrop()
                        .into(holder.mIvImage);
            } else {
                holder.mIvDel.setVisibility(View.GONE);
                mGlideRequests.load(R.drawable.ic_add)
                        .optionalCenterCrop()
                        .into(holder.mIvImage);
            }
        } else {
            holder.mIvDel.setVisibility(View.VISIBLE);
            mGlideRequests.load(mImagePath.get(position))
                    .optionalCenterCrop()
                    .into(holder.mIvImage);
        }
    }

    @Override
    public int getItemCount() {
        int size = mImagePath.size();
        if (mImagePath == null) {
            return 1;
        } else if (size == mMaxCount) {
            return size;
        } else {
            return size + 1;
        }
    }


    class EditPhotoHolder extends BaseViewHolder {
        private ImageView mIvImage;
        private ImageView mIvDel;

        public EditPhotoHolder(View itemView) {
            super(itemView);
            mIvImage = findViewById(R.id.iv_publish_image);
            mIvDel = findViewById(R.id.iv_del_image);
            mIvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), null, v);
                    }
                }
            });
            mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener == null) return;
                    if (mImagePath == null || (mImagePath != null && getItemCount() == (getAdapterPosition() + 1)) && getItemCount() != 9) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), null, v);
                    } else {
                        if (mImagePath.size() == getAdapterPosition()) {
                            mOnItemClickListener.onItemClick(getAdapterPosition(), null, v);
                        } else {
                            Uri uri = mImagePath.get(getAdapterPosition());
                            mOnItemClickListener.onItemClick(getAdapterPosition(), uri, v);
                        }
                    }
                }
            });
        }
    }
}

