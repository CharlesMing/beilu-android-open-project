package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;
import com.scj.beilu.app.mvp.store.bean.CollectGoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.model.GoodsInfoImpl;
import com.scj.beilu.app.mvp.store.model.IGoodsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 17:03
 */
public class MyCollectPre extends BaseMvpPresenter<MyCollectPre.MyCollectView> {

    private IGoodsInfo mGoodsInfo;
    private INewsInfo mNewsInfo;

    private final List<GoodsInfoBean> mGoodsInfoList = new ArrayList<>();
    private final List<NewsInfoBean> mNewsInfoList = new ArrayList<>();

    public MyCollectPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mGoodsInfo = new GoodsInfoImpl(mBuilder);
        mNewsInfo = new NewsInfoImpl(mBuilder);
    }
    public void getMyCollectNews(final int currentPage) {
        onceViewAttached(new ViewAction<MyCollectView>() {
            @Override
            public void run(@NonNull final MyCollectView mvpView) {
                mNewsInfo.getMyCollectNews(currentPage)
                        .subscribe(new BaseResponseCallback<NewsListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(NewsListBean newsListBean) {
                                try {
                                    List<NewsInfoBean> list = newsListBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mNewsInfoList.clear();
                                    }

                                    mvpView.onCheckLoadMore(list);
                                    mNewsInfoList.addAll(list);
                                    mvpView.onNewsList(mNewsInfoList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public void getMyCollectGoods(final int currentPage) {
        onceViewAttached(new ViewAction<MyCollectView>() {
            @Override
            public void run(@NonNull final MyCollectView mvpView) {
                mGoodsInfo.getMyCollectGoodsList(currentPage)
                        .subscribe(new BaseResponseCallback<CollectGoodsListResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CollectGoodsListResultBean goodsListResultBean) {
                                try {

                                    if (currentPage == 0) {
                                        mGoodsInfoList.clear();
                                    }
                                    List<GoodsInfoBean> list = goodsListResultBean.getCollectionPage().getList();

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

    public interface MyCollectView extends BaseCheckArrayView {
        void onNewsList(List<NewsInfoBean> newsList);

        void onGoodsList(List<GoodsInfoBean> goodsInfoBeanList);
    }
}
