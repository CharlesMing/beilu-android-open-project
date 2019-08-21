package com.scj.beilu.app.mvp.mine;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.mine.bean.AboutUsBean;
import com.scj.beilu.app.mvp.mine.bean.VersionBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

/**
 * author:SunGuiLan
 * date:2019/3/4
 * descriptin:
 */
public class AboutUsPre extends BaseMvpPresenter<AboutUsPre.AboutUsView> {
    private IMine mIMe;

    public AboutUsPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
    }


    /**
     * 关于我们
     */
    public void getAboutUsInfo() {
        onceViewAttached(mvpView -> mIMe.getAboutUsInfo()
                .subscribe(new BaseResponseCallback<AboutUsBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(AboutUsBean aboutUsBean) {
                        VersionBean version = aboutUsBean.getVersion();
                        mvpView.onAboutUsInfoResult(version);
                    }
                }));
    }

    public interface AboutUsView extends MvpView {
        void onAboutUsInfoResult(VersionBean versionBean);
    }
}
