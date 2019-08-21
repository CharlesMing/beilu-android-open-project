package com.scj.beilu.app.mvp.action.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.ActionApi;
import com.scj.beilu.app.mvp.action.bean.ActionInfoListBean;
import com.scj.beilu.app.mvp.action.bean.ActionInfoResultBean;
import com.scj.beilu.app.mvp.action.bean.ActionPayPhotoBean;
import com.scj.beilu.app.mvp.action.bean.ActionSecondTypeBean;
import com.scj.beilu.app.mvp.action.bean.ActionThirdTypeInfoBean;
import com.scj.beilu.app.mvp.action.bean.ActionThirdTypeListBean;
import com.scj.beilu.app.mvp.action.bean.ActionTopListTypeBean;
import com.scj.beilu.app.mvp.action.bean.ActionTypeDefBean;
import com.scj.beilu.app.mvp.action.bean.ActionTypeInfoBean;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:58
 * 动作库相关
 */
public class ActionInfoImpl extends BaseLoadUserInfoDelegate implements IActionInfo {

    private ActionApi mActionApi;
    public static ActionTopListTypeBean sActionTopListTypeBean;//一级列表
    private List<ActionSecondTypeBean> mTopSecondTypeList;//二级列表
    public static List<ActionThirdTypeInfoBean> sActionThirdTypeInfoList;//三级列表

    public ActionInfoImpl(Builder builder) {
        super(builder);
        mActionApi = create(ActionApi.class);
    }

    /**
     * 获取顶部两层分类数据
     */
    public Observable<ActionTopListTypeBean> getTopDoubleActionType() {
        return createObservable(mActionApi.getActionType());
    }

    /**
     * 获取第三层分类
     */
    public Observable<ActionThirdTypeListBean> getThirdActionType() {
        return createObservable(mActionApi.getThirdType());
    }

    @Override
    public Observable<ActionTypeInfoBean> getAllType() {
        return Observable.zip(getTopDoubleActionType(), getThirdActionType(),
                (actionTopListTypeBean, actionThirdTypeListBean) -> {
                    ActionTypeInfoBean infoBean = new ActionTypeInfoBean(actionTopListTypeBean, actionThirdTypeListBean);
                    ActionInfoImpl.sActionTopListTypeBean = actionTopListTypeBean;
                    ActionInfoImpl.sActionThirdTypeInfoList = actionThirdTypeListBean.getCate();
                    infoBean.setCode(2000);
                    return infoBean;
                });
    }

    @Override
    public Observable<ActionInfoListBean> getActionList(int desId, int cateId, int partId) {
        return createObservable(mActionApi.getActionListByTypeId(desId, cateId, partId));
    }

    @Override
    public Observable<ActionInfoResultBean> getActionInfo(int actionId) {
        return createObservable(mActionApi.getActionInfoById(actionId));
    }

    @Override
    public Observable<ActionPayPhotoBean> getActionPayPhoto() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ActionPayPhotoBean>>) token -> {
                    if (token.isEmpty()) {
                        throw new UserException();
                    } else {
                        return createObservable(mActionApi.getActionPayPhoto(token));
                    }
                });
    }


    /**
     * 通过一级列表下标获取二级分类
     * 默认选中第一个
     *
     * @param index
     * @return
     */
    public Observable<List<ActionSecondTypeBean>> getSecondTypeListByIndex(final int index) {

        ObservableOnSubscribe<List<ActionSecondTypeBean>> onSubscribe =
                emitter -> {
                    try {
                        mTopSecondTypeList = sActionTopListTypeBean.getDes().get(index).getParts();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    emitter.onNext(mTopSecondTypeList);
                    emitter.onComplete();
                };

        return createObservableOnSubscribe(onSubscribe)
                .flatMap((Function<List<ActionSecondTypeBean>, ObservableSource<List<ActionSecondTypeBean>>>) actionSecondTypeBeans -> setSecondSelectorByPosition(0));
    }

    /**
     * 获取三级分类
     * 默认选中第一个
     *
     * @return
     */
    public Observable<List<ActionThirdTypeInfoBean>> getThirdTypeListSetSelector(final int position) {
        ObservableOnSubscribe<List<ActionThirdTypeInfoBean>> onSubscribe =
                emitter -> {
                    try {
                        int size = sActionThirdTypeInfoList.size();
                        ActionThirdTypeInfoBean typeInfoBean = sActionThirdTypeInfoList.get(position);

                        if (!typeInfoBean.isSelected()) {
                            for (int i = 0; i < size; i++) {
                                ActionThirdTypeInfoBean infoBean = sActionThirdTypeInfoList.get(i);
                                if (i == position) {
                                    infoBean.setSelected(true);
                                } else {
                                    infoBean.setSelected(false);
                                }
                                sActionThirdTypeInfoList.set(i, infoBean);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onNext(sActionThirdTypeInfoList);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    /**
     * 二级列表选中状态，通过position 设置是否选中 默认必须选中一个
     */
    public Observable<List<ActionSecondTypeBean>> setSecondSelectorByPosition(final int position) {
        ObservableOnSubscribe<List<ActionSecondTypeBean>> onSubscribe =
                emitter -> {
                    try {
                        int size = mTopSecondTypeList.size();
                        ActionSecondTypeBean second = mTopSecondTypeList.get(position);
                        if (!second.isSelect()) {
                            for (int i = 0; i < size; i++) {

                                ActionSecondTypeBean secondTypeBean = mTopSecondTypeList.get(i);
                                if (i == position) {
                                    secondTypeBean.setSelect(true);
                                } else {
                                    secondTypeBean.setSelect(false);
                                }
                                mTopSecondTypeList.set(i, secondTypeBean);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onNext(mTopSecondTypeList);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    public Observable<ActionTypeDefBean> getActionTypeList(int secondIndex) {
        return Observable.zip(getSecondTypeListByIndex(secondIndex), getThirdTypeListSetSelector(0),
                (secondTypeBeans, actionThirdTypeInfoBeans) -> new ActionTypeDefBean(secondTypeBeans, actionThirdTypeInfoBeans));
    }

}
