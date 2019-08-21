package com.scj.beilu.app.mvp.common.preview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.request.FutureTarget;
import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.scj.beilu.app.GlideApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Mingxun
 * @time on 2019/4/16 20:19
 */
public class PhotoInfoImpl extends LoadDelegate implements IPhotoInfo {

    public PhotoInfoImpl(Builder builder) {
        super(builder);
    }

    @Override
    public Observable<String> downloadPhoto(final String path) {
        ObservableOnSubscribe<String> onSubscribe =
                emitter -> {
                    FutureTarget<Bitmap> bitmapFutureTarget = GlideApp.with(context)
                            .asBitmap()
                            .load(path)
                            .submit();
                    try {
                        Bitmap bitmap = bitmapFutureTarget.get();
                        String OutPutFileDirPath = Environment.getExternalStorageDirectory() + "/北鹿";
                        File appDir = new File(OutPutFileDirPath);
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        try {
                            FileOutputStream fos = new FileOutputStream(destFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String result = "图片已保存至" + destFile.getAbsolutePath() + "文件夹";
                        emitter.onNext(result);
                        // 最后通知图库更新
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(destFile.getPath()))));
                    } catch (InterruptedException e) {
                        //Log.d(TAG, "Interrupted waiting for background downloadOnly", e);
                    } catch (ExecutionException e) {
                        //Log.d(TAG, "Got ExecutionException waiting for background downloadOnly", e);
                    }

                    emitter.onComplete();
                };
        return createObservableOnSubscribe(onSubscribe);
    }

}
