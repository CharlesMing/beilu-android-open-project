package com.scj.beilu.app.mvp.find;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/19 16:24
 */
public class FindPublishPre extends BaseMvpPresenter<FindPublishPre.FindPublishView> {
    private IFindInfo mIFind;

    public FindPublishPre(Context context) {
        super(context);
        mIFind = new FindImpl(mBuilder);
    }


    public void startCreate(final List<String> paths, final boolean ofImage, final String description) {
        onceViewAttached(new ViewAction<FindPublishView>() {
            @Override
            public void run(@NonNull FindPublishView mvpView) {
                mIFind.createDynamicParams(paths, ofImage, description)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                // TODO: 2019/2/19
                            }
                        });
            }
        });
    }

    public interface FindPublishView extends MvpView {

    }
}
