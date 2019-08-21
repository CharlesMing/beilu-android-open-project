package com.scj.beilu.app.mvp.news;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.news.bean.NewsNavListBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

/**
 * @author Mingxun
 * @time on 2019/1/11 15:37
 */
public class NewsPre extends BaseMvpPresenter<NewsPre.NewsView> {
    private INewsInfo mINews;

    public NewsPre(Context context) {
        super(context);
        mINews = new NewsInfoImpl(mBuilder);

    }

    /**
     * 初始化顶部导航
     */
    public void initNav() {
        mBuilder.setLoadType(ShowConfig.LOADING_STATE);
        onceViewAttached(mvpView -> mINews.getNewsNav()
                .subscribe(new ObserverCallback<NewsNavListBean>(mBuilder.build(mvpView)) {

                    @Override
                    public void onNext(NewsNavListBean viewsBean) {
                        mvpView.onNewsList(viewsBean);
                    }
                }));
    }

    public interface NewsView extends MvpView {

        void onNewsList(NewsNavListBean viewsBean);
    }
}
