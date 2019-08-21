package com.scj.beilu.app.mvp.common.share;

import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/10 13:16
 */
public interface ShareInfoView extends BaseCheckArrayView {
    void onFindDel();

    void onShareCountResult();

    void onFindDelResult(ResultMsgBean result);
}
