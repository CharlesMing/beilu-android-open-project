package com.scj.beilu.app.mvp.merchant;

import android.content.Context;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoListBean;
import com.scj.beilu.app.mvp.merchant.model.IMerchantInfo;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:55
 */
public class MerchantListPre extends BaseMvpPresenter<MerchantListPre.MerchantListView> {

    private PublishSubject<String> mPublishSubject;
    private IMerchantInfo mMerchantInfo;

    private final List<MerchantInfoBean> mMerchantInfoList;

    public MerchantListPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);

        mMerchantInfo = new MerchantInfoImpl(mBuilder);

        mMerchantInfoList = new ArrayList<>();
    }

    public void getMerchantList(int currentPage, String keyName, String cityName) {
        onceViewAttached(mvpView -> mMerchantInfo.getMerchantList(currentPage, keyName, cityName)
                .subscribe(new BaseResponseCallback<MerchantInfoListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(MerchantInfoListBean merchantInfoListBean) {
                        try {
                            List<MerchantInfoBean> list = merchantInfoListBean.getPage().getList();
                            if (currentPage == 0) {
                                mMerchantInfoList.clear();
                            }
                            mMerchantInfoList.addAll(list);

                            mvpView.onCheckLoadMore(list);
                            mvpView.onMerchantList(mMerchantInfoList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }

    public void searchMerchant(String keyName, String city) {
        onceViewAttached(mvpView -> {
            if (mPublishSubject == null) {
                mPublishSubject = PublishSubject.create();
                mPublishSubject.debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .filter(s -> s.length() > 0)
                        .switchMap((Function<String, ObservableSource<String>>) s -> mMerchantInfo.setContent(s))
                        .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(String s) {
                                getMerchantList(0, s, city);
                            }
                        });
            }
            mPublishSubject.onNext(keyName);
        });
    }

    public interface MerchantListView extends BaseCheckArrayView {
        void onMerchantList(List<MerchantInfoBean> merchantInfoList);
    }
}
