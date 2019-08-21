package com.scj.beilu.app.ui.act.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/26 15:21
 */
public class ImageSelectedListAdapter extends RecyclerView.Adapter<ImageSelectedListAdapter.ImageViewHolder> {

    private List<Boolean> mImageSelectedList;

    public void setImageSelectedList(List<Boolean> imageSelectedList) {
        mImageSelectedList = imageSelectedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img_selected, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.mImageView.setSelected(mImageSelectedList.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageSelectedList == null ? 0 : mImageSelectedList.size();
    }

    class ImageViewHolder extends BaseViewHolder {
        private ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = findViewById(R.id.iv_image_selected);
        }
    }
}
