package com.scj.beilu.app.mvp.action;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.action.bean.ActionPayPhotoBean;
import com.scj.beilu.app.mvp.action.model.ActionInfoImpl;

public class ActionPayDescPre extends BaseMvpPresenter<ActionPayDescPre.ActionPayDescView> {
    private ActionInfoImpl mActionInfo;

    public ActionPayDescPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.NONE);
        mActionInfo = new ActionInfoImpl(mBuilder);
    }


    public void getActionPhoto() {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(mvpView -> mActionInfo.getActionPayPhoto()
                .subscribe(new BaseResponseCallback<ActionPayPhotoBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ActionPayPhotoBean actionPayPhotoBean) {

                    }
                }));
    }

    public interface ActionPayDescView extends MvpView {
    }

}
