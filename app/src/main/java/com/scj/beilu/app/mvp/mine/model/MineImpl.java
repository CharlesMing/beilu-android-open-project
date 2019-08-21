package com.scj.beilu.app.mvp.mine.model;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.api.UserApi;
import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.bean.AboutUsBean;
import com.scj.beilu.app.mvp.mine.bean.DistrictInfoBean;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansListBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.mine.bean.TotalCountBean;
import com.scj.beilu.app.mvp.mine.bean.UpdateAvatarResultBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.util.GetJsonDataUtil;

import org.json.JSONArray;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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


/**
 * @author Mingxun
 * @time on 2019/2/20 11:09
 */
public class MineImpl extends AddressInfoImpl implements IMine {
    private UserApi mUserApi;

    public MineImpl(Builder builder) {
        super(builder);
        mUserApi = create(UserApi.class);
    }

    @Override
    public Observable<TotalCountBean> getTotalCount() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<TotalCountBean>>) token -> {
                    if (token.size() == 0)
                        throw new UserException();
                    else {
                        return createObservable(mUserApi.getTotalCount(token));
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> setUserInfoParams(final Map<String, Object> params) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0)
                            throw new UserException();
                        else {
                            return createObservable(mUserApi.modifyUserInfo(token, params));
                        }
                    }
                });
    }


    //修改缓存里面的内容
    @Override
    public Observable<UserInfoEntity> updateUserInfo(final UserInfoEntity userInfoEntity) {
        if (mInfoEntityDao == null) {
            mInfoEntityDao = getDaoSession().getUserInfoEntityDao();
        }
        ObservableOnSubscribe<UserInfoEntity> userInfoEntityObservableOnSubscribe =
                emitter -> {
                    try {
                        mInfoEntityDao.update(userInfoEntity);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                    emitter.onNext(userInfoEntity);
                    emitter.onComplete();
                };
        return createObservableOnSubscribe(userInfoEntityObservableOnSubscribe);
    }

    @Override
    public Observable<UserInfoEntity> setUserPhotoParam(final String filePath) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<UpdateAvatarResultBean>>) header -> {
                    Map<String, RequestBody> map = new HashMap<>();
                    File file = new File(filePath);
                    map.put("file\";filename=\"" + file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                    return createObservable(mUserApi.modifyUserHead(header, map));
                }).flatMap((Function<UpdateAvatarResultBean, ObservableSource<UserInfoEntity>>) updateResult ->
                        getUserInfoByToken()
                                .flatMap((Function<UserInfoEntity, ObservableSource<UserInfoEntity>>) userInfoEntity -> {
                                    userInfoEntity.setUserCompressionHead(updateResult.getUserHead());
                                    userInfoEntity.setUserOriginalHead(updateResult.getUserHead());
                                    userInfoEntity.setResult(updateResult.getResult());
                                    return updateUserInfo(userInfoEntity);
                                }));
    }


    @Override
    public Observable<DictrictOptionBean> dealWithDistrictData() {
        return getDistrictInfo();
    }


    @Override
    public Observable<MeFocusListBean> GetMyFocusNumberList(final int currentPage) {

        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<MeFocusListBean>>() {
                    @Override
                    public ObservableSource<MeFocusListBean> apply(Map<String, String> token) throws Exception {
                        return createObservable(mUserApi.GetMyFocusNumber(token, currentPage));
                    }
                });

    }

    /**
     * 1.读取本地资源文件
     * 2.解析数据
     * 3.显示结果
     *
     * @return
     */
    private Observable<DictrictOptionBean> getDistrictInfo() {
        ObservableOnSubscribe<DictrictOptionBean> dealWithSub =
                new ObservableOnSubscribe<DictrictOptionBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<DictrictOptionBean> emitter) throws Exception {

                        DictrictOptionBean optionBean = new DictrictOptionBean();
                        ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
                        try {

                            String data = new GetJsonDataUtil().getJson(context, "province.json");//获取assets目录下的json文件数据
                            ArrayList<DistrictInfoBean> jsonBean = parseData(data);//用Gson 转成实体
                            optionBean.setOptions1Items(jsonBean);

                            for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                                ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                                ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                                for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                                    String cityName = jsonBean.get(i).getCity().get(c).getName();
                                    cityList.add(cityName);//添加城市
                                    ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                                    city_AreaList.addAll(jsonBean.get(i).getCity().get(c).getArea());
                                    province_AreaList.add(city_AreaList);//添加该省所有地区数据
                                }
                                /**
                                 * 添加城市数据
                                 */
                                options2Items.add(cityList);
                            }

                            optionBean.setOptions2Items(options2Items);
                        } catch (Exception e) {
                            e.printStackTrace();
                            emitter.onError(e);
                        } finally {
                            emitter.onNext(optionBean);
                            emitter.onComplete();
                        }
                    }
                };

        return createObservableOnSubscribe(dealWithSub);
    }

    /**
     * 设置关注与取消关注
     *
     * @param userId
     * @return
     */
    @Override
    public Observable<ResultMsgBean> setAttentionParams(final List<FocusUserInfoBean> list, final int position, final int userId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else
                        return createObservable(mUserApi.setUserFocus(header, userId));
                })
                .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean ->
                        updateAttention(list, position, resultMsgBean));

    }


    @Override
    public Observable<ResultMsgBean> setFansAttentionParams(final List<MeFansInfoBean> list, final int position, final int userId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else
                        return createObservable(mUserApi.setUserFocus(header, userId));
                })
                .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean ->
                        updateAttentionStatus(list, position, resultMsgBean));
    }

    /**
     * 我的粉丝
     */
    @Override
    public Observable<MeFansListBean> getMyFans(final int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<MeFansListBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else
                        return createObservable(mUserApi.getMyFans(header, currentPage));
                });
    }

    @Override
    public Observable<AboutUsBean> getAboutUsInfo() {
        return createObservable(mUserApi.getAboutUsInfo());
    }

    /**
     * 提交反馈信息
     */
    @Override
    public Observable<ResultMsgBean> addFeedBack(final Map<String, String> params) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mUserApi.addFeedBack(header, params));
                    }
                });
    }

    @Override
    public Observable<String> getCacheSize() {
        ObservableOnSubscribe<String> onSubscribe =
                emitter -> {
                    try {
//                        String path = context.getCacheDir().getPath();
//                        File file = new File(path);
//                        emitter.onNext(getCacheSize(file));
                        emitter.onNext(getTotalCacheSize(context));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }


    @Override
    public Observable<String> clearCache() {
        ObservableOnSubscribe<String> onSubscribe =
                emitter -> {
                    try {
                        GlideApp.get(context).clearDiskCache();
                        clearAllCache(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        emitter.onNext("清除成功");
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    private ArrayList<DistrictInfoBean> parseData(String result) {//Gson 解析
        ArrayList<DistrictInfoBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                DistrictInfoBean entity = gson.fromJson(data.optJSONObject(i).toString(), DistrictInfoBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 更新关注状态
     *
     * @return
     */
    private Observable<ResultMsgBean> updateAttention(final List<FocusUserInfoBean> list,
                                                      final int position,
                                                      final ResultMsgBean resultMsgBean) {
        ObservableOnSubscribe<ResultMsgBean> sub =
                emitter -> {
                    try {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            if (i == position) {
                                FocusUserInfoBean focusUserInfoBean = list.get(i);
//                                focusUserInfoBean.setIsFans(focusUserInfoBean.getIsFans() == 1 ? 0 : 1);
                                focusUserInfoBean.setIsFocus(focusUserInfoBean.getIsFocus() == 1 ? 0 : 1);
                                list.set(i, focusUserInfoBean);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        emitter.onError(e);
                    } finally {
                        emitter.onNext(resultMsgBean);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(sub);
    }


    /**
     * 更新粉丝关注状态
     */
    private Observable<ResultMsgBean> updateAttentionStatus(final List<MeFansInfoBean> beanList,
                                                            final int position,
                                                            final ResultMsgBean result) {
        ObservableOnSubscribe<ResultMsgBean> notifyObser = emitter -> {
            try {
                int size = beanList.size();
                for (int i = 0; i < size; i++) {
                    if (i == position) {
                        MeFansInfoBean meFansInfoBean = beanList.get(i);
                        meFansInfoBean
                                .setIsFocus(meFansInfoBean.getIsFocus() == 1 ? 0 : 1);
                        beanList.set(i, meFansInfoBean);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            } finally {
                emitter.onNext(result);
                emitter.onComplete();
            }
        };
        return createObservableOnSubscribe(notifyObser);
    }

    public String getTotalCacheSize(Context context) throws Exception {

        //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        long cacheSize = getFolderSize(context.getCacheDir());
        //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
            //下面两句清理webview网页缓存.但是每次执行都报false,我用的是魅蓝5.1的系统，后来发现且/data/data/应用package目录下找不到database文///件夹 不知道是不是个别手机的问题，
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     */
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}
