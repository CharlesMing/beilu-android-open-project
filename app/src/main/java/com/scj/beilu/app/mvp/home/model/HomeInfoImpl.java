package com.scj.beilu.app.mvp.home.model;

import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.scj.beilu.app.api.HomePageApi;
import com.scj.beilu.app.mvp.common.bean.BannerListBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.home.bean.EventSearchTypeBean;
import com.scj.beilu.app.mvp.home.bean.HomePageFindListBean;
import com.scj.beilu.app.mvp.home.bean.HomePageNewsListBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/2/11 21:09
 */
public class HomeInfoImpl extends LoadDelegate implements IHomeInfo {

    private HomePageApi mHomePageApi;

    public HomeInfoImpl(Builder builder) {
        super(builder);
        mHomePageApi = create(HomePageApi.class);
    }

    @Override
    public Observable<BannerListBean> getBannerList() {
        return createObservable(mHomePageApi.getBannerList());
    }

    @Override
    public Observable<CourseListBean> getCourseList() {
        return createObservable(mHomePageApi.getHomeCourseList());
    }

    @Override
    public Observable<GoodsListBean> getGoodsList() {
        return createObservable(mHomePageApi.getHomeGoodsList());
    }

    @Override
    public Observable<HomePageNewsListBean> getNewsList() {
        return createObservable(mHomePageApi.getHomeNewsList());
    }

    @Override
    public Observable<HomePageFindListBean> getFindList() {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<HomePageFindListBean>>() {
                    @Override
                    public ObservableSource<HomePageFindListBean> apply(Map<String, String> header) throws Exception {
                        return createObservable(mHomePageApi.getHomeFindList(header));
                    }
                });
    }

    @Override
    public Observable<MeFocusListBean> startSearchUser(final int currentPage, final String keyName) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<MeFocusListBean>>() {

                    @Override
                    public ObservableSource<MeFocusListBean> apply(Map<String, String> token) throws Exception {
                        return createObservable(mHomePageApi.searchUser(token, 4, keyName, currentPage));
                    }
                });
    }


    public Observable<String> content(final String s) {
        ObservableOnSubscribe<String> onSubscribe =
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        try {
                            emitter.onNext(s);
                            emitter.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    public  Observable<EventSearchTypeBean> setContent(final EventSearchTypeBean content) {
        ObservableOnSubscribe<EventSearchTypeBean> onSubscribe =
                new ObservableOnSubscribe<EventSearchTypeBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<EventSearchTypeBean> emitter) throws Exception {
                        try {
                            emitter.onNext(content);
                            emitter.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

}
