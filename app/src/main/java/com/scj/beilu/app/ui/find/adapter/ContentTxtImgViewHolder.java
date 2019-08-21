package com.scj.beilu.app.ui.find.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mx.pro.lib.album.internal.ui.widget.MediaGridInset;
import com.mx.pro.lib.album.internal.ui.widget.RoundedRectangleImageView;
import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/2/22 17:06
 */
class ContentTxtImgViewHolder extends BaseContentViewHolder {

    RoundedRectangleImageView mIvPic;

    RecyclerView mRvImgList;

    public ContentTxtImgViewHolder(Context context,View itemView, int mRestSize) {
        super(itemView);
        mRvImgList = findViewById(R.id.rv_item_find_pic);
        mIvPic = findViewById(R.id.iv_item_find_pic);
        MediaGridInset mediaGridInset = new MediaGridInset(3, mRestSize, false);
        GridLayoutManager gridImg = new GridLayoutManager(context, 3);
        mRvImgList.addItemDecoration(mediaGridInset);
        mRvImgList.setLayoutManager(gridImg);

    }
}
