package com.scj.beilu.app.mvp.mine;

import android.content.Context;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/3/2
 * descriptin:
 */
public class MineFocusPre extends BaseMvpPresenter<MineFocusPre.MeAttentionView> {
    private IMine mIMe;
    private final List<FocusUserInfoBean> mFocusList = new ArrayList<>();

    public MineFocusPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
    }

    /**
     * 获取关注我的人
     *
     * @param currentPage
     */
    public void getMyFocusNumberList(final int currentPage) {
        onceViewAttached(mvpView -> mIMe.GetMyFocusNumberList(currentPage)
                .subscribe(new BaseResponseCallback<MeFocusListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(MeFocusListBean meFocusBean) {
                        List<FocusUserInfoBean> list = meFocusBean.getPage().getList();
                        if (currentPage == 0) {
                            mFocusList.clear();
                        }
                        mvpView.onCheckLoadMore(list);
                        mFocusList.addAll(list);
                        mvpView.onMyFocusNumberList(mFocusList);
                    }
                }));
    }


    /**
     * 关注人-取消关注
     */
    public void setAttentionUser(int focusUser, int position) {
        onceViewAttached(mvpView -> mIMe.setAttentionParams(mFocusList, position, focusUser)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(final ResultMsgBean resultMsgBean) {
                        mvpView.onNotifyListChangeResult(resultMsgBean.getResult());
                    }
                }));
    }

    public interface MeAttentionView extends BaseCheckArrayView {

        void onMyFocusNumberList(List<FocusUserInfoBean> focusList);

        void onNotifyListChangeResult(String result);
    }
}
