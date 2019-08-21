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
 * date:2019/2/28
 * descriptin:
 */
public class AddressUpdatePre extends BaseMvpPresenter<AddressUpdatePre.AddressUpdateView> {

    private MineImpl mIMe;

    public AddressUpdatePre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
        getDistirctList();
    }

    /**
     * 修改个人地址
     *
     * @param userAddressBean
     */
    public void modifyUserInfo(final AddressInfoBean userAddressBean) {
        onceViewAttached(new ViewAction<AddressUpdateView>() {
            @Override
            public void run(@NonNull final AddressUpdateView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("addrId", userAddressBean.getAddrId());
                params.put("userAddrName", userAddressBean.getUserAddrName());
                params.put("userAddrTel", userAddressBean.getUserAddrTel());
                params.put("userAddrProvince", userAddressBean.getUserAddrProvince());
                params.put("userAddrCity", userAddressBean.getUserAddrCity());
                params.put("userAddrDetail", userAddressBean.getUserAddrDetail());
                params.put("userAddrDefault", userAddressBean.getUserAddrDefault());
                mIMe.modifyUserShipAddr(params)
                        .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ResultMsgBean resultMsgBean) {
                                mvpView.onUpdateResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }


    /**
     * 删除地址
     *
     * @param addrId
     */
    public void delUserAddrById(final int addrId) {
        onceViewAttached(new ViewAction<AddressUpdateView>() {
            @Override
            public void run(@NonNull final AddressUpdateView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("addrId", addrId);
                mIMe.delUserAddrById(params)
                        .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ResultMsgBean resultMsgBean) {
                                mvpView.onUpdateResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    /**
     * 获取地区列表
     */
    private void getDistirctList() {
        onceViewAttached(new ViewAction<AddressUpdateView>() {
            @Override
            public void run(@NonNull final AddressUpdateView mvpView) {
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

    public interface AddressUpdateView extends MvpView {
        void onUpdateResult(String result);

        void onDistrictListResult(DictrictOptionBean optionBean);

    }
}
