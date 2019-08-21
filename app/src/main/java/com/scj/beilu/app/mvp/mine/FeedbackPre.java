package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mingxun
 * @time on 2019/1/12 17:36
 */
public class FeedbackPre extends BaseMvpPresenter<FeedbackPre.FeedbackView> {
    private IMine mIMe;

    public FeedbackPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
    }

    /**
     * 提交反馈
     */

    public void addFeedBack(final String content, final String contact) {
        onceViewAttached(new ViewAction<FeedbackView>() {
            @Override
            public void run(@NonNull final FeedbackView mvpView) {
                Map<String, String> params = new HashMap<>();
                params.put("feedbackContent", content);
                if (contact != null && !(contact.isEmpty())) {
                    params.put("feedbackContact", contact);
                }
                mIMe.addFeedBack(params)
                        .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ResultMsgBean resultMsgBean) {
                                mvpView.onAddFeedBackResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public interface FeedbackView extends MvpView {
        void onAddFeedBackResult(String result);
    }
}
