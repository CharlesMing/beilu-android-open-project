package com.scj.beilu.app.mvp.home.selller;

import android.content.Context;


import com.scj.beilu.app.mvp.base.TabPresenter;
import com.scj.beilu.app.mvp.base.TabShopPhotoPresent;
import com.scj.beilu.app.mvp.base.TabView;

/**
 * author:SunGuiLan
 * date:2019/2/18
 * descriptin:
 */
public class ShopPhotoManagerPre extends TabShopPhotoPresent<ShopPhotoManagerPre.ShopPhotoManagerView> {
    public ShopPhotoManagerPre(Context context) {
        super(context);
    }

    public interface ShopPhotoManagerView extends TabView {
    }
}
