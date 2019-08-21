package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.view.View;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;
import com.scj.beilu.app.mvp.fit.model.IFitRecord;

/**
 * @author Mingxun
 * @time on 2019/3/15 13:24
 */
public class FitRecordGeneratePicturePre extends BaseMvpPresenter<FitRecordGeneratePicturePre.FitRecordGeneratePictureView> {
    private IFitRecord mFitRecord;

    public FitRecordGeneratePicturePre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mFitRecord = new FitRecordImpl(mBuilder);
    }

    public void startGenerate(final View view, final boolean isWaterMark) {
        onceViewAttached(mvpView -> mFitRecord.generatePicture(view, isWaterMark)
                .subscribe(new BaseResponseCallback<FitRecordImgResultBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(FitRecordImgResultBean result) {
                        mvpView.onGenerateResult(result);
                    }
                }));
    }

    public void shareToImage(View view, int scene) {
        onceViewAttached(mvpView -> mFitRecord.shareToImg(view, scene)
                .subscribe(new ObserverCallback<FitRecordImgResultBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(FitRecordImgResultBean result) {
                        mvpView.onGenerateResult(result);
                    }
                }));
    }

    public interface FitRecordGeneratePictureView extends MvpView {
        void onGenerateResult(FitRecordImgResultBean result);
    }
}
