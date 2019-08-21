package com.scj.beilu.app.mvp.action;

import android.content.Context;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.mvp.action.bean.ActionInfoListBean;
import com.scj.beilu.app.mvp.action.bean.ActionSecondTypeBean;
import com.scj.beilu.app.mvp.action.bean.ActionThirdTypeInfoBean;
import com.scj.beilu.app.mvp.action.bean.ActionTypeDefBean;
import com.scj.beilu.app.mvp.action.model.ActionInfoImpl;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 16:21
 */
public class ActionListPre extends BaseMvpPresenter<ActionListPre.ActionListView> {

    private ActionInfoImpl mActionInfo;

    public ActionListPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mActionInfo = new ActionInfoImpl(mBuilder);
    }

    public void getTypeList(final int secondIndex) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mActionInfo.getActionTypeList(secondIndex)
                .subscribe(new ObserverCallback<ActionTypeDefBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(ActionTypeDefBean actionTypeDefBean) {
                        mvpView.onSecondListResult(actionTypeDefBean.secondTypeList);
                        mvpView.onThirdListResult(actionTypeDefBean.thirdTypeList);
                    }
                }));
    }


    public void setThirdSelector(final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mActionInfo.getThirdTypeListSetSelector(position)
                .subscribe(new ObserverCallback<List<ActionThirdTypeInfoBean>>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(List<ActionThirdTypeInfoBean> actionThirdTypeInfoBeans) {
                        mvpView.onThirdListResult(actionThirdTypeInfoBeans);
                    }
                }));
    }

    public void setSecondSelector(final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mActionInfo.setSecondSelectorByPosition(position)
                .subscribe(new ObserverCallback<List<ActionSecondTypeBean>>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(List<ActionSecondTypeBean> secondTypeBeans) {
                        mvpView.onSecondListResult(secondTypeBeans);
                    }
                }));
    }

    public void getActionListById(final int desId, final int cateId, final int partId) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mActionInfo.getActionList(desId, cateId, partId)
                .subscribe(new BaseResponseCallback<ActionInfoListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ActionInfoListBean actionInfoListBean) {
                        try {
                            mvpView.onTotalPrice(actionInfoListBean.getActionTotalPrice());
                            List<ActionInfoBean> sortInfoBeans = actionInfoListBean.getActions();
                            mvpView.onCheckLoadMore(sortInfoBeans);
                            mvpView.onActionListResult(sortInfoBeans);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }


    public interface ActionListView extends BaseCheckArrayView {
        void onSecondListResult(List<ActionSecondTypeBean> secondTypeList);

        void onThirdListResult(List<ActionThirdTypeInfoBean> thirdTypeList);

        void onActionListResult(List<ActionInfoBean> actionInfoList);

        void onTotalPrice(double price);
    }

}
