package com.scj.beilu.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.mvp.wechat.bean.WeChatTokenBean;
import com.scj.beilu.app.mvp.wechat.model.IWeChat;
import com.scj.beilu.app.mvp.wechat.model.WeChatImpl;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WXEntryActivity extends Activity
        implements IWXAPIEventHandler {
    private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;
    private IWeChat mWeChat;
    private LoadDelegate.Builder mBuilder;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.WXAPPID, false);

        mBuilder = new LoadDelegate.Builder()
                .setLoadType(ShowConfig.LOADING_DIALOG)
                .setLoadErrType(ShowConfig.ERROR_TOAST)
                .setContext(this)
                .setCompositeDisposable(mCompositeDisposable);
        mWeChat = new WeChatImpl(mBuilder);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

        finish();
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            final String code = authResp.code;
            mWeChat.setCode(code)
                    .subscribe(new Observer<WeChatTokenBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(WeChatTokenBean tokenBean) {
                            EventBus.getDefault().post(tokenBean);
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            finish();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    EventBus.getDefault().post(resp);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    finish();
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}