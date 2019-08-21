package com.scj.beilu.app.mvp.common.bean;

import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/2/26 14:49
 */
public abstract class PicPreviewBean implements Parcelable {
    public abstract String getOriginalPicPath();

    public abstract String getThumbnailPicPath();
}
