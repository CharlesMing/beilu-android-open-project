package com.mx.pro.lib.mvp.delegate;

import android.content.Context;
import android.content.SharedPreferences;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ErrorHandlingCallAdapterFactory;
import com.mx.pro.lib.mvp.network.config.AppConfig;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.mvp.network.gson.CustomGsonConverterFactory;
import com.mx.pro.lib.mvp.network.observer.ObserveOnMainCallAdapterFactory;
import com.mx.pro.lib.mvp.network.vlog.LogInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author Mingxun
 * @time on 2019/2/11 17:58
 */
public class LoadDelegate {

    public final MvpView mMvpView;
    public final int mLoadType;
    public final int mLoadErrType;
    public final CompositeDisposable mCompositeDisposable;
    public final Context context;
    protected final Builder mBuilder;

    private static final int TIMEOUT = 5 * 12;

    public LoadDelegate(Builder builder) {
        mBuilder = builder;
        this.mMvpView = builder.mvpView;
        this.mLoadType = builder.loadType;
        this.mLoadErrType = builder.loadErrType;
        this.mCompositeDisposable = builder.mCompositeDisposable;
        this.context = builder.context;
    }


    protected <T> T create(Class<T> tClass) {
        if (!tClass.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        } else {
            return mBuilder.mRetrofit.create(tClass);
        }
    }

    protected <T> Observable<T> createObservableOnSubscribe(ObservableOnSubscribe<T> observable) {
        return Observable.create(observable)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mMvpView == null) return;
                        mMvpView.showLoading(mLoadType, true);
                    }
                });
    }

    protected <T> Observable<T> createObservable(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (mMvpView == null) return;
                        mMvpView.showLoading(mLoadType, true);
                    }
                });
    }

    protected static final String VAL_TOKEN = "token";

    /**
     * 是否需要跳转至登录页面
     */
    public Observable<Map<String, String>> getHeader() {
        ObservableOnSubscribe<Map<String, String>> check = new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                Map<String, String> header = new HashMap<>();
                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE);
                String token = preferences.getString(AppConfig.USER_TOKEN, null);
                if (token != null) {
                    header.put(VAL_TOKEN, token);
                }
                emitter.onNext(header);
                emitter.onComplete();
            }
        };
        return createObservableOnSubscribe(check);
    }


    /**
     * config loading code / error code / request cancel
     */
    public static final class Builder {
        MvpView mvpView;
        int loadType = ShowConfig.NONE;
        int loadErrType = ShowConfig.NONE;
        CompositeDisposable mCompositeDisposable;
        Context context;
        Retrofit mRetrofit;

        public Builder() {
            initConfig();
        }

        public Builder setLoadType(@ShowConfig.loading int loadType) {
            this.loadType = loadType;
            return this;
        }

        public Builder setLoadErrType(@ShowConfig.error int loadErrType) {
            this.loadErrType = loadErrType;
            return this;
        }

        public Builder setCompositeDisposable(CompositeDisposable compositeDisposable) {
            mCompositeDisposable = compositeDisposable;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public LoadDelegate build(MvpView mvpView) {
            this.mvpView = mvpView;
            return new LoadDelegate(this);
        }

        public LoadDelegate build() {
            return new LoadDelegate(this);
        }

        /**
         * 初始化网络请求配置
         */
        private void initConfig() {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)//retry
                    .addInterceptor(new LogInterceptor(context))//show log
                    .build();


            Scheduler observeOn = Schedulers.computation();
            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(new ErrorHandlingCallAdapterFactory())//use call
                    .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory(observeOn))//use rxAndroid
                    .addConverterFactory(CustomGsonConverterFactory.create())//use Gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//use RxAndroid
                    .baseUrl(BuildConfig.API_HOST)
                    .build();

        }
    }
}
