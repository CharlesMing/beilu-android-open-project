package com.scj.beilu.app.ui.act;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.album.GlideEngine;
import com.mx.pro.lib.album.MimeType;
import com.mx.pro.lib.album.filter.Filter;
import com.mx.pro.lib.album.internal.entity.CaptureStrategy;
import com.orhanobut.logger.Logger;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.find.GifSizeFilter;
import com.scj.beilu.app.mvp.mine.UserInfEditPre;
import com.scj.beilu.app.mvp.mine.bean.DistrictInfoBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.ui.user.UserInfoDetailsAct;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.BaseDialog;
import com.scj.beilu.app.widget.ItemLayout;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.scj.beilu.app.api.Constants.DIRECTORY;


/**
 * @author Mingxun
 * @time on 2019/1/12 16:08
 */
@RuntimePermissions
public class UserInfoEditAct extends BaseMvpActivity<UserInfEditPre.UserInfoEditView, UserInfEditPre>
        implements UserInfEditPre.UserInfoEditView, View.OnClickListener {
    private ItemLayout il_name;
    private ItemLayout il_sex;
    private ItemLayout il_birth;
    private ItemLayout il_place;
    private ItemLayout il_signature;
    private UserInfoEntity mUserInfo;
    private RelativeLayout rl_photo;
    private ImageView iv_info_avatar;
    private String path;
    private BaseDialog mDialog;
    public static final int UPDATE_REQUEST_CODE = 0x001;
    private int mPosotion1 = 0;
    private int mPosotion2 = 0;
    private List<DistrictInfoBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items;

    public static void startUserInfoEditAct(Activity activity) {
        Intent intent = new Intent(activity, UserInfoEditAct.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarDarkFont(true,0.2f)
                .init();
        il_name = findViewById(R.id.il_name);
        il_sex = findViewById(R.id.il_sex);
        il_birth = findViewById(R.id.il_birth);
        il_place = findViewById(R.id.il_place);
        il_signature = findViewById(R.id.il_signature);
        rl_photo = findViewById(R.id.rl_photo);
        iv_info_avatar = findViewById(R.id.iv_info_avatar);
        il_name.setOnClickListener(this);
        il_sex.setOnClickListener(this);
        il_birth.setOnClickListener(this);
        il_place.setOnClickListener(this);
        il_signature.setOnClickListener(this);
        rl_photo.setOnClickListener(this);
        getPresenter().getUserInfo();
    }

    @Override
    public int getLayout() {
        return R.layout.act_user_info_edit;
    }

    @NonNull
    @Override
    public UserInfEditPre createPresenter() {
        return new UserInfEditPre(this);
    }

    @Override
    public void onUserInfo(UserInfoEntity userInfoEntity) {
        mUserInfo = userInfoEntity;
        if (userInfoEntity.getUserNickName() != null) {
            il_name.setRightText(userInfoEntity.getUserNickName());
        }
        if (userInfoEntity.getUserSex() != null) {
            if (userInfoEntity.getUserSex().equals("man")) {
                il_sex.setRightText("男");
            } else {
                il_sex.setRightText("女");
            }
        }

        if (userInfoEntity.getUserBirth() != null) {
            il_birth.setRightText(userInfoEntity.getUserBirth().split(" ")[0]);
        }

        if (userInfoEntity.getUserProvince() != null && userInfoEntity.getUserProvince() != null)
            il_place.setRightText(userInfoEntity.getUserProvince() + userInfoEntity.getUserCity());
        il_signature.setRightText(userInfoEntity.getUserBrief());
        if (userInfoEntity.getUserCompressionHead() != null) {
            //把图片加载到控件上
            GlideApp.with(this)
                    .load(userInfoEntity.getUserCompressionHead())
                    .centerCrop()
                    .transform(new CircleCrop())
                    .into(iv_info_avatar);
        }

    }

    //getpresent.modifyUserInfo()将会调用
    @Override
    public void onUpdateInfoResult(String resultMsg) {
        ToastUtils.showToast(UserInfoEditAct.this, resultMsg);
    }

    /**
     * @param userInfo
     */
    @Override
    public void onUpdateCacheResult(UserInfoEntity userInfo) {
        if (mDialog != null) {
            mDialog.close();
        }
        onUserInfo(userInfo);
    }

    @Override
    public void onDistrictListResult(DictrictOptionBean optionBean) {
        options1Items.clear();
        options1Items.addAll(optionBean.getOptions1Items());
        this.options2Items = optionBean.getOptions2Items();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.il_name:
                UserInfoDetailsAct.startUserInfoDetailsAct(UserInfoEditAct.this, il_name.getLeftText(), mUserInfo);
                break;
            case R.id.il_sex:
                selectSexDialog(Gravity.BOTTOM, R.style.Alpah_aniamtion);
                break;
            case R.id.il_birth:
                showDateView();
                break;
            case R.id.il_place:
                showPickerView();
                break;
            case R.id.il_signature:
                UserInfoDetailsAct.startUserInfoDetailsAct(UserInfoEditAct.this, il_signature.getLeftText(), mUserInfo);
                break;
            case R.id.rl_photo:
                UserInfoEditActPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                break;
            case R.id.tv_man:
                mUserInfo.setUserSex("man");
                getPresenter().modifyUserInfo(mUserInfo);
                break;
            case R.id.tv_girl:
                mUserInfo.setUserSex("female");
                getPresenter().modifyUserInfo(mUserInfo);
                break;
            case R.id.tv_cancel:
                mDialog.close();
                break;
        }
    }


    /**
     * 选择性别
     *
     * @param gravity
     * @param animationStyle
     */
    public void selectSexDialog(int gravity, int animationStyle) {

        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mDialog = builder.setViewId(R.layout.dialog_select_sex)
                .setGravity(gravity)
                .setPaddingdp(15, 0, 15, 15)
                .setAnimation(animationStyle)
                .isOnTouchCanceled(true)
                .builder();
        mDialog.show();
        TextView tvMan = mDialog.getView(R.id.tv_man);
        TextView tvGirl = mDialog.getView(R.id.tv_girl);
        TextView tvCancel = mDialog.getView(R.id.tv_cancel);
        tvMan.setOnClickListener(this);
        tvGirl.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }


    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mPosotion1 = options1;
                mPosotion2 = options2;
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";
                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                il_place.setRightText(opt1tx + opt2tx);
                mUserInfo.setUserProvince(opt1tx);
                mUserInfo.setUserCity(opt2tx);
                getPresenter().modifyUserInfo(mUserInfo);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSelectOptions(mPosotion1, mPosotion2)//默认选中项
                .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
                .build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }


    /**
     * 选择生日date
     */
    private void showDateView() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        //获取当前的年月日
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        Calendar selectDate = Calendar.getInstance();

        if (mUserInfo.getUserBirth() != null) {
            String date = mUserInfo.getUserBirth().split(" ")[0];
            selectDate.set(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]) - 1,
                    Integer.parseInt(date.split("-")[2]));
        }

        TimePickerView pvTime = new TimePickerBuilder(this,
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        mUserInfo.setUserBirth(getTime(date));
                        Logger.e(mUserInfo.getUserBirth());
                        getPresenter().modifyUserInfo(mUserInfo);
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("日期")//标题文字
                .setRangDate(startDate, endDate)
                .setDate(selectDate) //默认选中日期
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.show();
    }

    /**
     * 选择相册中的图片需要进行动态申请权限
     */
    public void selectSource() {
        AlbumManager.from(this)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, UserInfoEditAct.this.getPackageName() + ".fileprovider", DIRECTORY))
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())  // for glide-V4
                .maxOriginalSize(10
                )
                .autoHideToolbarOnSingleTap(true)
                .forResult(110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UPDATE_REQUEST_CODE && data != null) {
            UserInfoEntity userInfo = data.getParcelableExtra(UserInfoDetailsAct.EXTRA_USER_INFO);
            onUserInfo(userInfo);
        }

        if (resultCode == RESULT_OK && requestCode == 110) {
            UCrop.Options options = new UCrop.Options();
            options.setHideBottomControls(true);
            options. setStatusBarColor(ContextCompat.getColor(this, R.color.colorFed230));
            options.setToolbarColor(ContextCompat.getColor(this, R.color.colorFed230));
            UCrop.of(AlbumManager.obtainResult(data).get(0), Uri.fromFile(new File(getDiskCacheDir(UserInfoEditAct.this),
                    System.currentTimeMillis() + "")))
                    .withOptions(options)
                    .withAspectRatio(1, 1)
                    .start(this);
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            path = resultUri.getPath();
            getPresenter().setUserPhoto(path);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }


    /**
     * @param context
     * @return 缓存
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsPermission() {
        selectSource();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserInfoEditActPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_camera_rationale, request);
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
