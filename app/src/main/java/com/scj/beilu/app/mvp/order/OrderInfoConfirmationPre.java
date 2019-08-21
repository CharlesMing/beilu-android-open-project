package com.scj.beilu.app.mvp.order;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.pay.GoodsPayPre;
import com.scj.beilu.app.mvp.common.pay.GoodsPayView;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoResultBean;
import com.scj.beilu.app.mvp.mine.model.AddressInfoImpl;
import com.scj.beilu.app.mvp.mine.model.IAddressInfo;

/**
 * @author Mingxun
 * @time on 2019/4/4 11:36
 */
public class OrderInfoConfirmationPre extends GoodsPayPre<OrderInfoConfirmationPre.OrderInfoConfirmationView> {

    private IAddressInfo mAddressInfo;

    public OrderInfoConfirmationPre(Activity activity) {
        super(activity);
        mAddressInfo = new AddressInfoImpl(mBuilder);
    }

    public void getAddressInfo() {
        onceViewAttached(new ViewAction<OrderInfoConfirmationView>() {
            @Override
            public void run(@NonNull final OrderInfoConfirmationView mvpView) {
                mAddressInfo.getDefAddressInfo()
                        .subscribe(new BaseResponseCallback<AddressInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(AddressInfoResultBean userAddressBean) {
                                mvpView.onAddressInfoResult(userAddressBean);
                            }
                        });
            }
        });
    }
    public interface OrderInfoConfirmationView extends GoodsPayView {
        void onAddressInfoResult(AddressInfoResultBean addressInfo);
    }

}
