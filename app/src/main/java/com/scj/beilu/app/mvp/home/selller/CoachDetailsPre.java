package com.scj.beilu.app.mvp.home.selller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.mvp.home.model.CoachDetailsImpl;
import com.scj.beilu.app.mvp.home.model.ICoachDetails;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/2/15
 * descriptin:
 */
public class CoachDetailsPre extends BaseMvpPresenter<CoachDetailsPre.CoachDetailsView> {
    private ICoachDetails mICoachDetails;

    public interface CoachDetailsView extends MvpView {
        void onBannerList(List<String> images);
    }

    public CoachDetailsPre(Context context) {
        super(context);
      //  mBuilder.setUseToken(true);
        mICoachDetails = new CoachDetailsImpl(mBuilder);
    }

    public void getBannerList() {
        onceViewAttached(new ViewAction<CoachDetailsView>() {
            @Override
            public void run(@NonNull final CoachDetailsView mvpView) {
                mICoachDetails.getImages()
                        .subscribe(new ObserverCallback<List<String>>(
                                mBuilder.build(mvpView)
                        ) {
                            @Override
                            public void onNext(List<String> strings) {
                                mvpView.onBannerList(strings);
                            }
                        });
            }
        });
    }
}
