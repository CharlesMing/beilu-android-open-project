package com.scj.beilu.app.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.scj.beilu.app.util.DownLoadUtils;
import com.scj.beilu.app.util.SystemParams;


public class DownloadManagerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

            int status = DownLoadUtils.getInstance(context).checkStatus(downloadApkId);
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    //获得下载暂停的原因等信息
                    Toast.makeText(context, "暂停下载" + DownLoadUtils.getInstance(context).getPausedReason(downloadApkId), Toast.LENGTH_LONG).show();
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    //获得下载延迟的原因
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    installApk(context, downloadApkId);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    // 获得下载失败的原因
                    Toast.makeText(context, "暂停下载" + DownLoadUtils.getInstance(context).getFailedReason(downloadApkId), Toast.LENGTH_LONG).show();
                    break;
            }
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {

            //点击通知栏取消下载
//            Toast.makeText(context,"通知栏被点击",Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 安装apk
     */
    private void installApk(Context context, long downloadId) {

        long downId = SystemParams.getInstance().getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
        if (downloadId == downId) {
            DownloadManager downManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = downManager.getUriForDownloadedFile(downloadId);
            SystemParams.getInstance().setString("downloadApk", downloadUri.getPath());
            if (downloadUri != null) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(downloadUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if ((Build.VERSION.SDK_INT >= 24)) {//判读版本是否在7.0以上
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                }
                context.startActivity(install);
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
