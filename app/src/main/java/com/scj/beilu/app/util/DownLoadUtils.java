package com.scj.beilu.app.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * @author Mingxun
 * @time on 2019/4/25 17:21
 */
public class DownLoadUtils {
    private Context mContext;
    private DownloadManager mDownloadManager;
    private static volatile DownLoadUtils instance;

    private DownLoadUtils(Context context) {
        this.mContext = context.getApplicationContext();
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 获取单例对象
     *
     * @param context
     * @return
     */
    public static DownLoadUtils getInstance(Context context) {

        if (instance == null) {
            synchronized (DownLoadUtils.class) {
                if (instance == null) {
                    instance = new DownLoadUtils(context);
                    return instance;
                }
            }
        }
        return instance;
    }

    /**
     * 下载
     *
     * @param uri
     * @param title
     * @param description
     * @param appName
     * @return downloadId
     */
    public long download(String uri, String title, String description, String appName) {

        //1.构建下载请求
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(uri));
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        /**设置漫游状态下是否可以下载*/
        downloadRequest.setAllowedOverRoaming(false);
        /**如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理，
         我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true.*/
        downloadRequest.setVisibleInDownloadsUi(true);
        //文件保存位置
        //file:///storage/emulated/0/Android/data/your-package/files/Download/appName.apk
        downloadRequest.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, appName + ".apk");
        // 设置一些基本显示信息
        downloadRequest.setTitle(title);
        downloadRequest.setDescription(description);
        //req.setMimeType("application/vnd.android.package-archive");
        return mDownloadManager.enqueue(downloadRequest);//异步请求
    }

    /**
     * 获取文件下载路径
     *
     * @param downloadId
     * @return
     */
    public String getDownloadPath(long downloadId) {

        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                }
            } finally {
                c.close();
            }
        }

        return null;
    }

    /**
     * 获取文件保存的地址
     *
     * @param downloadId
     * @return
     */
    public Uri getDownloadUri(long downloadId) {
        return mDownloadManager.getUriForDownloadedFile(downloadId);
    }

    public DownloadManager getDownloadManager() {
        return mDownloadManager;
    }

    /**
     * 获取下载状态
     *
     * @param downloadId
     * @return
     */
    public int getDownloadStatus(long downloadId) {

        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }

    /**
     * 判断下载管理程序是否可用
     *
     * @return
     */
    public boolean canDownload() {

        try {
            int state = mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 进入 启用/禁用 下载管理程序界面
     */
    public void skipToDownloadManager() {

        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        mContext.startActivity(intent);
    }


    /**
     * 进入 启用/禁用 下载管理程序界面
     */
    public int checkStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        int status = -1;
        if (c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

        }
        return status;
    }


    /**
     * 查询当前下载暂停失败的原因
     */

    public String getPausedReason(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        query.setFilterByStatus(DownloadManager.STATUS_PAUSED);
        // Query the Download Manager for paused downloads.
        Cursor pausedDownloads = mDownloadManager.query(query);

        // Find the column indexes for the data we require.
        int reasonIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_REASON);
        int titleIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TITLE);
        int fileSizeIdx =
                pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        int bytesDLIdx =
                pausedDownloads.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);

        // Iterate over the result Cursor.
        while (pausedDownloads.moveToNext()) {
            // Extract the data we require from the Cursor.
            String title = pausedDownloads.getString(titleIdx);
            int fileSize = pausedDownloads.getInt(fileSizeIdx);
            int bytesDL = pausedDownloads.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = pausedDownloads.getInt(reasonIdx);
            String reasonString = "Unknown";
            switch (reason) {
                case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                    reasonString = "Waiting for WiFi";
                    break;
                case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                    reasonString = "Waiting for connectivity";
                    break;
                case DownloadManager.PAUSED_WAITING_TO_RETRY:
                    reasonString = "Waiting to retry";
                    break;
                default:
                    break;
            }
            // Construct a status summary
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n");
            sb.append(reasonString).append("\n");
            sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);

            return  sb.toString();
        }
        return "";
    }

    /**
     * 查询当前下载暂停失败的原因
     */

    public String getFailedReason(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        query.setFilterByStatus(DownloadManager.STATUS_FAILED);
        // Query the Download Manager for paused downloads.
        Cursor pausedDownloads = mDownloadManager.query(query);

        // Find the column indexes for the data we require.
        int reasonIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_REASON);
        int titleIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TITLE);
        int fileSizeIdx =
                pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        int bytesDLIdx =
                pausedDownloads.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);

        // Iterate over the result Cursor.
        while (pausedDownloads.moveToNext()) {
            // Extract the data we require from the Cursor.
            String title = pausedDownloads.getString(titleIdx);
            int fileSize = pausedDownloads.getInt(fileSizeIdx);
            int bytesDL = pausedDownloads.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = pausedDownloads.getInt(reasonIdx);
            String reasonString = "Unknown";
            switch (reason) {
                case DownloadManager.ERROR_HTTP_DATA_ERROR:
                    reasonString = "服务器异常";
                    break;
                case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                    reasonString = "存储空间不足";
                    break;
                case DownloadManager.ERROR_FILE_ERROR:
                    reasonString = "找不到该文件";
                    break;
                default:
                    break;
            }
            // Construct a status summary
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n");
            sb.append(reasonString).append("\n");
            sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);

            return sb.toString();
        }
        return "";
    }

}
