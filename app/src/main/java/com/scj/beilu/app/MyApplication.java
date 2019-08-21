package com.scj.beilu.app;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.mx.pro.lib.LibApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scj.beilu.app.dao.DaoMaster;
import com.scj.beilu.app.dao.DaoSession;
import com.scj.beilu.app.helper.MySQLiteOpenHelper;
import com.tencent.smtt.sdk.QbSdk;


/**
 * @author Mingxun
 * @time on 2018/12/21 15:09
 */
public class MyApplication extends LibApplication {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());

        initDao();

        initCloudChannel(this);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        initTBS();
    }

    private void initDao() {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "beilu_db", null);
        daoSession = new DaoMaster(helper.getWritableDatabase()).newSession();
        helper.onUpgrade(helper.getWritableDatabase(), 1, 7);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(final Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        PushServiceFactory.getCloudPushService().setDebug(true);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
            }
        });

    }

    private void initTBS() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
