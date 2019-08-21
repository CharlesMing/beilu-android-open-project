package com.scj.beilu.app.mvp.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationListBean;
import com.scj.beilu.app.mvp.store.model.GoodsInfoImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/3/28 21:34
 */
public class GoodsSpecificationPre extends BaseMvpPresenter<GoodsSpecificationPre.GoodsSpecificationView> {

    private GoodsInfoImpl mGoodsInfo;
    private final List<GoodsSpecificationListBean> mGoodsSpecificationList;

    public GoodsSpecificationPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mGoodsSpecificationList = new ArrayList<>();
        mGoodsInfo = new GoodsInfoImpl(mBuilder);
    }

    public void getGoodsSpecification(final int productId) {
        onceViewAttached(new ViewAction<GoodsSpecificationView>() {
            @Override
            public void run(@NonNull final GoodsSpecificationView mvpView) {
                if (mGoodsSpecificationList.size() > 0) {
                    mvpView.onSpecificationList(mGoodsSpecificationList);
                } else {
                    mGoodsInfo.getSpecification(productId)
                            .subscribe(new ObserverCallback<List<GoodsSpecificationListBean>>(mBuilder.build(mvpView)) {
                                @Override
                                public void onNext(List<GoodsSpecificationListBean> groupList) {
                                    mGoodsSpecificationList.clear();
                                    mGoodsSpecificationList.addAll(groupList);
                                    mvpView.onSpecificationList(mGoodsSpecificationList);

                                    mGoodsInfo.getSelectVal(mGoodsSpecificationList)
                                            .subscribe(new ObserverCallback<StringBuilder>(mBuilder.build(mvpView)) {
                                                @Override
                                                public void onNext(StringBuilder result) {
                                                    mvpView.onSelectValResult(result);
                                                }
                                            });
                                }
                            });
                }
            }
        });
    }

    public void setSpecificationSelector(final int groupPosition, final int childPosition) {
        onceViewAttached(new ViewAction<GoodsSpecificationView>() {
            @Override
            public void run(@NonNull final GoodsSpecificationView mvpView) {
                mGoodsInfo.setSelect(mGoodsSpecificationList, groupPosition, childPosition)
                        .flatMap(new Function<List<GoodsSpecificationListBean>, ObservableSource<StringBuilder>>() {
                            @Override
                            public ObservableSource<StringBuilder> apply(List<GoodsSpecificationListBean> result) throws Exception {
                                return mGoodsInfo.getSelectVal(mGoodsSpecificationList);
                            }
                        })
                        .subscribe(new ObserverCallback<StringBuilder>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(StringBuilder result) {
                                mvpView.onSelectResult(groupPosition);
                                mvpView.onSelectValResult(result);
                            }
                        });

            }

        });
    }


    public interface GoodsSpecificationView extends MvpView {
        void onSpecificationList(List<GoodsSpecificationListBean> specificationListBeans);

        void onSelectResult(int groupPosition);

        void onSelectValResult(StringBuilder result);
    }
}
