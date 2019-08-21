package com.scj.beilu.app.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.scj.beilu.app.R;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @author Mingxun
 * @time on 2019/2/22 17:08
 */
class ContentVideoViewHolder extends BaseContentViewHolder {

    StandardGSYVideoPlayer gsyVideoPlayer;
    GSYVideoOptionBuilder gsyVideoOptionBuilder;
    ImageView imageView;
    private Context mContext;

    public ContentVideoViewHolder(Context context, View itemView) {
        super(itemView);
        gsyVideoPlayer = findViewById(R.id.video_item_player);
        mContext = context;
        imageView = new ImageView(mContext);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

}
