package com.scj.beilu.app.mvp.common;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;
import com.scj.beilu.app.mvp.common.preview.IPhotoInfo;
import com.scj.beilu.app.mvp.common.preview.PhotoInfoImpl;
import com.scj.beilu.app.util.ToastUtils;

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
 * @time on 2019/2/26 15:20
 */
public class PicImagePreviewPre extends BaseMvpPresenter<PicImagePreviewPre.PicImagePreviewView> {

    private IPhotoInfo mPhotoInfo;

    public PicImagePreviewPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mPhotoInfo = new PhotoInfoImpl(mBuilder);
    }

    private final List<Boolean> mImageSelectorList = new ArrayList<>();

    public void initSelectorList(final ArrayList<PicPreviewBean> picPreviewBeans) {

        Observable.create(new ObservableOnSubscribe<List<Boolean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Boolean>> emitter) throws Exception {
                List<Boolean> imgSelected = new ArrayList<>();
                try {
                    for (PicPreviewBean bean : picPreviewBeans) {
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
                        onceViewAttached(new ViewAction<PicImagePreviewView>() {
                            @Override
                            public void run(@NonNull PicImagePreviewView mvpView) {
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
        onceViewAttached(new ViewAction<PicImagePreviewView>() {
            @Override
            public void run(@NonNull PicImagePreviewView mvpView) {
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

    public void downloadImg(final String imgUrl) {
        onceViewAttached(mvpView -> mPhotoInfo.downloadPhoto(imgUrl)
                .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(mContext, s);
                    }
                }));

    }

    public interface PicImagePreviewView extends MvpView {
        void onImgSelectedListResult(List<Boolean> imageSelectorList);

        void onNotify(int position);

    }
}
