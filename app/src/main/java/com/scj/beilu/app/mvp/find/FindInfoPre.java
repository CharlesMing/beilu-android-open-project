package com.scj.beilu.app.mvp.find;

import android.content.Context;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.share.ShareInfoPre;
import com.scj.beilu.app.mvp.find.bean.FindDetailsRecommendBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * @author Charlie
 * date:2019/2/2
 * 发现模块
 */
public class FindInfoPre<mvpView extends FindInfoView>
        extends ShareInfoPre<mvpView> {

    private IFindInfo mFind;

    protected final List<FindInfoBean> mFindList = new ArrayList<>();

    protected List<RecommendUserInfoBean> mUserInfoList;


    public FindInfoPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mFind = new FindImpl(mBuilder);
        EventBus.getDefault().register(this);
    }

    public void getHotList(final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mFind.getHotDynamicList(currentPage)
                .subscribe(new BaseResponseCallback<FindListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(FindListBean findListBean) {
                        List<FindInfoBean> list = findListBean.getPage().getList();
                        if (currentPage == 0) {
                            mFindList.clear();
                        }
                        mvpView.onCheckLoadMore(list);
                        mFindList.addAll(list);
                        mvpView.onFindListResult(mFindList);
                    }
                }));
    }

    public void getAttentionFindList(final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mFind
                .getAttentionFindList(currentPage)
                .subscribe(new BaseResponseCallback<FindListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(FindListBean findListBean) {
                        List<FindInfoBean> list = findListBean.getPage().getList();
                        if (currentPage == 0) {
                            mFindList.clear();
                        }
                        mvpView.onCheckLoadMore(list);
                        mFindList.addAll(list);
                        mvpView.onFindListResult(mFindList);
                    }
                }));
    }

    public void setLike(final int dynamicId, final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mFind.setLikeParamsByList(mFindList, dynamicId, position)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        mvpView.onNotifyListChangeResult(resultMsgBean.getResult(), position);
                    }
                }));
    }

    public void setFocusUser(final int focusUser) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(mvpView -> mFind.setAttentionParams(mFindList, focusUser)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(final ResultMsgBean resultMsgBean) {
                        mvpView.onNotifyListChangeResult(resultMsgBean.getResult(), -1);
                    }
                }));
    }

    public void modifyRecord(final ModifyRecordBean record, final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mFind.updateFindDetailsResult(mFindList, position, record)
                .subscribe(new ObserverCallback<Integer>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(Integer position1) {
                        mvpView.onModifyResult(position1);
                    }
                }));
    }

    public void delFind(int dynamicId) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        super.delFind(mFindList, dynamicId);
    }


    public void startSearch(final String val, final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mFind.startSearchFind(currentPage, val)
                .subscribe(new ObserverCallback<FindListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(FindListBean findListBean) {
                        try {
                            List<FindInfoBean> list = findListBean.getPage().getList();
                            if (currentPage == 0) {
                                mFindList.clear();
                            }
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            mFindList.addAll(list);
                            mvpView.onCheckLoadMore(list);

                            mvpView.onFindListResult(mFindList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }

    /**
     * 使用EventBus FindImpl post 传递数据
     * FindImpl.saveTempFindInfo()
     *
     * @param findInfo
     */
    @Subscribe
    public void addFindInfo(FindInfoBean findInfo) {
        onceViewAttached(mvpView -> {
            final int index = 0;
            mFindList.add(index, findInfo);
            mvpView.onAddForResult(index);
        });
    }

    @Subscribe
    public void addFindResultNotifyList(final FindListBean listBean) {
        onceViewAttached(mvpView -> {
            try {
                int index = 0;
                mFindList.remove(index);
                List<FindInfoBean> list = listBean.getPage().getList();
                if (list.size() > 0) {
                    mFindList.addAll(index, list);
                    mvpView.onAddForResult(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void getRecommendUserList() {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mFind.getRecommendList()
                .subscribe(new BaseResponseCallback<FindDetailsRecommendBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(FindDetailsRecommendBean userInfo) {
                        try {
                            if (mUserInfoList == null) {
                                mUserInfoList = new ArrayList<>();
                            }
                            mUserInfoList.clear();
                            List<RecommendUserInfoBean> users = userInfo.getUsers();
                            mUserInfoList.addAll(users);

                            mvpView.onUserList(mUserInfoList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }


    public void setAttention(final long userId, final int position) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(mvpView -> mFind.setAttentionParams(null, (int) userId)
                .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean ->
                        updateStatus(userId, resultMsgBean))
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        mvpView.onAttentionResult(resultMsgBean.getResult(), position);
                    }
                }));
    }

    private Observable<ResultMsgBean> updateStatus(final long userId, final ResultMsgBean result) {
        ObservableOnSubscribe<ResultMsgBean> onSubscribe =
                emitter -> {
                    try {
                        int size = mUserInfoList.size();
                        for (int i = 0; i < size; i++) {
                            RecommendUserInfoBean userInfoBean = mUserInfoList.get(i);

                            if (userId == userInfoBean.getUserId()) {
                                userInfoBean.setAttention(!userInfoBean.isAttention());
                                mUserInfoList.set(i, userInfoBean);
                                break;
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                };
        return Observable
                .create(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 更新热门/关注页 的关注状态
     *
     * @param userId
     */
    public void updateAttention(int userId) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mFind.updateAttentionStatus(mFindList, userId, new ResultMsgBean())
                .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(ResultMsgBean resultMsgBean) {
                        // TODO: 2019/4/24
                        mvpView.onNotifyListChangeResult(null, -1);
                    }
                }));
    }

    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

}
