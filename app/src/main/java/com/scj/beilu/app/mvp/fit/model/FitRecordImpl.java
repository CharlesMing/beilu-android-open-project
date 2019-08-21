package com.scj.beilu.app.mvp.fit.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.R;
import com.scj.beilu.app.api.FitApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthValBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordInfoResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitUserSexBean;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Mingxun
 * @time on 2019/3/11 14:49
 */
public class FitRecordImpl extends BaseLoadUserInfoDelegate implements IFitRecord {

    private FitApi mFitApi;
//    private String mGenerateImgPath;//生成后的图片路径，用于上传到服务器成功后，删除当前图片

    public FitRecordImpl(Builder builder) {
        super(builder);
        mFitApi = create(FitApi.class);
    }

    @Override
    public Observable<ResultMsgBean> setSex(final int sex) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header ->
                        createObservable(mFitApi.setSexOfFitRecord(header, sex)));
    }

    @Override
    public Observable<FitUserSexBean> getSexInfo() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FitUserSexBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mFitApi.getFitSexInfo(header));
                    }
                });
    }

    @Override
    public Observable<FitRecordGirthListBean> getUserRecord() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FitRecordGirthListBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mFitApi.getUserFitRecord(header));
                    }
                })
                .flatMap((Function<FitRecordGirthListBean, ObservableSource<FitRecordGirthListBean>>) fitRecordGirthListBean ->
                        dealWithLineChart(fitRecordGirthListBean));
    }

    @Override
    public Observable<ResultMsgBean> addFitRecord(final FitRecordGirthInfoBean girthInfoBean, final String recordKey, final float recordVal) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        Map<String, Float> params = new HashMap<>();
                        params.put(recordKey, recordVal);
                        return createObservable(mFitApi.addFitRecord(token, params));
                    }
                })
                .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean ->
                        addForResult(girthInfoBean, resultMsgBean, recordVal));
    }

    @Override
    public Observable<FitRecordInfoResultBean> getRecentFitRecord() {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FitRecordInfoResultBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mFitApi.getRecentFitRecord(header));
                    }
                });
    }

    @Override
    public Observable<FitRecordImgResultBean> addFitRecordImg(File filePath) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FitRecordImgResultBean>>) tokens -> {
                    if (tokens.size() == 0) {
                        throw new UserException();
                    } else {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), filePath);
                        String fileName = URLEncoder.encode(filePath.getName(), "UTF-8");
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileName, requestBody);
                        return createObservable(mFitApi.addFitRecordForImg(tokens, filePart));
                    }
                });
    }

    @Override
    public Observable<FitRecordImgListBean> getFitRecordImgList(final int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<FitRecordImgListBean>>) tokens -> {
                    if (tokens.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mFitApi.getFitRecordImgList(tokens, currentPage));
                    }
                });
    }

    @Override
    public Observable<FitRecordImgResultBean> generatePicture(View view, boolean isWaterMark) {
        if (isWaterMark) {//添加水印保存到本地
            return generate(view, true);
        } else {//上传到服务器，不添加水印
            return generate(view, false)
                    .flatMap((Function<FitRecordImgResultBean, ObservableSource<FitRecordImgResultBean>>) generateResultBean ->
                            addFitRecordImg(new File(generateResultBean.getOrgFile())))
                    .map(fitRecordImgResultBean -> {//上传成功后删除本地
                        if (fitRecordImgResultBean.getOrgFile() != null) {
                            File imgFile = new File(fitRecordImgResultBean.getOrgFile());
                            if (imgFile.exists()) {
                                imgFile.delete();
                            }
                        }
                        return fitRecordImgResultBean;
                    });
        }
    }

    @Override
    public Observable<ResultMsgBean> deleteImg(final long recordPicId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) header -> {
                    if (header.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mFitApi.deleteImg(header, recordPicId));
                    }
                });

    }

    @Override
    public Observable<FitRecordImgResultBean> shareToImg(View view, int scene) {
        return generate(view, true)
                .flatMap((Function<FitRecordImgResultBean, ObservableSource<FitRecordImgResultBean>>) fitRecordImgResultBean -> {
                    return generateImg(fitRecordImgResultBean, scene);
                });
    }

    private final int THUMB_SIZE = 150;

    private Observable<FitRecordImgResultBean> generateImg(FitRecordImgResultBean recordImgResultBean, int scene) {
        ObservableOnSubscribe<FitRecordImgResultBean> onSubscribe = emitter -> {
            String path = recordImgResultBean.getOrgFile();
            File file = new File(path);
            if (!file.exists()) {
                return;
            }

            WXImageObject imgObj = new WXImageObject();
            imgObj.setImagePath(path);

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;

            Bitmap bmp = BitmapFactory.decodeFile(path);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            msg.thumbData = bmpToByteArray(thumbBmp, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "img" + System.currentTimeMillis();
            req.message = msg;
            req.scene = scene;
            recordImgResultBean.setReq(req);
            emitter.onNext(recordImgResultBean);
            emitter.onComplete();
        };
        return createObservableOnSubscribe(onSubscribe);
    }


    byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 添加水印
     *
     * @param view        当前显示的View
     * @param isWaterMark 是否添加水印
     * @return
     */
    private Observable<FitRecordImgResultBean> generate(final View view, final boolean isWaterMark) {
        ObservableOnSubscribe<FitRecordImgResultBean> onSubscribe =
                emitter -> {
                    FitRecordImgResultBean generate = new FitRecordImgResultBean();
                    try {
                        view.buildDrawingCache();

                        Bitmap bitmap = view.getDrawingCache();
                        Bitmap bitmapFromView = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmapFromView);
                        canvas.drawBitmap(bitmap, 0, 0, null);

                        if (isWaterMark) {
                            Bitmap waterMark = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_mark);
                            int left = bitmapFromView.getWidth() - waterMark.getWidth() - 20;
                            int top = bitmapFromView.getHeight() - waterMark.getHeight() - 20;
                            //在画布上绘制水印图片
                            canvas.drawBitmap(waterMark, left, top, null);
                        }

//                            canvas.save(Canvas.ALL_SAVE_FLAG);
                        canvas.save();
                        // 存储
                        canvas.restore();

                        // 保存图片到本地
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                        Date curTime = new Date();
                        String fileName = new StringBuilder("beilu").append(dateFormat.format(curTime)).append(".jpg").toString();
                        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/beilu";
                        String mGenerateImgPath = saveImage(filePath, fileName, bitmapFromView, isWaterMark);
                        generate.setOrgFile(mGenerateImgPath);
                        generate.setComFile(mGenerateImgPath);
                        generate.setResult("保存成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        generate.setResult("保存失败");
                    } finally {
                        generate.setCode(2000);

                        emitter.onNext(generate);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    /**
     * 根据路径和文件名保存文件
     *
     * @param folderName
     * @param fileName
     * @param bm
     */
    private String saveImage(String folderName, String fileName, Bitmap bm, boolean isWaterMark) {
        if (null == bm) {
            // 保存失败，直接返回
            return null;
        }
        // 先判断文件夹是否存在，不存在要先创建
        File dir = new File(folderName);
        if (!dir.exists() && !dir.mkdirs()) {
            return null;
        }
        File file = new File(dir, fileName);
        OutputStream out = null;
        try {
            // 封装输出流
            out = new BufferedOutputStream(new FileOutputStream(file));
            if (!bm.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                return null;
            }

            // 转换为数据流后回收bitmap
            if (!bm.isRecycled()) {
                bm.recycle();
            }

            if (isWaterMark) {
                // 扫描新增图片文件
                Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                final Uri fileContentUri = Uri.fromFile(file);
                mediaScannerIntent.setData(fileContentUri);
                context.sendBroadcast(mediaScannerIntent);
            }

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    // 关闭输出流
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Observable<FitRecordGirthListBean> dealWithLineChart(final FitRecordGirthListBean recordInfo) {
        ObservableOnSubscribe<FitRecordGirthListBean> onSubscribe =
                emitter -> {
                    List<FitRecordGirthInfoBean> commons = recordInfo.getRecordCommons();
                    try {
                        final String pattern = "yyyy-MM-dd HH:mm:ss";
                        final SimpleDateFormat df = new SimpleDateFormat(pattern);
                        final String pattern1 = "MM/dd#yyyy";
                        final SimpleDateFormat format = new SimpleDateFormat(pattern1);

                        int size = commons.size();
                        for (int i = 0; i < size; i++) {
                            FitRecordGirthInfoBean girthInfoBean = commons.get(i);
                            List<FitRecordGirthValBean> recordData = girthInfoBean.getRecordData();
                            int count = recordData.size();
                            if (count > 0) {
                                ArrayList<Entry> values = new ArrayList<>();
                                for (int j = 0; j < count; j++) {//添加值
                                    FitRecordGirthValBean valBean = recordData.get(j);
                                    Date parse = df.parse(valBean.getRecordDate());
                                    valBean.setRecordDate(format.format(parse));//转换时间格式
                                    recordData.set(j, valBean);
                                    values.add(new Entry(j, valBean.getRecordData()));
                                }
                                // 添加折线
                                List<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(getLineDataSet(values));
                                // 绘制折线
                                LineData data = new LineData(dataSets);

                                girthInfoBean.setRecordData(recordData);//重新保存设置的时间格式
                                girthInfoBean.setLineData(data);

                                commons.set(i, girthInfoBean);//保存到对象
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //将数据重组
                        recordInfo.setRecordCommons(commons);
                        emitter.onNext(recordInfo);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    @NonNull
    private LineDataSet getLineDataSet(ArrayList<Entry> values) {
        LineDataSet lineDataSet = new LineDataSet(values, "");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setColor(ColorTemplate.getHoloBlue());
        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.chart_val_color));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setValueTextColor(ContextCompat.getColor(context, R.color.chart_val_color));
        lineDataSet.setValueTextSize(13f);
        lineDataSet.setDrawValues(true);
        return lineDataSet;
    }

    @NonNull
    private Observable<ResultMsgBean> addForResult(final FitRecordGirthInfoBean girthInfoBean,
                                                   final ResultMsgBean resultMsgBean,
                                                   final float val) {

        ObservableOnSubscribe<ResultMsgBean> onSubscribe =
                new ObservableOnSubscribe<ResultMsgBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResultMsgBean> emitter) throws Exception {
                        final String pattern = "MM/dd#yyyy";
                        List<FitRecordGirthValBean> recordData = girthInfoBean.getRecordData();
                        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                        String date = dateFormat.format(new Date());

                        FitRecordGirthValBean girthValBean = new FitRecordGirthValBean();
                        girthValBean.setRecordData(val);
                        girthValBean.setRecordDate(date);
                        recordData.add(girthValBean);
                        int count = recordData.size();
                        girthInfoBean.setRecordData(recordData);

                        ArrayList<Entry> values = new ArrayList<>();
                        for (int j = 0; j < count; j++) {//添加值
                            values.add(new Entry(j, recordData.get(j).getRecordData()));
                        }
                        // 添加折线
                        List<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(getLineDataSet(values));

                        // 绘制折线
                        LineData data = new LineData(dataSets);
                        girthInfoBean.setLineData(data);
                        emitter.onNext(resultMsgBean);
                        emitter.onComplete();
                    }
                };

        return createObservableOnSubscribe(onSubscribe);
    }

    public Observable<List<FitRecordImgInfoBean>> startShowSelector(final List<FitRecordImgInfoBean> imgList, final boolean isShow) {
        ObservableOnSubscribe<List<FitRecordImgInfoBean>> onSubscribe =
                new ObservableOnSubscribe<List<FitRecordImgInfoBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<FitRecordImgInfoBean>> emitter) throws Exception {
                        try {
                            int size = imgList.size();
                            for (int i = 0; i < size; i++) {
                                FitRecordImgInfoBean infoBean = imgList.get(i);
                                infoBean.setShow(isShow);
                                infoBean.setSelected(false);
                                imgList.set(i, infoBean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(imgList);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    public Observable<String> setSelector(final List<String> imgPathList, final List<FitRecordImgInfoBean> imgList, final int position) {
        ObservableOnSubscribe<String> onSubscribe =
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        String hint = "";
                        try {
                            int size = imgList.size();
                            for (int i = 0; i < size; i++) {
                                if (i == position) {
                                    FitRecordImgInfoBean infoBean = imgList.get(position);
                                    String path = infoBean.getPicOrgAddr();
                                    if (imgPathList.contains(path)) {//已添加
                                        imgPathList.remove(path);
                                        infoBean.setSelected(false);
                                    } else {
                                        if (imgPathList.size() == 2) {
                                            hint = "最多只能添加2张图片哦";
                                        } else {
                                            imgPathList.add(path);
                                            infoBean.setSelected(true);
                                        }
                                    }
                                    imgList.set(position, infoBean);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(hint);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

}
