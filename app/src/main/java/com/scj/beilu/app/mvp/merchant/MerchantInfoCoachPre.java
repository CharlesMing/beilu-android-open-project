package com.scj.beilu.app.mvp.merchant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachResultBean;
import com.scj.beilu.app.mvp.merchant.model.IMerchantInfo;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Mingxun
 * @time on 2019/4/15 21:29
 */
public class MerchantInfoCoachPre extends BaseMvpPresenter<MerchantInfoCoachPre.MerchantInfoCoachView> {

    private IMerchantInfo mMerchantInfo;
    private final List<Boolean> mImageSelectorList = new ArrayList<>();

    public MerchantInfoCoachPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mMerchantInfo = new MerchantInfoImpl(mBuilder);
    }

    public void getCoachInfo(final int merchantId, final int coachId) {
        onceViewAttached(new ViewAction<MerchantInfoCoachView>() {
            @Override
            public void run(@NonNull final MerchantInfoCoachView mvpView) {
                mMerchantInfo.getCoachInfo(merchantId, coachId)
                        .subscribe(new BaseResponseCallback<MerchantInfoCoachResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MerchantInfoCoachResultBean merchantInfoCoachResultBean) {
                                mvpView.onCoachInfo(merchantInfoCoachResultBean);
                            }
                        });
            }
        });
    }

    public void initSelectorList(final ArrayList<MerchantInfoCoachPicInfoBean> picPreviewBeans) {

        Observable.create(new ObservableOnSubscribe<List<Boolean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Boolean>> emitter) throws Exception {
                List<Boolean> imgSelected = new ArrayList<>();
                try {
                    for (MerchantInfoCoachPicInfoBean bean : picPreviewBeans) {
                        imgSelected.add(false);
                    }
                    imgSelected.set(0, true);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    emitter.onNext(imgSelected);
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Boolean> booleans) {
                        mImageSelectorList.clear();
                        mImageSelectorList.addAll(booleans);
                        onceViewAttached(new ViewAction<MerchantInfoCoachView>() {
                            @Override
                            public void run(@NonNull MerchantInfoCoachView mvpView) {
                                mvpView.onImgSelectedListResult(mImageSelectorList);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void setSelectedByPosition(final int position) {
        onceViewAttached(new ViewAction<MerchantInfoCoachView>() {
            @Override
            public void run(@NonNull MerchantInfoCoachView mvpView) {
                int size = mImageSelectorList.size();
                for (int i = 0; i < size; i++) {
                    if (i == position) {
                        mImageSelectorList.set(position, true);

                    } else {
                        mImageSelectorList.set(i, false);
                    }
                }
                mvpView.onNotify(position);
            }
        });
    }


    public interface MerchantInfoCoachView extends MvpView {
        void onCoachInfo(MerchantInfoCoachResultBean coachInfo);

        void onImgSelectedListResult(List<Boolean> imageSelectorList);

        void onNotify(int position);

    }
}
