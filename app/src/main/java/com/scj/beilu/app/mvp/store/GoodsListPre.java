package com.scj.beilu.app.mvp.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListResultBean;
import com.scj.beilu.app.mvp.store.model.GoodsInfoImpl;
import com.scj.beilu.app.mvp.store.model.IGoodsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/3 16:15
 */
public class GoodsListPre extends BaseMvpPresenter<GoodsListPre.GoodsListView> {

    private IGoodsInfo mGoodsInfo;
    private final List<GoodsInfoBean> mGoodsInfoList = new ArrayList<>();

    public GoodsListPre(Context context) {
        super(context, ShowConfig.LOADING_STATE, ShowConfig.ERROR_TOAST);
        mGoodsInfo = new GoodsInfoImpl(mBuilder);
    }

    public void getGoodsList(final int productCateId, final int currentPage) {
        onceViewAttached(new ViewAction<GoodsListView>() {
            @Override
            public void run(@NonNull final GoodsListView mvpView) {
                mGoodsInfo.getGoodsListByCategoryId(productCateId, currentPage)
                 .subscribe(new BaseResponseCallback<GoodsListResultBean>(mBuilder.build(mvpView)) {
                     @Override
                     public void onRequestResult(GoodsListResultBean goodsListResultBean) {
                         try {

                             if (currentPage == 0) {
                                 mGoodsInfoList.clear();
                             }
                             List<GoodsInfoBean> list = goodsListResultBean.getPage().getList();

                             mGoodsInfoList.addAll(list);
                             mvpView.onCheckLoadMore(list);
                             mvpView.onGoodsList(mGoodsInfoList);

                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 });
            }
        });
    }

    public interface GoodsListView extends BaseCheckArrayView {
        void onGoodsList(List<GoodsInfoBean> goodsInfoBeanList);
    }

}
