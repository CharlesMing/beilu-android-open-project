package com.scj.beilu.app.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.scj.beilu.app.base.BaseService;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.ui.find.FindPublishFrag;
import com.scj.beilu.app.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/2/21 10:47
 */
public class UploadService extends BaseService {
    private IFindInfo mIFind;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mIFind == null) {
            mIFind = new FindImpl(mBuilder);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);
        Intent fileData = intent.getParcelableExtra(FindPublishFrag.FILE_DATA);
        List<String> filePathList = AlbumManager.obtainPathResult(fileData);
        if (filePathList == null) {
            filePathList = new ArrayList<>();
        }
        boolean ofImage = intent.getBooleanExtra(FindPublishFrag.MIME_TYPE, false);
        String description = intent.getStringExtra(FindPublishFrag.DESCRIPTION);
        mIFind.createDynamicParams(filePathList, ofImage, description)
                .flatMap((Function<ResultMsgBean, ObservableSource<FindListBean>>) resultMsgBean -> mIFind.getMyFindInfoList())
                .subscribe(new ObserverCallback<FindListBean>(mBuilder.build()) {
                    @Override
                    public void onNext(FindListBean findListBean) {
                        EventBus.getDefault().post(findListBean);
                        ToastUtils.showToast(getBaseContext(), "发布成功");
                        stopService(intent);
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        super.onError(errorMsg);
                        ToastUtils.showToast(getBaseContext(), errorMsg);
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
