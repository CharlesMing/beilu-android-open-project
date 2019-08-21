package com.scj.beilu.app.mvp.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoResultBean;
import com.scj.beilu.app.mvp.store.model.GoodsInfoImpl;
import com.scj.beilu.app.mvp.store.model.IGoodsInfo;

/**
 * @author Mingxun
 * @time on 2019/3/28 13:37
 */
public class GoodsDetailPre extends ShoppingCartPre<GoodsDetailPre.GoodsDetailView> {

    private IGoodsInfo mGoodsInfo;


    public GoodsDetailPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mGoodsInfo = new GoodsInfoImpl(mBuilder);
    }

    public void getGoodsInfoById(final int productId) {
        onceViewAttached(new ViewAction<GoodsDetailView>() {
            @Override
            public void run(@NonNull final GoodsDetailView mvpView) {
                mGoodsInfo.getGoodsInfoById(productId)
                        .subscribe(new BaseResponseCallback<GoodsInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(GoodsInfoResultBean goodsInfoResultBean) {
                                mvpView.onGoodsInfoResult(goodsInfoResultBean.getProductInfo());
                            }
                        });
            }
        });
    }

    public void setCollect(final int productId) {
        onceViewAttached(new ViewAction<GoodsDetailView>() {
            @Override
            public void run(@NonNull final GoodsDetailView mvpView) {
                mGoodsInfo.setCollect(productId)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onCollectResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public void addToCart(final GoodsInfoBean goodsInfo, final int goodsNum, final String goodsSpecification) {
        onceViewAttached(new ViewAction<GoodsDetailView>() {
            @Override
            public void run(@NonNull final GoodsDetailView mvpView) {
                mShoppingCar.addCart(goodsInfo, goodsNum, goodsSpecification)
                        .subscribe(new ObserverCallback<Integer>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(Integer integer) {
                                mvpView.onCartCount(integer);
                            }
                        });
            }
        });
    }


    public interface GoodsDetailView extends ShoppingCartPre.ShoppingCartView {
        void onGoodsInfoResult(GoodsInfoBean goodsInfo);

        void onCollectResult(ResultMsgBean result);

    }

}
