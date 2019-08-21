package com.scj.beilu.app.mvp.find;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.find.bean.OrganizationListBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;

/**
 * author:SunGuiLan
 * date:2019/2/7
 * descriptin:
 */
public class FindOrganizationPre extends BaseMvpPresenter<FindOrganizationPre.FindOrganizationView> {
    private FindImpl mFind;

    public FindOrganizationPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mFind = new FindImpl(mBuilder);
    }

    /**
     * 获取组织动态
     */
    public void getOrganizationFindList() {

    }

    /**
     * 获取组织列表
     */
    public void getOrganizationList(final int currentPage) {
        onceViewAttached(new ViewAction<FindOrganizationView>() {
            @Override
            public void run(@NonNull FindOrganizationView mvpView) {
                mFind.getOrganization(currentPage)
                        .subscribe(new BaseResponseCallback<OrganizationListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(OrganizationListBean organizationListBean) {

                            }
                        });
            }
        });
    }

    public interface FindOrganizationView extends MvpView {

    }
}
