package com.scj.beilu.app.mvp.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.store.bean.GoodsBannerInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsBannerResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListResultBean;
import com.scj.beilu.app.mvp.store.model.GoodsInfoImpl;
import com.scj.beilu.app.mvp.store.model.IGoodsInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:25
 */
public class StorePre extends ShoppingCartPre<StorePre.StoreView> {

    private IGoodsInfo mGoodsInfo;


    public StorePre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mGoodsInfo = new GoodsInfoImpl(mBuilder);
    }

    public void getCurrentPageData() {
        onceViewAttached(new ViewAction<StoreView>() {
            @Override
            public void run(@NonNull final StoreView mvpView) {
                Observable.merge(mGoodsInfo.getBannerList(), mGoodsInfo.getGoodsCategoryList(),
                        mGoodsInfo.getGoodsList(0))
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                try {
                                    if (resultMsgBean instanceof GoodsBannerResultBean) {
                                        GoodsBannerResultBean bannerResultBean = (GoodsBannerResultBean) resultMsgBean;
                                        mvpView.onBannerList(bannerResultBean.getBanner());
                                    } else if (resultMsgBean instanceof GoodsCategoryListBean) {
                                        GoodsCategoryListBean goodsCategoryList = (GoodsCategoryListBean) resultMsgBean;
                                        mvpView.onCategoryList((ArrayList<GoodsCategoryInfoBean>) goodsCategoryList.getCates());
                                    } else if (resultMsgBean instanceof GoodsListResultBean) {
                                        GoodsListResultBean goodsList = (GoodsListResultBean) resultMsgBean;
                                        mvpView.onGoodsList(goodsList.getPage().getList());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface StoreView extends ShoppingCartPre.ShoppingCartView {
        void onBannerList(List<GoodsBannerInfoBean> bannerList) throws Exception;

        void onCategoryList(ArrayList<GoodsCategoryInfoBean> categoryList) throws Exception;

        void onGoodsList(List<GoodsInfoBean> goodsList) throws Exception;

    }

}
