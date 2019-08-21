package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.fit.bean.FitUserSexBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;
import com.scj.beilu.app.mvp.fit.model.IFitRecord;

/**
 * @author Mingxun
 * @time on 2019/3/11 15:00
 */
public class FitRecordPre extends BaseMvpPresenter<FitRecordPre.FitRecordView> {

    private IFitRecord mFitRecord;

    public FitRecordPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mFitRecord = new FitRecordImpl(mBuilder);
    }

    public void getFitSexInfo() {
        onceViewAttached(new ViewAction<FitRecordView>() {
            @Override
            public void run(@NonNull final FitRecordView mvpView) {
                mFitRecord.getSexInfo()
                        .subscribe(new BaseResponseCallback<FitUserSexBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(FitUserSexBean sexBean) {
                                mvpView.OnFitSexInfo(sexBean);
                            }
                        });
            }
        });
    }

    public interface FitRecordView extends MvpView {
        void OnFitSexInfo(FitUserSexBean sexBean);
    }
}
