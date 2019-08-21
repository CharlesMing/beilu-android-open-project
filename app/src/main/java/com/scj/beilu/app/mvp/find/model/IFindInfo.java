package com.scj.beilu.app.mvp.find.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUserInfo;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentListBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsRecommendBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.bean.LikeResultBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.find.bean.OrganizationListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/19 10:39
 */
public interface IFindInfo extends IUserInfo {

    /**
     * 发布动态
     */
    Observable<ResultMsgBean> createDynamicParams(List<String> path, boolean ofImage, String description);

    /**
     * 获取热门动态列表
     */
    Observable<FindListBean> getHotDynamicList(int currentPage);

    /**
     * 获取推荐用户
     */
    Observable<FindDetailsRecommendBean> getRecommendList();

    /**
     * 设置 关注
     */
    Observable<ResultMsgBean> setAttentionParams(final List<FindInfoBean> beanList, int userId);

    /**
     * 更新本地关注状态
     */
    Observable<ResultMsgBean> updateAttentionStatus(final List<FindInfoBean> beanList,
                                                    final int focusUserId,
                                                    final ResultMsgBean result);

    /**
     * 通过列表点赞
     */
    Observable<LikeResultBean> setLikeParamsByList(final List<FindInfoBean> beanList, int findId, int position);

    /**
     * 获取动态详情
     */
    Observable<FindDetailsBean> getFindDetailsById(int dynamicId);

    /**
     * 获取动态评论列表
     */
    Observable<FindCommentListBean> getFindCommentList(int dynamicId, int currentPage);


    /**
     * 评论回复
     * 如果用户点击的是自己: 调用{@link FindImpl#setCommentParams(int, String, List<FindCommentBean>)}
     * 否则就调用{@link FindImpl#setReplyCommentParams(FindCommentBean, String, List, int)}
     */
    Observable<ResultMsgBean> createCommentParams(int findId, FindCommentBean commentInfo,
                                                  String commentContent,
                                                  List<FindCommentBean> commentList,
                                                  int position);

    /**
     * 详情返回过来，用于修改列表数据
     */
    Observable<Integer> updateFindDetailsResult(List<FindInfoBean> findList, int position,
                                                ModifyRecordBean record);

    /**
     * 获取关注动态列表
     */
    Observable<FindListBean> getAttentionFindList(int currentPage);

    /**
     * 获取组织列表
     */
    Observable<OrganizationListBean> getOrganization(int currentPage);

    /**
     * 获取用户动态列表
     */
    Observable<FindListBean> getUserDynamicList(int userId, int currentPage);

    /**
     * 删除动态
     */
    Observable<ResultMsgBean> delWithFind(final List<FindInfoBean> findList, int dynamicId);

    /**
     * 搜索发现
     */
    Observable<FindListBean> startSearchFind(int currentPage, String keyName);

    /**
     * 获取我的第一个动态
     */
    Observable<FindListBean> getMyFindInfoList();


}
