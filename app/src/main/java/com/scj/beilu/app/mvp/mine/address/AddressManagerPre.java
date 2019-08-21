package com.scj.beilu.app.mvp.mine.address;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.mine.address.bean.AddressArrayBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/18 15:47
 */
public class AddressManagerPre extends BaseMvpPresenter<AddressManagerPre.AddressManagerView> {
    private MineImpl mIMe;
    private final List<AddressInfoBean> mUserAddressBean = new ArrayList<>();

    public AddressManagerPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
    }

    public void getAllAddress() {
        onceViewAttached(new ViewAction<AddressManagerView>() {
            @Override
            public void run(@NonNull final AddressManagerView mvpView) {
                mIMe.getAllAddrByUserId()
                        .subscribe(new ObserverCallback<AddressArrayBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(AddressArrayBean addressArrayBean) {
                                List<AddressInfoBean> list = addressArrayBean.getAddr();
                                mUserAddressBean.clear();
                                mUserAddressBean.addAll(list);
                                mvpView.onAllAddressInfoResult(mUserAddressBean);
                            }
                        });
            }
        });
    }

    public interface AddressManagerView extends MvpView {
        void onAllAddressInfoResult(List<AddressInfoBean> userAddressBeans);
    }
}
