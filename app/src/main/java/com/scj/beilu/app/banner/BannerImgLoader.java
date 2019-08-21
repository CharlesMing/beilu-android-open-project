package com.scj.beilu.app.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mx.pro.lib.banner.loader.ImageLoader;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/2/12 17:21
 */
public class BannerImgLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, View imageView) {
        ImageView image = imageView.findViewById(R.id.image_view);
        GlideApp.with(context).load(path)
                .dontAnimate()
                .into(image);
    }

    @Override
    public View createImageView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.banner_state_image, null, true);
        return inflate;
    }
}
