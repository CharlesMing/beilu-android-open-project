package com.scj.beilu.app.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.mx.pro.lib.banner.loader.ImageLoader;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/1/24 15:01
 */
public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, View imageView) {
        ImageView image = imageView.findViewById(R.id.banner_image);
        GlideApp.with(context).load(path)
                .dontAnimate()
                .into(image);
    }
}
