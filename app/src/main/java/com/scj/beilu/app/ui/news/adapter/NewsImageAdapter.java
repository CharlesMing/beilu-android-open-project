package com.scj.beilu.app.ui.news.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.widget.RectImageView;

/**
 * @author Mingxun
 * @time on 2019/1/24 18:10
 */
class NewsImageAdapter extends RecyclerView.Adapter<NewsImageAdapter.ImageViewHolder> {
    private String[] mNewsImgs;

    public NewsImageAdapter(String[] newsImgs) {
        mNewsImgs = newsImgs;
    }



    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_img, parent, false);
        return new ImageViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GlideApp.with(holder.mIvImg.getContext())
                .load(mNewsImgs[position])
                .into(holder.mIvImg);
    }

    @Override
    public int getItemCount() {
        return mNewsImgs == null ? 0 : mNewsImgs.length;
    }

    class ImageViewHolder extends BaseViewHolder {
        private RectImageView mIvImg;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mIvImg = findViewById(R.id.iv_find_img);
        }
    }
}
