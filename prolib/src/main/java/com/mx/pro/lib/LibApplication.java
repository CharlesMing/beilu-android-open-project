package com.mx.pro.lib;

import android.support.multidex.MultiDexApplication;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author Mingxun
 * @time on 2019/4/1 13:40
 */
public class LibApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initUmeng();
    }

    private void initUmeng() {
//        UMConfigure.init(this, "5ca17f410cafb227fc001229", null, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        MobclickAgent.setCatchUncaughtExceptions(true);
        UMConfigure.setLogEnabled(false);
    }
}
