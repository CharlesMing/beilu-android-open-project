package com.scj.beilu.app.mvp.news;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.bean.NewsBannerBean;
import com.scj.beilu.app.mvp.news.bean.NewsBannerListBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsNavBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/2/11 15:38
 */
public class NewsListPre extends BaseMvpPresenter<NewsListPre.NewsListView> {
    private INewsInfo mINewsList;
    private final List<NewsInfoBean> mNewsInfoBeans = new ArrayList<>();

    public NewsListPre(Context context) {
        super(context);
        if (mINewsList == null) {
            mINewsList = new NewsInfoImpl(mBuilder);
        }
    }

    public void getNewsList(final NewsNavBean nav, final int currentPage) {
        onceViewAttached(new ViewAction<NewsListView>() {
            @Override
            public void run(@NonNull final NewsListView mvpView) {
                if (nav.getNewsCateId() == 1) {//是否显示Bannner

                    mINewsList.getNewsBannerArray()
                            .flatMap(new Function<NewsBannerListBean, ObservableSource<NewsListBean>>() {
                                @Override
                                public ObservableSource<NewsListBean> apply(NewsBannerListBean newsBannerListBean) throws Exception {
                                    mvpView.onBannerList(newsBannerListBean.getBanner());
                                    return mINewsList.setNewsArrayByNavId(nav.getNewsCateId(), currentPage);
                                }
                            }).subscribe(new BaseResponseCallback<NewsListBean>(mBuilder.build(mvpView)) {
                        @Override
                        public void onRequestResult(NewsListBean newsArrayBean) {
                            List<NewsInfoBean> list = newsArrayBean.getPage().getList();
                            if (currentPage == 0) {
                                mNewsInfoBeans.clear();
                            }

                            mvpView.onCheckLoadMore(list);
                            mNewsInfoBeans.addAll(list);
                            mvpView.onNewsList(mNewsInfoBeans);
                        }
                    });

                } else {

                    mINewsList.setNewsArrayByNavId(nav.getNewsCateId(), currentPage)
                            .subscribe(new BaseResponseCallback<NewsListBean>(mBuilder.build(mvpView)) {
                                @Override
                                public void onRequestResult(NewsListBean newsArrayBean) {
                                    List<NewsInfoBean> list = newsArrayBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mNewsInfoBeans.clear();
                                    }

                                    mvpView.onCheckLoadMore(list);
                                    mNewsInfoBeans.addAll(list);
                                    mvpView.onNewsList(mNewsInfoBeans);
                                }
                            });
                }


            }
        });
    }

    public interface NewsListView extends BaseCheckArrayView {
        void onNewsList(List<NewsInfoBean> newsList);

        void onBannerList(List<NewsBannerBean> bannerBeanList);
    }
}
