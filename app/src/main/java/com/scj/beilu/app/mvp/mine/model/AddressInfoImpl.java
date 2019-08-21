package com.scj.beilu.app.mvp.mine.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.AddressApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressArrayBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/4/4 11:45
 */
public class AddressInfoImpl extends BaseLoadUserInfoDelegate implements IAddressInfo {
    private AddressApi mAddressApi;

    public AddressInfoImpl(Builder builder) {
        super(builder);
        mAddressApi = create(AddressApi.class);
    }

    @Override
    public Observable<AddressArrayBean> getAllAddrByUserId() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<AddressArrayBean>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mAddressApi.getAllAddrByUserId(token));
                    }
                });
    }


    public Observable<ResultMsgBean> addUserAddr(final Map<String, Object> params) {
        return getHeader().
                flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mAddressApi.addUserAddr(token, params));
                        }

                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> modifyUserShipAddr(final Map<String, Object> params) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0)
                            throw new UserException();
                        else {
                            return createObservable(mAddressApi.modifyUserShipAddr(token, params));
                        }
                    }
                });
    }


    @Override
    public Observable<ResultMsgBean> delUserAddrById(final Map<String, Object> params) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0)
                            throw new UserException();
                        else {
                            return createObservable(mAddressApi.delUserAddrById(token, params));
                        }
                    }
                });
    }

    @Override
    public Observable<AddressInfoResultBean> getDefAddressInfo() {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<AddressInfoResultBean>>() {
                    @Override
                    public ObservableSource<AddressInfoResultBean> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mAddressApi.getDefAddrByToken(token));
                        }
                    }
                });
    }
}
