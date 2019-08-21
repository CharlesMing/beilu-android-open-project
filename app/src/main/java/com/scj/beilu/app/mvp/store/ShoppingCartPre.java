package com.scj.beilu.app.mvp.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.store.model.IShoppingCar;
import com.scj.beilu.app.mvp.store.model.ShoppingCarImpl;

/**
 * @author Mingxun
 * @time on 2019/4/1 12:38
 */
public class ShoppingCartPre<View extends ShoppingCartPre.ShoppingCartView> extends BaseMvpPresenter<View> {
    protected IShoppingCar mShoppingCar;
    protected final LoadDelegate.Builder builder;

    public ShoppingCartPre(Context context, @ShowConfig.loading int loadType, @ShowConfig.error int loadErrType) {
        super(context, loadType, loadErrType);

        builder = new LoadDelegate.Builder()
                .setContext(mContext)
                .setCompositeDisposable(mCompositeDisposable);
        mShoppingCar = new ShoppingCarImpl(builder);
    }

    public void getCartCount() {
        onceViewAttached(new ViewAction<View>() {
            @Override
            public void run(@NonNull final View mvpView) {
                mShoppingCar.getCartCount()
                        .subscribe(new ObserverCallback<Integer>(builder.build(mvpView)) {
                            @Override
                            public void onNext(Integer integer) {
                                mvpView.onCartCount(integer);
                            }
                        });
            }
        });
    }

    public interface ShoppingCartView extends MvpView {
        void onCartCount(int count);
    }
}
