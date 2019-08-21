package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.mine.bean.TotalCountBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/20 11:09
 */
public class MinePre extends BaseMvpPresenter<MinePre.MineView> {
    private IMine mMine;

    public MinePre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mMine = new MineImpl(mBuilder);
    }

    public void getMineInfo() {
        onceViewAttached(new ViewAction<MineView>() {
            @Override
            public void run(@NonNull final MineView mvpView) {
                Observable.merge(mMine.getUserInfoByToken(), mMine.getTotalCount())
                        .subscribe(new ObserverCallback<Object>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(Object o) {
                                if (o instanceof UserInfoEntity) {
                                    mvpView.onUserInfo((UserInfoEntity) o);
                                } else if (o instanceof TotalCountBean) {
                                    mvpView.onTotalCount((TotalCountBean) o);
                                }
                            }
                        });

//                Observable.zip(mMine.getUserInfoByToken(), mMine.getTotalCount(), new BiFunction<UserInfoEntity, TotalCountBean, TotalCountBean>() {
//                    @Override
//                    public TotalCountBean apply(UserInfoEntity userInfoEntity, TotalCountBean totalCountBean) throws Exception {
//                        totalCountBean.setUserInfoEntity(userInfoEntity);
//                        return totalCountBean;
//                    }
//                }).subscribe(new ObserverCallback<TotalCountBean>(mBuilder.build(mvpView)) {
//                    @Override
//                    public void onNext(TotalCountBean totalCountBean) {
//                        mvpView.onTotalCount(totalCountBean);
//                    }
//                });

            }
        });
    }


    public interface MineView extends MvpView {
        //        void onTotalCount(TotalCountBean totalCountBean);
        void onUserInfo(UserInfoEntity userInfo);

        void onTotalCount(TotalCountBean totalCountBean);
    }
}
