package com.mx.pro.lib.mvp.network.config;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.mx.pro.lib.mvp.MvpQueuingBasePresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;

import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Mingxun
 * @time on 2018/12/24 18:33
 */
public class BaseMvpPresenter<V extends MvpView> extends MvpQueuingBasePresenter<V> {

    protected Context mContext;

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected LoadDelegate.Builder mBuilder;


    public BaseMvpPresenter() {
        this(null);
    }

    public BaseMvpPresenter(Context context) {
        mContext = context;
        initLoad(ShowConfig.NONE, ShowConfig.ERROR_TOAST);
    }

    public BaseMvpPresenter(Context context, @ShowConfig.loading int loadType, @ShowConfig.error int loadErrType) {
        mContext = context;
        initLoad(loadType, loadErrType);
    }

    protected void initLoad(@ShowConfig.loading int loadType, @ShowConfig.error int loadErrType) {
        mBuilder = new LoadDelegate.Builder()
                .setLoadType(loadType)
                .setLoadErrType(loadErrType)
                .setContext(mContext)
                .setCompositeDisposable(mCompositeDisposable);
    }


    @Override
    public void detachView() {
        super.detachView();
        cancel();
    }

    public void cancel() {
        if (mCompositeDisposable == null) return;
        mCompositeDisposable.clear();
    }


    // 加密
    public static String Encrypt(String val) {
        try {
            byte[] raw = "chongqinsanchaji".getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(val.getBytes("utf-8"));
            return Base64.encodeToString(encrypted, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void checkUserIsLogin(final @IdRes int resId) {
        onceViewAttached(new ViewAction<V>() {
            @Override
            public void run(@NonNull final V mvpView) {
                mBuilder.build().getHeader()
                        .subscribe(new ObserverCallback<Map<String, String>>(mBuilder.build()) {
                            @Override
                            public void onNext(Map<String, String> token) {
                                mvpView.userIsLogin(token.size() > 0, resId);
                            }
                        });
            }
        });
    }

}
