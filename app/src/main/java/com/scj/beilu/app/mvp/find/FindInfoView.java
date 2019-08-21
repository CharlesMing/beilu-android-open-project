package com.scj.beilu.app.mvp.find;

import com.scj.beilu.app.mvp.common.share.ShareInfoView;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/12 00:34
 */
public interface FindInfoView extends ShareInfoView {

    void onFindListResult(List<FindInfoBean> findList);

    void onUserList(List<RecommendUserInfoBean> userInfoList);

    void onNotifyListChangeResult(String result, int position);

    void onModifyResult(int position);

    void onAddForResult(int position);

    void onAttentionResult(String result, int position);
}
