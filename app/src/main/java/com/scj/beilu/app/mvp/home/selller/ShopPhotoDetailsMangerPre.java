package com.scj.beilu.app.mvp.home.selller;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.mvp.base.TabShopPhotoDetailsPresent;
import com.scj.beilu.app.mvp.base.TabShopPhotoPresent;
import com.scj.beilu.app.mvp.base.TabView;

/**
 * author:SunGuiLan
 * date:2019/2/19
 * descriptin:
 */
public class ShopPhotoDetailsMangerPre extends TabShopPhotoDetailsPresent<ShopPhotoDetailsMangerPre.ShopPhotoDetailsManagerView> {
    public ShopPhotoDetailsMangerPre(Context context) {
        super(context);
    }

    public interface ShopPhotoDetailsManagerView extends TabView {

    }
}
