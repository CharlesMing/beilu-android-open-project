package com.scj.beilu.app.mvp.action;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.action.bean.ActionTypeInfoBean;
import com.scj.beilu.app.mvp.action.model.ActionInfoImpl;
import com.scj.beilu.app.mvp.action.model.IActionInfo;

/**
 * @author Mingxun
 * @time on 2019/3/16 17:39
 */
public class ActionNavPre extends BaseMvpPresenter<ActionNavPre.ActionNavView> {

    private IActionInfo mActionInfo;

    public ActionNavPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mActionInfo = new ActionInfoImpl(mBuilder);
    }

    //合并请求所有分类数据，分类不能为null
    public void getAllType() {
        onceViewAttached(new ViewAction<ActionNavView>() {
            @Override
            public void run(@NonNull final ActionNavView mvpView) {
                mActionInfo.getAllType()
                        .subscribe(new BaseResponseCallback<ActionTypeInfoBean>(mBuilder.build(mvpView)) {

                            @Override
                            public void onRequestResult(ActionTypeInfoBean actionTypeInfoBean) {
                                try {
                                    mvpView.onTopFirstListResult(actionTypeInfoBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        try {
            if (ActionInfoImpl.sActionThirdTypeInfoList != null) {
                ActionInfoImpl.sActionThirdTypeInfoList.clear();
            }
            ActionInfoImpl.sActionTopListTypeBean = null;
            ActionInfoImpl.sActionThirdTypeInfoList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface ActionNavView extends MvpView {

        void onTopFirstListResult(ActionTypeInfoBean infoBean);

    }
}
