package com.mx.pro.lib.banner.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mx.pro.lib.R;


public abstract class ImageLoader implements ImageLoaderInterface<View> {

    @Override
    public View  createImageView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_image, null, true);
        return inflate;
    }

}
