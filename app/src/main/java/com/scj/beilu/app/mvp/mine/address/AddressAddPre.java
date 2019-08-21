package com.scj.beilu.app.mvp.mine.address;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * author:SunGuiLan
 * date:2019/2/27
 * descriptin:
 */
public class AddressAddPre extends BaseMvpPresenter<AddressAddPre.AddressAddView> {

    private MineImpl mIMe;

    public AddressAddPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
        getDistrictList();
    }

    public void addUserAddr(final AddressInfoBean userAddressBean) {
        onceViewAttached(new ViewAction<AddressAddView>() {
            @Override
            public void run(@NonNull final AddressAddView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("userAddrName", userAddressBean.getUserAddrName());
                params.put("userAddrTel", userAddressBean.getUserAddrTel());
                params.put("userAddrProvince", userAddressBean.getUserAddrProvince());
                params.put("userAddrCity", userAddressBean.getUserAddrCity());
                params.put("userAddrDetail", userAddressBean.getUserAddrDetail());
                params.put("userAddrDefault", userAddressBean.getUserAddrDefault());
                mIMe.addUserAddr(params)
                        .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ResultMsgBean resultMsgBean) {
                                mvpView.onAddUserAddrResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    /**
     * 获取地区列表
     */
    private void getDistrictList() {
        onceViewAttached(new ViewAction<AddressAddView>() {
            @Override
            public void run(@NonNull final AddressAddView mvpView) {
                mIMe.dealWithDistrictData()
                        .subscribe(new ObserverCallback<DictrictOptionBean>
                                (mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(DictrictOptionBean result) {
                                mvpView.onDistrictListResult(result);
                            }
                        });
            }
        });
    }

    public interface AddressAddView extends MvpView {
        void onAddUserAddrResult(String resultMsg);

        void onDistrictListResult(DictrictOptionBean optionBean);
    }
}
