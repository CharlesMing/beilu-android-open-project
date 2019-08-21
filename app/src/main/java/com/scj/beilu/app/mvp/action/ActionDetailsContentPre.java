package com.scj.beilu.app.mvp.action;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.mvp.action.bean.ActionInfoResultBean;
import com.scj.beilu.app.mvp.action.model.ActionInfoImpl;
import com.scj.beilu.app.mvp.action.model.IActionInfo;

/**
 * @author Mingxun
 * @time on 2019/3/18 21:17
 */
public class ActionDetailsContentPre extends BaseMvpPresenter<ActionDetailsContentPre.ActionDetailsContentView> {
    private IActionInfo mActionInfo;

    public ActionDetailsContentPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mActionInfo = new ActionInfoImpl(mBuilder);
    }

    public void getActionDetailsById(final int actionId) {
        onceViewAttached(new ViewAction<ActionDetailsContentView>() {
            @Override
            public void run(@NonNull final ActionDetailsContentView mvpView) {
                mActionInfo.getActionInfo(actionId)
                        .subscribe(new BaseResponseCallback<ActionInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ActionInfoResultBean actionInfoBean) {
                                mvpView.onActionInfoResult(actionInfoBean.getAction());
                            }
                        });
            }
        });
    }

    public interface ActionDetailsContentView extends MvpView {
        void onActionInfoResult(ActionInfoBean actionInfo);
    }
}
