package com.scj.beilu.app.mvp.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.store.entity.GoodsShoppingCarInfoBean;
import com.scj.beilu.app.mvp.store.model.ShoppingCarImpl;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/1 17:11
 */
public class ShoppingCartInfoPre extends ShoppingCartPre<ShoppingCartInfoPre.ShoppingCartInfoView> {

    private ShoppingCarImpl mShoppingCar;

    public ShoppingCartInfoPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mShoppingCar = new ShoppingCarImpl(builder);
    }


    public void getCartList() {
        onceViewAttached(new ViewAction<ShoppingCartInfoView>() {
            @Override
            public void run(@NonNull final ShoppingCartInfoView mvpView) {
                mShoppingCar.getCartListByToken()
                        .subscribe(new ObserverCallback<List<GoodsShoppingCarInfoBean>>(builder.build(mvpView)) {
                            @Override
                            public void onNext(List<GoodsShoppingCarInfoBean> goodsShoppingCarInfoBeans) {
                                mvpView.onCartListResult(goodsShoppingCarInfoBeans);
                            }
                        });
            }
        });
    }

    public void modifyGoodsNum(final int goodsNum, final int position) {
        onceViewAttached(new ViewAction<ShoppingCartInfoView>() {
            @Override
            public void run(@NonNull final ShoppingCartInfoView mvpView) {
                mShoppingCar.modifyCartNum(goodsNum, position)
                        .subscribe(new ObserverCallback<Integer>(builder.build(mvpView)) {
                            @Override
                            public void onNext(Integer integer) {
                                mvpView.modifyResult(position);
                            }
                        });
            }
        });
    }

    public void setSelectAll(final boolean isAll) {
        onceViewAttached(new ViewAction<ShoppingCartInfoView>() {
            @Override
            public void run(@NonNull final ShoppingCartInfoView mvpView) {
                mShoppingCar.setSelectAll(isAll)
                        .subscribe(new ObserverCallback<Integer>(builder.build(mvpView)) {
                            @Override
                            public void onNext(Integer integer) {
                                mvpView.onSelectCountResult(integer);
                            }
                        });
            }
        });
    }

    public void setSelectSingle(final int position) {
        onceViewAttached(new ViewAction<ShoppingCartInfoView>() {
            @Override
            public void run(@NonNull final ShoppingCartInfoView mvpView) {
                mShoppingCar.setSelectSingle(position)
                        .subscribe(new ObserverCallback<Integer>(builder.build(mvpView)) {
                            @Override
                            public void onNext(Integer integer) {
                                mvpView.onSelectCountResult(integer);
                            }
                        });
            }
        });
    }

    public void delCart() {
        onceViewAttached(new ViewAction<ShoppingCartInfoView>() {
            @Override
            public void run(@NonNull final ShoppingCartInfoView mvpView) {
                mShoppingCar.delCart()
                        .subscribe(new ObserverCallback<Integer>(builder.build(mvpView)) {
                            @Override
                            public void onNext(Integer integer) {
                                mvpView.onSelectCountResult(integer);
                            }
                        });
            }
        });
    }

    public interface ShoppingCartInfoView extends ShoppingCartPre.ShoppingCartView {

        void onCartListResult(List<GoodsShoppingCarInfoBean> cartList);

        void modifyResult(int position);

        void onSelectCountResult(Integer count);

    }
}
