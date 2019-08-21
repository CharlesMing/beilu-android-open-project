package com.scj.beilu.app.mvp.merchant.model;

import com.scj.beilu.app.api.MerchantApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoListBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeResultBean;
import com.scj.beilu.app.ui.merchant.adapter.MerchantInfoPicManagerListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:54
 */
public class MerchantInfoImpl extends BaseLoadUserInfoDelegate implements IMerchantInfo {

    private MerchantApi mMerchantApi;

    public MerchantInfoImpl(Builder builder) {
        super(builder);
        mMerchantApi = create(MerchantApi.class);
    }

    @Override
    public Observable<MerchantInfoListBean> getMerchantList(int currentPage, String keyName, String cityName) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", currentPage);
        if (keyName != null) {
            params.put("keyWord", keyName);
        }
        params.put("merAddr", cityName);
        return createObservable(mMerchantApi.getLocationByMerchantList(params));
    }

    @Override
    public Observable<MerchantInfoDetailsListResultBean> getMerchantInfo(int merchantId) {
        return createObservable(mMerchantApi.getMerchantInfoById(merchantId))
                .flatMap(new Function<MerchantInfoResultBean, ObservableSource<MerchantInfoDetailsListResultBean>>() {
                    @Override
                    public ObservableSource<MerchantInfoDetailsListResultBean> apply(MerchantInfoResultBean merchantInfoResultBean) throws Exception {
                        return delWithData(merchantInfoResultBean);
                    }
                });
    }

    private Observable<MerchantInfoDetailsListResultBean> delWithData(final MerchantInfoResultBean result) {
        ObservableOnSubscribe<MerchantInfoDetailsListResultBean> onSubscribe =
                new ObservableOnSubscribe<MerchantInfoDetailsListResultBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<MerchantInfoDetailsListResultBean> emitter) throws Exception {
                        MerchantInfoDetailsListResultBean detailsListResultBean = new MerchantInfoDetailsListResultBean();
                        try {

                            MerchantInfoBean merchantEntity = result.getMerchantEntity();
                            detailsListResultBean.setMerchantInfoBean(merchantEntity);

                            List<MerchantInfoDetailsListBean> infoDetailsListBeans = new ArrayList<>();
                            MerchantInfoDetailsListBean detail;

                            if (result.getCoachList() != null && result.getCoachList().size() > 0) {
                                detail = new MerchantInfoDetailsListBean();
                                detail.setCoachList(result.getCoachList());
                                detail.setGroupName("场馆教练");
                                infoDetailsListBeans.add(detail);
                            }

                            if (result.getMemberShipList() != null && result.getMemberShipList().size() > 0) {
                                detail = new MerchantInfoDetailsListBean();
                                detail.setMemberShipList(result.getMemberShipList());
                                detail.setGroupName("会籍顾问服务");
                                infoDetailsListBeans.add(detail);
                            }

                            detail = new MerchantInfoDetailsListBean();
                            detail.setGroupName("基本信息");
                            String openShop = merchantEntity.getMerchantOpenShop();
                            String closeShop = merchantEntity.getMerchantCloseShop();
                            String startBusiness = merchantEntity.getMerchantStartBusiness();
                            String merchantDecoration = merchantEntity.getMerchantDecoration();
                            openShop = (openShop == null) ? "00:00" : openShop;
                            closeShop = (closeShop == null) ? "00:00" : closeShop;
                            startBusiness = (startBusiness == null) ? "未知" : startBusiness;
                            merchantDecoration = (merchantDecoration == null) ? "未知" : merchantDecoration;
                            detail.setStartTime(openShop);
                            detail.setEndTime(closeShop);
                            detail.setStartBusinessTime(startBusiness);
                            detail.setFitmentTime(merchantDecoration);
                            infoDetailsListBeans.add(detail);

                            if (result.getInstallList() != null && result.getInstallList().size() > 0) {
                                detail = new MerchantInfoDetailsListBean();
                                detail.setInstallList(result.getInstallList());
                                detail.setGroupName("综合设施");
                                infoDetailsListBeans.add(detail);
                            }

                            detailsListResultBean.setDetailsListBeans(infoDetailsListBeans);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(detailsListResultBean);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    @Override
    public Observable<String> setContent(final String content) {
        ObservableOnSubscribe<String> onSubscribe =
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        try {
                            emitter.onNext(content);
                            emitter.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    @Override
    public Observable<MerchantInfoCoachResultBean> getCoachInfo(int merchantId, int coachId) {
        return createObservable(mMerchantApi.getCoachInfo(merchantId, coachId));
    }

    @Override
    public Observable<MerchantPicTypeResultBean> getMerchantPicTypeList(int merchantId) {
        return createObservable(mMerchantApi.getMerchantPicTypeList(merchantId));
    }

    @Override
    public Observable<MerchantPicInfoResultBean> getMerchantPicListById(int merchantId, int regionId) {
        return createObservable(mMerchantApi.getMerchantPicListById(merchantId, regionId));
    }

    @Override
    public Observable<MerchantInfoCoachPicInfoResultBean> getCoachAlbumList(int merchantId, int coachId) {
        return createObservable(mMerchantApi.getCoachAlbum(merchantId, coachId));
    }

    public Observable<ArrayList<MerchantPicTypeBean>> setSelectedByIndex(final int position) {
        ObservableOnSubscribe<ArrayList<MerchantPicTypeBean>> onSubscribe = new ObservableOnSubscribe<ArrayList<MerchantPicTypeBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<MerchantPicTypeBean>> emitter) throws Exception {
                try {
                    ArrayList<MerchantPicTypeBean> picTypeList = MerchantInfoPicManagerListAdapter.PIC_TYPE_LIST;
                    if (picTypeList != null) {
                        MerchantPicTypeBean typeBean = picTypeList.get(position);
                        if (!typeBean.isSelected()) {
                            int size = picTypeList.size();
                            for (int i = 0; i < size; i++) {
                                MerchantPicTypeBean picTypeBean = picTypeList.get(i);
                                if (i == position) {
                                    picTypeBean.setSelected(true);
                                } else {
                                    picTypeBean.setSelected(false);
                                }
                                picTypeList.set(i, picTypeBean);
                            }

                            emitter.onNext(picTypeList);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    emitter.onComplete();
                }
            }
        };
        return createObservableOnSubscribe(onSubscribe);
    }

}
