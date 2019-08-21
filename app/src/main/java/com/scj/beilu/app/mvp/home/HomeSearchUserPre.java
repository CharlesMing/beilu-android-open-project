package com.scj.beilu.app.mvp.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.home.model.HomeInfoImpl;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:07
 */
public class HomeSearchUserPre extends BaseMvpPresenter<HomeSearchUserPre.HomeSearchView> {

    private PublishSubject<String> mPublishSubject;

    private HomeInfoImpl mHomeInfo;
    private IMine mIMe;
    private final List<FocusUserInfoBean> mFocusList = new ArrayList<>();

    public HomeSearchUserPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mHomeInfo = new HomeInfoImpl(mBuilder);
        mIMe = new MineImpl(mBuilder);
    }

    public void startSearch(final String keyName, final int currentPage) {
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull HomeSearchView mvpView) {
                if (mPublishSubject == null) {
                    mPublishSubject = PublishSubject.create();
                    mPublishSubject.debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                            .filter(new Predicate<String>() {
                                @Override
                                public boolean test(String s) throws Exception {
                                    return s.length() > 0;
                                }
                            })

                            .switchMap(new Function<String, Observable<String>>() {
                                @Override
                                public Observable<String> apply(String s) throws Exception {
                                    return mHomeInfo.content(s);
                                }
                            })
                            .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                                @Override
                                public void onNext(String keyName) {
                                    searchUserInfoList(keyName, currentPage);
                                }
                            });

                }

                mPublishSubject.onNext(keyName);
            }
        });
    }


    public void searchUserInfoList(final String keyName, final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);

        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull final HomeSearchView mvpView) {
                mHomeInfo.startSearchUser(currentPage, keyName)
                        .subscribe(new BaseResponseCallback<MeFocusListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MeFocusListBean meFocusBean) {
                                try {
                                    List<FocusUserInfoBean> list = meFocusBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mFocusList.clear();
                                    }
                                    mvpView.onCheckLoadMore(list);
                                    mFocusList.addAll(list);
                                    mvpView.onUserInfoList(mFocusList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    /**
     * 关注人-取消关注
     */
    public void setAttentionUser(final int focusUser, final int position) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull final HomeSearchView mvpView) {
                mIMe.setAttentionParams(mFocusList, position, focusUser)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(final ResultMsgBean resultMsgBean) {
                                mvpView.onNotifyListChangeResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public interface HomeSearchView extends BaseCheckArrayView {

        void onUserInfoList(List<FocusUserInfoBean> userInfoList);

        void onNotifyListChangeResult(String result);
    }
}
