package com.scj.beilu.app.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.scj.beilu.app.R;
import com.scj.beilu.app.mvp.notify.MsgContentBean;
import com.scj.beilu.app.ui.act.MsgManagerAct;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.v4.app.NotificationCompat.FLAG_AUTO_CANCEL;

/**
 * @author Mingxun
 * @time on 2019/4/8 16:36
 */
public class MuPushReceiver extends MessageReceiver {
    private int clickNotificationCode = 100;

    @Override
    protected void onMessage(final Context context, final CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);

        ObservableOnSubscribe<MsgContentBean> onSubscribe =
                new ObservableOnSubscribe<MsgContentBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<MsgContentBean> emitter) throws Exception {
                        try {
                            MsgContentBean msgContentBean = new Gson().fromJson(cPushMessage.getContent(), MsgContentBean.class);
                            emitter.onNext(msgContentBean);
                            emitter.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

        Observable.create(onSubscribe)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MsgContentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MsgContentBean msgContentBean) {
                        createNotificationChannel(context, cPushMessage, msgContentBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public PendingIntent buildClickContent(Context context, MsgContentBean content) {
        Intent clickIntent = new Intent(context, MsgManagerAct.class);
        clickIntent.putExtra(MsgManagerAct.EXTRA_VAL, content.getMsgType());
        return PendingIntent.getActivity(context, clickNotificationCode, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel(Context context, CPushMessage message, MsgContentBean content) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "1";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(mChannel);
        }


        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(message.getTitle())
                .setContentText(content.getMsgContent())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();

        notification.flags = FLAG_AUTO_CANCEL;
        notification.contentIntent = buildClickContent(context, content);
//            notification.deleteIntent = buildDeleteContent(context, message);
        mNotificationManager.notify(message.hashCode(), notification);
    }
}
