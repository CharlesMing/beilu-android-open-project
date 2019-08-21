package com.scj.beilu.app.ui.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
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

    public void onBind(final int position, FindInfoBean videoModel) {
        try {
            GlideApp.with(mContext)
                    .load(videoModel.getDynamicVideoPic())
                    .centerCrop()
                    .into(imageView);

            if (imageView.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) imageView.getParent();
                viewGroup.removeView(imageView);
            }

            gsyVideoOptionBuilder
                    .setIsTouchWiget(false)
                    .setThumbImageView(imageView)
                    .setUrl(videoModel.getDynamicVideoAddr())
                    .setVideoTitle(videoModel.getDynamicDec())
                    .setCacheWithPlay(false)
                    .setRotateViewAuto(false)
                    .setLockLand(false)
                    .setPlayTag(FindInfoListAdapter.TAG)
                    .setShowFullAnimation(true)
                    .setAutoFullWithSize(false)
                    .setNeedLockFull(false)
                    .setPlayPosition(position)
                    .setVideoAllCallBack(new GSYSampleCallBack() {

                        @Override
                        public void onEnterFullscreen(String url, Object... objects) {
                            super.onEnterFullscreen(url, objects);
                            gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                        }
                    })
                    .build(gsyVideoPlayer);


            //增加title
            gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

            //设置返回键
            gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

            //设置全屏按键功能
            gsyVideoPlayer.getFullscreenButton().setOnClickListener(v -> resolveFullBtn(gsyVideoPlayer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(mContext, true, true);
    }
}
