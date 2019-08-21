package com.scj.beilu.app.mvp.find.model;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.mx.pro.lib.mvp.exception.UserException;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.scj.beilu.app.api.FindApi;
import com.scj.beilu.app.api.HomePageApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentListBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentReplyBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsRecommendBean;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoLikeMemberBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.bean.LikeResultBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.find.bean.OrganizationListBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @author Mingxun
 * @time on 2019/2/19 10:42
 */
public class FindImpl extends BaseLoadUserInfoDelegate implements IFindInfo {

    private FindApi mFindApi;
    private MediaMetadataRetriever mMetadataRetriever;
    private final String VAL_DESCRIPTION = "dynamicDec";
    private HomePageApi mHomePageApi;

    public FindImpl(Builder builder) {
        super(builder);
        mFindApi = create(FindApi.class);
    }

    @Override
    public Observable<ResultMsgBean> createDynamicParams(final List<String> path, final boolean ofImage, final String description) {
        if (path.size() == 0) {
            return createDesc(description);
        } else if (ofImage) {
            return createImage(path, description);
        } else {
            return createVideo(path, description);
        }
    }

    @Override
    public Observable<FindListBean> getHotDynamicList(final int currentPage) {

        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<FindListBean>>() {
                    @Override
                    public ObservableSource<FindListBean> apply(Map<String, String> token) throws Exception {
                        return createObservable(mFindApi.getHotDynamicList(token, currentPage));
                    }
                });

    }

    @Override
    public Observable<FindDetailsRecommendBean> getRecommendList() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FindDetailsRecommendBean>>) token ->
                        createObservable(mFindApi.getRecommendUserList(token)));
    }

    @Override
    public Observable<ResultMsgBean> setAttentionParams(final List<FindInfoBean> beanList, final int userId) {
        if (beanList == null) {
            return getHeader()
                    .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header -> {
                        if (header.size() == 0) {
                            throw new UserException();
                        } else
                            return createObservable(mFindApi.setUserFocus(header, userId));
                    });
        } else {
            return getHeader()
                    .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header -> {
                        if (header.size() == 0) {
                            throw new UserException();
                        } else
                            return createObservable(mFindApi.setUserFocus(header, userId));
                    })
                    .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean ->
                            updateAttentionStatus(beanList, userId, resultMsgBean));
        }
    }


    @Override
    public Observable<LikeResultBean> setLikeParamsByList(final List<FindInfoBean> beanList, final int findId, final int position) {
        if (beanList == null) {
            return getHeader()
                    .flatMap((Function<Map<String, String>, ObservableSource<LikeResultBean>>) token -> {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else
                            return createObservable(mFindApi.setLike(token, findId));
                    });
        } else {
            return getHeader()
                    .flatMap((Function<Map<String, String>, ObservableSource<LikeResultBean>>) token -> {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else
                            return createObservable(mFindApi.setLike(token, findId));
                    })
                    .flatMap((Function<LikeResultBean, ObservableSource<LikeResultBean>>) likeResultBean ->
                            updateLikeCount(beanList, position, likeResultBean));
        }

    }


    @Override
    public Observable<FindListBean> getAttentionFindList(final int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FindListBean>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mFindApi.getAttentionDynamicList(token, currentPage));
                    }
                });
    }

    @Override
    public Observable<OrganizationListBean> getOrganization(int currentPage) {
        return createObservable(mFindApi.getOrganizationList(currentPage));
    }

    @Override
    public Observable<FindListBean> getUserDynamicList(final int userId, final int currentPage) {
        Observable<FindListBean> observable;
        if (userId == -1) {
            observable = getHeader()
                    .flatMap((Function<Map<String, String>, ObservableSource<FindListBean>>) token ->
                            createObservable(mFindApi.getMyDynamicByToken(token, currentPage)));

        } else {
            observable = getHeader()
                    .flatMap((Function<Map<String, String>, ObservableSource<FindListBean>>) token ->
                            createObservable(mFindApi.getOtherDynamicListByOtherUserId(token, userId, currentPage)));
        }
        //获取数据后，将处理时间格式
        return observable.flatMap((Function<FindListBean, ObservableSource<FindListBean>>) findListBean -> {
            if (findListBean.getPage() == null) {
                return dealWithTime(null, findListBean);
            } else {
                return dealWithTime(findListBean.getPage().getList(), findListBean);
            }
        });
    }

    @Override
    public Observable<ResultMsgBean> delWithFind(final List<FindInfoBean> findList, final int dynamicId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) token ->
                        createObservable(mFindApi.delWithFind(token, dynamicId)))
                .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean ->
                        notifyDelWithResult(findList, dynamicId, resultMsgBean));
    }

    private Observable<ResultMsgBean> notifyDelWithResult(final List<FindInfoBean> findList, final int dynamicId, final ResultMsgBean result) {
        ObservableOnSubscribe<ResultMsgBean> onSubscribe =
                emitter -> {
                    try {
                        if (findList != null) {
                            for (int i = 0; i < findList.size(); i++) {
                                FindInfoBean findBean = findList.get(i);
                                if (findBean.getDynamicId() == dynamicId) {
                                    findList.remove(i);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }


    /**
     * 详情返回过来 修改数据
     */
    @Override
    public Observable<Integer> updateFindDetailsResult(final List<FindInfoBean> findList, final int position,
                                                       final ModifyRecordBean record) {
        ObservableOnSubscribe<Integer> updateObservableOnSubscribe =
                emitter -> {
                    int notifyPosition = position;
                    try {
                        //更新关注状态
                        if (record.mFocusUserId != -1) {
                            int size = findList.size();
                            for (int i = 0; i < size; i++) {
                                FindInfoBean findInfo = findList.get(i);
                                if (findInfo.getUserId() == record.mFocusUserId) {
                                    findInfo.setIsFocus(findInfo.getIsFocus() == 1 ? 0 : 1);
                                    findList.set(i, findInfo);
                                }
                            }
                            notifyPosition = size;
                        }

                        FindInfoBean findInfo = findList.get(position);

                        if (record.mLikeCount != -1) {
                            //更新点赞数量
                            findInfo.setDynamicLikeCount(record.mLikeCount);
                        }
                        if (record.mCommentCount != -1) {
                            //更新评论数量
                            findInfo.setCommentCount(record.mCommentCount);
                        }
                        findList.set(position, findInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onNext(notifyPosition);
                        emitter.onComplete();
                    }


                };
        return createObservableOnSubscribe(updateObservableOnSubscribe);
    }

    @Override
    public Observable<FindDetailsBean> getFindDetailsById(final int dynamicId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FindDetailsBean>>) header -> {
                    //获取详情
                    return createObservable(mFindApi.getFindDetailsById(header, dynamicId));
                });
    }


    @Override
    public Observable<FindCommentListBean> getFindCommentList(int dynamicId, final int currentPage) {
        return createObservable(mFindApi.getFindCommentList(dynamicId, currentPage))
                .flatMap((Function<FindCommentListBean, ObservableSource<FindCommentListBean>>) commentListBean -> {
                    List<FindCommentBean> list = commentListBean.getPage().getList();
                    //处理时间格式
                    return dealWithTime(list, commentListBean);
                });

    }

    /**
     * 通过详情UI进行关注并更新UI
     *
     * @param findBean by findBean in UserId
     * @return
     */
    public Observable<FindDetailsBean> setAttentionParamsByDetails(final FindDetailsBean findBean) {
        return setAttentionParams(null, findBean.getDynamic().getUserId())
                .flatMap((Function<ResultMsgBean, ObservableSource<FindDetailsBean>>) resultMsgBean ->
                        getUserInfoByToken()
                                .flatMap((Function<UserInfoEntity, ObservableSource<FindDetailsBean>>) userInfoEntity -> {
                                    userInfoEntity.setResult(resultMsgBean.getResult());
                                    return notifyFindInfo(findBean, null, userInfoEntity);
                                }));
    }

    /**
     * 通过详情UI进行点赞
     * 更新点赞成员列表
     * 动态id
     *
     * @return {@link FindImpl#notifyFindInfo(FindDetailsBean, LikeResultBean, UserInfoEntity)}
     */
    public Observable<FindDetailsBean> setLikeParamsByDetails(final FindDetailsBean findInfo) {
        return setLikeParamsByList(null, findInfo.getDynamic().getDynamicId(), -1)
                .flatMap((Function<LikeResultBean, ObservableSource<FindDetailsBean>>) likeResultBean ->
                        getUserInfoByToken()
                                .flatMap((Function<UserInfoEntity, ObservableSource<FindDetailsBean>>)
                                        userInfoEntity -> {
                                            userInfoEntity.setResult(likeResultBean.getResult());
                                            return notifyFindInfo(findInfo, likeResultBean, userInfoEntity);
                                        }));
    }

    /**
     * 开始更新列表
     */
    private Observable<FindDetailsBean> notifyFindInfo(final FindDetailsBean findInfo,
                                                       final LikeResultBean likeResult,
                                                       final UserInfoEntity userInfo) {
        ObservableOnSubscribe<FindDetailsBean> updateListSub =
                emitter -> {
                    try {

                        FindInfoBean dynamic = findInfo.getDynamic();
                        int dynamicId = dynamic.getDynamicId();
                        if (likeResult != null) {

                            List<FindInfoLikeMemberBean> memberList = findInfo.getLikeNumbers();
                            if (likeResult.getLike() > 0) {//add action
                                memberList.add(new FindInfoLikeMemberBean(dynamicId, userInfo.getUserId(), userInfo.getUserOriginalHead()));
                            } else {//remove action
                                int size = memberList.size();
                                for (int i = 0; i < size; i++) {
                                    FindInfoLikeMemberBean memberBean = memberList.get(i);
                                    if (memberBean.getUserId() == userInfo.getUserId()) {
                                        memberList.remove(i);
                                        break;
                                    }
                                }
                            }
                            dynamic.setDynamicLikeCount(dynamic.getDynamicLikeCount() + likeResult.getLike());
                            findInfo.setResult(userInfo.getResult());

                        } else {
                            dynamic.setIsFocus(dynamic.getIsFocus() == 1 ? 0 : 1);
                            findInfo.setDynamic(dynamic);
                            findInfo.setResult(userInfo.getResult());
                        }
                    } catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    } finally {
                        emitter.onNext(findInfo);
                        emitter.onComplete();
                    }

                };
        return createObservableOnSubscribe(updateListSub);
    }


    /**
     * 更新关注状态
     */
    @Override
    public Observable<ResultMsgBean> updateAttentionStatus(final List<FindInfoBean> beanList,
                                                           final int focusUserId,
                                                           final ResultMsgBean result) {
        ObservableOnSubscribe<ResultMsgBean> notifyObser = emitter -> {
            int size = beanList.size();
            for (int i = 0; i < size; i++) {
                FindInfoBean findInfo = beanList.get(i);
                if (findInfo.getUserId() == focusUserId) {
                    findInfo.setIsFocus(findInfo.getIsFocus() == 1 ? 0 : 1);
                    beanList.set(i, findInfo);
                }
            }
            emitter.onNext(result);
            emitter.onComplete();
        };
        return createObservableOnSubscribe(notifyObser);
    }

    /***
     * 更新喜欢数量
     */
    private Observable<LikeResultBean> updateLikeCount(final List<FindInfoBean> findList,
                                                       final int position,
                                                       final LikeResultBean likeResultBean) {
        ObservableOnSubscribe<LikeResultBean> updatePraiseObservable =
                emitter -> {
                    FindInfoBean findInfo = findList.get(position);
                    findInfo.setDynamicLikeCount(findInfo.getDynamicLikeCount() + likeResultBean.getLike());
                    findInfo.setIsLike(likeResultBean.getLike());
                    findList.set(position, findInfo);
                    emitter.onNext(likeResultBean);
                    emitter.onComplete();
                };
        return createObservableOnSubscribe(updatePraiseObservable);
    }

    /**
     * 发布动态评论
     *
     * @param dynamicId
     * @param commentContent
     * @return
     */
    private Observable<ResultMsgBean> setCommentParams(final int dynamicId, final String commentContent,
                                                       final List<FindCommentBean> commentList) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> header) throws Exception {
                        if (header.size() == 0) {
                            throw new UserException();
                        } else
                            return createObservable(mFindApi.createFindComment(header, dynamicId, commentContent));
                    }
                })
                .flatMap(new Function<ResultMsgBean, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(final ResultMsgBean result) throws Exception {
                        return getUserInfoByToken()
                                .flatMap(new Function<UserInfoEntity, ObservableSource<ResultMsgBean>>() {
                                    @Override
                                    public ObservableSource<ResultMsgBean> apply(UserInfoEntity userInfoEntity) throws Exception {
                                        userInfoEntity.setResult(result.getResult());
                                        return updateCommentList(commentList, userInfoEntity, commentContent);
                                    }
                                });
                    }

                });
    }

    /**
     * 动态评论回复
     *
     * @param commentInfo
     * @param commentContent
     * @return
     */
    private Observable<ResultMsgBean> setReplyCommentParams(final FindCommentBean commentInfo,
                                                            final String commentContent,
                                                            final List<FindCommentBean> commentList,
                                                            final int position) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> header) throws Exception {
                        if (header.size() == 0) {
                            throw new UserException();
                        } else {
                            Map<String, Object> params = new HashMap<>();
                            params.put("comId", commentInfo.getComId());
                            params.put("toUserId", commentInfo.getComUserId());
                            params.put("dynamicReplyContent", commentContent);
                            return createObservable(mFindApi.createReplyFidnComment(header, params));
                        }
                    }
                })
                .flatMap(new Function<ResultMsgBean, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(final ResultMsgBean result) throws Exception {
                        return getUserInfoByToken()
                                .flatMap(new Function<UserInfoEntity, ObservableSource<ResultMsgBean>>() {
                                    @Override
                                    public ObservableSource<ResultMsgBean> apply(UserInfoEntity userInfo) throws Exception {
                                        userInfo.setResult(result.getResult());
                                        return updateCommentReplyList(commentList, commentContent, userInfo, position);
                                    }
                                });
                    }
                });
    }

    /**
     * 更新本地数组
     */
    private Observable<ResultMsgBean> updateCommentReplyList(final List<FindCommentBean> commentList,
                                                             final String commentContent,
                                                             final UserInfoEntity userInfo,
                                                             final int position) {
        ObservableOnSubscribe<ResultMsgBean> update =
                new ObservableOnSubscribe<ResultMsgBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResultMsgBean> emitter) throws Exception {

                        FindCommentBean findCommentBean = commentList.get(position);

                        List<FindCommentReplyBean> comReplies = findCommentBean.getComReplies();

                        if (comReplies == null) {
                            comReplies = new ArrayList<>();
                        }

                        try {
                            FindCommentReplyBean findCommentReplyBean = new FindCommentReplyBean();
                            findCommentReplyBean.setToUserName(findCommentBean.getComUserName());
                            findCommentReplyBean.setToUserId(findCommentBean.getComUserId());
                            findCommentReplyBean.setDynamicReplyContent(commentContent);
                            findCommentReplyBean.setFromUserName(userInfo.getUserNickName());
                            findCommentReplyBean.setFromUserId((int) userInfo.getUserId());
                            comReplies.add(findCommentReplyBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            ResultMsgBean result = new ResultMsgBean();
                            result.setResult(userInfo.getResult());
                            result.setCode(2000);
                            emitter.onNext(result);
                            emitter.onComplete();
                        }

                    }
                };
        return createObservableOnSubscribe(update);
    }


    @Override
    public Observable<ResultMsgBean> createCommentParams(final int findId,
                                                         final FindCommentBean commentInfo,
                                                         final String commentContent,
                                                         final List<FindCommentBean> commentList,
                                                         final int position) {
        return getUserInfoByToken()
                .map(new Function<UserInfoEntity, UserInfoEntity>() {
                    @Override
                    public UserInfoEntity apply(UserInfoEntity userInfoEntity) throws Exception {
                        if (userInfoEntity == null) {
                            throw new UserException();
                        } else {
                            return userInfoEntity;
                        }
                    }
                })
                .flatMap(new Function<UserInfoEntity, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(UserInfoEntity userInfo) throws Exception {
                        if (commentInfo == null ||
                                (commentInfo != null && userInfo.getUserId() == commentInfo.getComUserId())) {
                            return setCommentParams(findId, commentContent, commentList);
                        } else {
                            //用户点击评论内容进入 List<FindCommentBean>
                            return setReplyCommentParams(commentInfo, commentContent, commentList, position);
                        }
                    }
                });
    }

    /**
     * 用户点击自己时（仅在点击评论内容时才会发生），EditText hint 内容不变
     */
    public Observable<ResultMsgBean> checkIsOwn(final FindCommentBean commentInfo) {
        if (getToken() == null) {
            return createObservable(Observable.create(new ObservableOnSubscribe<ResultMsgBean>() {
                @Override
                public void subscribe(ObservableEmitter<ResultMsgBean> emitter) throws Exception {
                    String hint = "回复 " + commentInfo.getComUserName();
                    ResultMsgBean result = new ResultMsgBean();
                    result.setResult(hint);
                    result.setCode(0);//默认创建评论
                    emitter.onNext(result);
                    emitter.onComplete();
                }
            }));
        } else {
            return getUserInfoByToken()
                    .map(new Function<UserInfoEntity, ResultMsgBean>() {
                        @Override
                        public ResultMsgBean apply(UserInfoEntity userInfo) throws Exception {
                            String hint;
                            ResultMsgBean result = new ResultMsgBean();
                            if (userInfo != null && (userInfo.getUserId() == commentInfo.getComUserId())) {
                                hint = "说点什么呗~";
                                result.setCode(0);//默认创建评论
                            } else {
                                hint = "回复 " + commentInfo.getComUserName();
                                result.setCode(1);//回复评论
                            }

                            result.setResult(hint);

                            return result;
                        }
                    });
        }


    }


    /**
     * 获取视频第一帧图片
     * 保存视频文件
     * 保存视频第一帧图片
     */
    private Observable<Map<String, RequestBody>> setVideoParams(final String path, final String description) {

        if (mMetadataRetriever == null) {
            mMetadataRetriever = new MediaMetadataRetriever();
        }

        ObservableOnSubscribe<Map<String, RequestBody>> videoImg = emitter -> {
            mMetadataRetriever.setDataSource(path);
            Bitmap frameAtTime = mMetadataRetriever.getFrameAtTime();
            mMetadataRetriever.release();

            String OutPutFileDirPath = context.getCacheDir().getPath() + "/temp_img";

            File appDir = new File(OutPutFileDirPath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File videoImageFile = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(videoImageFile);
                frameAtTime.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<String, RequestBody> params = new HashMap<>();

            File videoFile = new File(path);
            params.put("videoFile\";filename=\"" + new String(videoFile.getName().getBytes(), "utf-8"), RequestBody.create(MediaType.parse("multipart/form-data"), videoFile));
            params.put("videoPic\";filename=\"" + new String(videoImageFile.getName().getBytes(), "utf-8"), RequestBody.create(MediaType.parse("multipart/form-data"), videoImageFile));

            if (description != null && description.length() > 0) {
                params.put(VAL_DESCRIPTION, RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), description));
            }
            saveTempFindInfo(description, null, videoFile.getPath(), videoImageFile.getPath());
            emitter.onNext(params);
            emitter.onComplete();
        };
        return createObservableOnSubscribe(videoImg);
    }

    /**
     * 上传图片
     */
    private Observable<Map<String, RequestBody>> setImageParams(final List<String> pathList, final String description) {


        ObservableOnSubscribe<Map<String, RequestBody>> image = new ObservableOnSubscribe<Map<String, RequestBody>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, RequestBody>> emitter) throws Exception {

                Map<String, RequestBody> map = new HashMap<>();
                if (description != null && description.length() > 0) {
                    map.put(VAL_DESCRIPTION, RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), description));
                }

                ArrayList<FindImageBean> picList = new ArrayList<>();

                List<File> files = Luban.with(context)
                        .ignoreBy(1024)
                        .load(pathList)
                        .get();

                int size = files.size();
                for (int i = 0; i < size; i++) {
                    File file = files.get(i);
                    picList.add(new FindImageBean(file.getPath(), file.getPath()));
                    String fileName = URLEncoder.encode(file.getName(), "UTF-8");
                    map.put("picFiles\";filename=\"" + fileName, RequestBody.create(MediaType.parse("multipart/form-data"), file));
                }
                saveTempFindInfo(description, picList, null, null);
                emitter.onNext(map);
                emitter.onComplete();
            }
        };
        return createObservableOnSubscribe(image);
    }

    /**
     * 开始上传视频
     */
    private Observable<ResultMsgBean> createVideo(final List<String> path, final String desc) {
        Observable<ResultMsgBean> createVideo = getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<Map<String, RequestBody>>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        return setVideoParams(path.get(0), desc);
                    }
                })
                .flatMap((Function<Map<String, RequestBody>, ObservableSource<ResultMsgBean>>) requestBodyMap -> {
                    Map<String, String> token = new HashMap<>();
                    token.put(VAL_TOKEN, getToken());
                    return createObservable(mFindApi.createDynamic(token, requestBodyMap));
                });
        return createVideo;
    }

    /**
     * 开始上传图(文）
     */
    private Observable<ResultMsgBean> createImage(final List<String> path, final String desc) {
        Observable<ResultMsgBean> createImage = getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<Map<String, RequestBody>>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        return setImageParams(path, desc);
                    }
                })
                .flatMap(new Function<Map<String, RequestBody>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, RequestBody> requestBodyMap) throws Exception {
                        Map<String, String> token = new HashMap<>();
                        token.put(VAL_TOKEN, getToken());
                        return createObservable(mFindApi.createDynamic(token, requestBodyMap));
                    }
                });
        return createImage;
    }

    /**
     * 仅发布文字
     */
    private Observable<ResultMsgBean> createDesc(final String description) {
        Observable<ResultMsgBean> createDesc = getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> header) throws Exception {

                        if (header.size() == 0) {
                            throw new UserException();
                        } else {
                            Map<String, RequestBody> map = new HashMap<>();
                            if (description != null && description.length() > 0) {
                                map.put("dynamicDec", RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), description));
                            }
                            saveTempFindInfo(description, new ArrayList<FindImageBean>(), null, null);
                            return createObservable(mFindApi.createDynamic(header, map));
                        }

                    }
                });

        return createDesc;
    }


    /**
     * 保存临时数据 显示在动态顶部
     *
     * @param description
     * @param picList
     * @param videoPath
     * @param videoImg
     */
    public void saveTempFindInfo(final String description, final ArrayList<FindImageBean> picList,
                                 final String videoPath, final String videoImg) {

        getUserInfoByToken()
                .subscribe(new ObserverCallback<UserInfoEntity>(mBuilder.build()) {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        try {
                            FindInfoBean findInfo = new FindInfoBean();
                            findInfo.setUserNickName(userInfoEntity.getUserNickName());
                            findInfo.setIsOwn(1);
                            findInfo.setUserId((int) userInfoEntity.getUserId());
                            findInfo.setUserHead(userInfoEntity.getUserOriginalHead());
                            // findInfo.setDynamicId(new Random().nextInt(999));

                            if (description != null && description.length() > 0) {
                                findInfo.setDynamicDec(description);
                            }

                            if (picList != null) {
                                findInfo.setDynamicPic(picList);
                            }
                            if (videoPath != null && videoImg != null) {
                                findInfo.setDynamicVideoAddr(videoPath);
                                findInfo.setDynamicVideoPic(videoImg);
                            }
                            EventBus.getDefault().post(findInfo);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 评论成功，更新到评论列表
     */
    private Observable<ResultMsgBean> updateCommentList(final List<FindCommentBean> commentList,
                                                        final UserInfoEntity userInfo,
                                                        final String commentContent) {
        ObservableOnSubscribe<ResultMsgBean> resultMsgBeanObservableOnSubscribe
                = new ObservableOnSubscribe<ResultMsgBean>() {
            @Override
            public void subscribe(ObservableEmitter<ResultMsgBean> emitter) throws Exception {
                try {
                    FindCommentBean commentBean = new FindCommentBean();
                    final String pattern = "yyyy-MM-dd HH:mm:ss";
                    commentBean.setFormatDate(TimeUtil.showTime(new Date(), pattern));
                    commentBean.setComUserHead(userInfo.getUserOriginalHead());
                    commentBean.setComContent(commentContent);
                    commentBean.setComUserName(userInfo.getUserNickName());
                    commentBean.setComUserId((int) userInfo.getUserId());
                    commentList.add(0, commentBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                } finally {
                    ResultMsgBean result = new ResultMsgBean();
                    result.setResult(userInfo.getResult());
                    emitter.onNext(result);
                    emitter.onComplete();
                }
            }
        };
        return createObservableOnSubscribe(resultMsgBeanObservableOnSubscribe);
    }

    @Override
    public Observable<FindListBean> startSearchFind(final int currentPage, final String keyName) {
        if (mHomePageApi == null) {
            mHomePageApi = create(HomePageApi.class);
        }
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FindListBean>>)
                        token -> createObservable(mHomePageApi.searchFind(token, 1, keyName, currentPage)));
    }

    @Override
    public Observable<FindListBean> getMyFindInfoList() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FindListBean>>)
                        token -> createObservable(mFindApi.getMyFindInfo(token)));
    }

}
