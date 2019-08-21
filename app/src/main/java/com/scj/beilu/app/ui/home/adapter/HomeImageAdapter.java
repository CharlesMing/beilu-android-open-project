package com.scj.beilu.app.ui.home.adapter;

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
class HomeImageAdapter extends RecyclerView.Adapter<HomeImageAdapter.ImageViewHolder> {
    String[] imgs = {"https://imgsa.baidu.com/news/q%3D100/sign=57cb26cb4c34970a4173142fa5cbd1c0/7a899e510fb30f24bdbf02e7c595d143ad4b0329.jpg",
            "https://imgsa.baidu.com/news/q%3D100/sign=c32a60d741086e066ca83b4b32097b5a/d8f9d72a6059252daf99e1e2399b033b5ab5b94e.jpg",
//            "https://imgsa.baidu.com/news/q%3D100/sign=3d0e4ad457afa40f3ac6cadd9b66038c/1c950a7b02087bf4442faa0fffd3572c10dfcf11.jpg",
//            "https://imgsa.baidu.com/news/q%3D100/sign=3d0e4ad457afa40f3ac6cadd9b66038c/1c950a7b02087bf4442faa0fffd3572c10dfcf11.jpg",
//            "https://imgsa.baidu.com/news/q%3D100/sign=57cb26cb4c34970a4173142fa5cbd1c0/7a899e510fb30f24bdbf02e7c595d143ad4b0329.jpg",
            "https://imgsa.baidu.com/news/q%3D100/sign=57cb26cb4c34970a4173142fa5cbd1c0/7a899e510fb30f24bdbf02e7c595d143ad4b0329.jpg"};

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_img, parent, false);
        return new ImageViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GlideApp.with(holder.mIvImg.getContext())
                .load(imgs[position])
                .into(holder.mIvImg);
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    class ImageViewHolder extends BaseViewHolder {
        private RectImageView mIvImg;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mIvImg = findViewById(R.id.iv_find_img);
        }
    }
}
