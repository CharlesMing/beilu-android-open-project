package com.scj.beilu.app.location;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.scj.beilu.app.R;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.URI_ALLOW_UNSAFE;

/**
 * @author Mingxun
 * @time on 2019/4/17 16:56
 */
public class LoctionNavigationUtil {
    public final static String BAIDU_PACKAGENAME = "com.baidu.BaiduMap";
    public final static String GAODE_PACKAGENAME = "com.autonavi.minimap";

    /**
     * 高德导航
     *
     * @param context
     * @param location
     */
    public static void gaodeGuide(Context context, double[] location) {
        if (isAvilible(context, GAODE_PACKAGENAME)) {
            try {
                Intent intent = Intent.parseUri("androidamap://navi?sourceApplication=" +
                        context.getResources().getString(R.string.app_name) +
                        "&poiname=我的目的地" +
                        "&lat=" + location[0] +
                        "&lon=" + location[1] +
                        "&dev=0", URI_ALLOW_UNSAFE);
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 百度导航
     *
     * @param context
     * @param location location[0]纬度lat，location[1]经度lon
     */
    public static void baiduGuide(Context context, double[] location) {
        double[] baiduLoc = GpsUtils.gcj02_To_Bd09(location[0], location[1]);

        if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
            try {
                //intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:" + baiduLoc[0] + "," + baiduLoc[1] + "|name:我的目的地" +        //终点
                        "&mode=driving" +          //导航路线方式
                        "&region=" +           //
                        "&src=" +
                        context.getResources().getString(R.string.app_name) +
                        "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


}
