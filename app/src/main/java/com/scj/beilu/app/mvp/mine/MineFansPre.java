package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansListBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/3/4
 * descriptin:
 */
public class MineFansPre extends BaseMvpPresenter<MineFansPre.MeFansView> {
    private IMine mIMe;
    private final List<MeFansInfoBean> mMeFansList = new ArrayList<>();

    public MineFansPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
    }

    /**
     * 获取关注我的人
     *
     * @param currentPage
     */
    public void getMyFansList(final int currentPage) {
        onceViewAttached(new ViewAction<MeFansView>() {
            @Override
            public void run(@NonNull final MeFansView mvpView) {
                mIMe.getMyFans(currentPage)
                        .subscribe(new BaseResponseCallback<MeFansListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MeFansListBean meFansListBean) {
                                List<MeFansInfoBean> list = meFansListBean.getPage().getList();
                                if (currentPage == 0) {
                                    mMeFansList.clear();
                                }
                                mvpView.onCheckLoadMore(list);
                                mMeFansList.addAll(list);
                                mvpView.onMyFansResult(mMeFansList);
                            }
                        });
            }
        });
    }

    /**
     * 关注-取消关注人
     */
    public void setAttentionUser(final int focusUser, final int position) {
        onceViewAttached(new ViewAction<MeFansView>() {
            @Override
            public void run(@NonNull final MeFansView mvpView) {
                mIMe.setFansAttentionParams(mMeFansList, position, focusUser)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(final ResultMsgBean resultMsgBean) {
                                mvpView.onNotifyListChangeResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public interface MeFansView extends BaseCheckArrayView {
        void onMyFansResult(List<MeFansInfoBean> fansList);

        void onNotifyListChangeResult(String result);
    }
}
