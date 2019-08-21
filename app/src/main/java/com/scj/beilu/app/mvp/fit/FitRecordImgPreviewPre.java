package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;
import com.scj.beilu.app.mvp.fit.model.IFitRecord;

/**
 * @author Mingxun
 * @time on 2019/3/15 18:06
 */
public class FitRecordImgPreviewPre extends BaseMvpPresenter<FitRecordImgPreviewPre.FitRecordImgPreviewView> {
    private IFitRecord mFitRecord;

    public FitRecordImgPreviewPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mFitRecord = new FitRecordImpl(mBuilder);
    }

    public void deleteImg(final long recordId) {
        onceViewAttached(new ViewAction<FitRecordImgPreviewView>() {
            @Override
            public void run(@NonNull final FitRecordImgPreviewView mvpView) {
                mFitRecord.deleteImg(recordId)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onDelResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public interface FitRecordImgPreviewView extends MvpView {
        void onDelResult(String result);
    }
}
