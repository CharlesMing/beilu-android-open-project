package com.scj.beilu.app.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.mine.UserInfoDetailsPre;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.ui.act.UserInfoEditAct;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.AppToolbar;

/**
 * author: SunGuiLan
 * date:2019/2/22
 * descriptin:个人信息编辑
 */
public class UserInfoDetailsAct extends BaseMvpActivity<UserInfoDetailsPre.UserInfoDetailsView, UserInfoDetailsPre> implements UserInfoDetailsPre.UserInfoDetailsView {
    private AppToolbar toolbar;
    private UserInfoEntity userInfoEntity;
    private String data;
    private EditText et_data;
    private String inputData;
    public static final String EXTRA_USER_INFO = "user_info";

    //个人信息来源上个界面数据（缓存中的数据）通过intent传递EXTRA_USER_INFO修改也是修改缓冲中的数据
    public static void startUserInfoDetailsAct(Activity activity, String title, UserInfoEntity userInfo) {
        Intent intent = new Intent(activity, UserInfoDetailsAct.class);
        intent.putExtra("title", title);
        intent.putExtra(EXTRA_USER_INFO, userInfo);
        activity.startActivityForResult(intent, UserInfoEditAct.UPDATE_REQUEST_CODE);
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar = findViewById(R.id.toolbar);
        et_data = findViewById(R.id.et_data);
        Intent intent = getIntent();
        if (intent == null) return;
        data = intent.getStringExtra("title");
        try {
            //设置标题
            toolbar.setToolbarTitle(data);
            userInfoEntity = intent.getParcelableExtra(EXTRA_USER_INFO);
            if (data.equals("昵称")) {
                if (userInfoEntity.getUserNickName() != null) {
                    et_data.setText(userInfoEntity.getUserNickName());
                }
            }
            if (data.equals("个性签名")) {
                String content = "无";
                if (userInfoEntity.getUserBrief() != null && userInfoEntity.getUserBrief().length() > 0) {
                    content = userInfoEntity.getUserBrief();
                }
                et_data.setText(content);
            }
            //光标移动到文字后
            et_data.setSelection(et_data.getText().length());
            toolbar.setOnMenuItemClickListener(item -> {
                inputData = et_data.getText().toString();
                try {
                    CheckUtils.checkStringIsNull(inputData, "输入信息");
                    if (data.equals("昵称")) {
                        userInfoEntity.setUserNickName(inputData);
                    }
                    if (data.equals("个性签名")) {
                        userInfoEntity.setUserBrief(inputData);
                    }
                    getPresenter().modifyUserInfo(userInfoEntity);
                } catch (Exception e) {
                    ToastUtils.showToast(UserInfoDetailsAct.this, e.getMessage());
                }

                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.act_userinfo_details;
    }

    @NonNull
    @Override
    public UserInfoDetailsPre createPresenter() {
        return new UserInfoDetailsPre(this);
    }


    @Override
    public void onUpdateInfoResult(String resultMsg) {
        ToastUtils.showToast(UserInfoDetailsAct.this, resultMsg);
    }

    @Override
    public void onUpdateCacheResult(UserInfoEntity userInfo) {
        userInfoEntity = userInfo;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_INFO, userInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
