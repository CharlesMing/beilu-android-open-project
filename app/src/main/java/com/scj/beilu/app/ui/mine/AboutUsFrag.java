package com.scj.beilu.app.ui.mine;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.mine.AboutUsPre;
import com.scj.beilu.app.mvp.mine.bean.VersionBean;
import com.scj.beilu.app.ui.act.FeedbackAct;
import com.scj.beilu.app.util.DownLoadUtils;
import com.scj.beilu.app.util.DownloadApk;
import com.scj.beilu.app.util.SystemParams;
import com.scj.beilu.app.widget.ItemLayout;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * author:SunGuiLan
 * date:2019/3/4
 * descriptin:关于我们
 */
@RuntimePermissions
public class AboutUsFrag extends BaseMvpFragment<AboutUsPre.AboutUsView, AboutUsPre> implements AboutUsPre.AboutUsView, View.OnClickListener {

    private TextView tv_name, mVersionNum;
    private TextView mVersionDec;
    private TextView mVersionTitle;
    private TextView mVersionWeb;
    private TextView mVersionEmail;
    private ItemLayout mILFeedBack;
    private ItemLayout item_user_agreement;

    private String mUrl;
    private String mOfficialWebsite;

    @Override
    public AboutUsPre createPresenter() {
        return new AboutUsPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_about_us;
    }

    @Override
    public void initView() {
        super.initView();
        tv_name = findViewById(R.id.tv_name);
        mVersionNum = findViewById(R.id.tv_version_num);
        mVersionDec = findViewById(R.id.tv_version_dec);
        mVersionTitle = findViewById(R.id.tv_version_title);
        mVersionWeb = findViewById(R.id.tv_web);
        mVersionEmail = findViewById(R.id.tv_email);
        mILFeedBack = findViewById(R.id.il_feedback);
        item_user_agreement = findViewById(R.id.item_user_agreement);
        mILFeedBack.setOnClickListener(this);
        item_user_agreement.setOnClickListener(this);
        getPresenter().getAboutUsInfo();
        if (isAdded()) {
            String appName = getResources().getString(R.string.app_name);
            tv_name.setText(appName);
        }
        AboutUsFragPermissionsDispatcher.neesFileWithPermissionCheck(this);
    }


    @Override
    public void onAboutUsInfoResult(VersionBean versionBean) {

        PackageManager packageManager = mFragmentActivity.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mFragmentActivity.getPackageName(), 0);
            mVersionNum.setText("当前版本号" + packageInfo.versionName);

            if (versionBean.getVersionId() > packageInfo.versionCode) {
                updateHint(versionBean.getVersionDec(), versionBean.getVersionApkAddr(), versionBean.getVersionRestric() == 0);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mUrl = versionBean.getVersionProtocol();
            mVersionDec.setText(versionBean.getAppDec());
            mVersionTitle.setText(versionBean.getVersionTitle());
            mOfficialWebsite = versionBean.getVersionUrl();
            if (mOfficialWebsite.contains("http://")) {
                mVersionWeb.setText(versionBean.getVersionApkAddr());
            } else {
                mOfficialWebsite = "http://" + mOfficialWebsite;
                mVersionWeb.setText(mOfficialWebsite);
            }
            mVersionEmail.setText(versionBean.getVersionEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void updateHint(String txt, String uri, boolean isCancelable) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mFragmentActivity)
                .setPositiveButton(R.string.button_update, (dialog, which) -> {
                    startDownload(uri);
                })
                .setCancelable(isCancelable)

                .setTitle("版本更新")
                .setMessage(txt);

        if (isCancelable) {
            builder.setNegativeButton(R.string.button_deny, (dialog, which) -> {
                if (isCancelable) {
                    dialog.cancel();
                }
            });
        }
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.il_feedback:
                FeedbackAct.startFeedbackAct(mFragmentActivity);
                break;
            case R.id.item_user_agreement:
                start(UserAgreementFrag.newInstance(mUrl));
                break;
            case R.id.tv_web:
                start(UserAgreementFrag.newInstance(mOfficialWebsite));
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void neesFile() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AboutUsFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void rationale(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_call_file, request);
    }

    private void startDownload(String uri) {

        SystemParams.init(mFragmentActivity);

        //1.注册下载广播接收器
        DownloadApk.registerBroadcast(mFragmentActivity);
        //2.apk按照成功之后再次进场到app删除已存在的Apk
        DownloadApk.removeFile/**/(mFragmentActivity);

        if (DownLoadUtils.getInstance(mFragmentActivity).canDownload()) {
            //DownloadApk.downloadApk(getApplicationContext(), "http://www.huiqu.co/public/download/apk/huiqu.apk", "Hobbees更新", "Hobbees");
            DownloadApk.downloadApk(mFragmentActivity, uri, "北鹿更新", "北鹿");
        } else {
            DownLoadUtils.getInstance(mFragmentActivity).skipToDownloadManager();
        }
    }

    @Override
    public void onDestroy() {
        //4.反注册广播接收器
        DownloadApk.unregisterBroadcast(mFragmentActivity);
        super.onDestroy();
    }
}
