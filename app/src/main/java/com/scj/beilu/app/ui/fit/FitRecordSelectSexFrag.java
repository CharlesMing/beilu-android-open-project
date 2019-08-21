package com.scj.beilu.app.ui.fit;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.fit.FitRecordSexPre;
import com.scj.beilu.app.util.ToastUtils;

/**
 * @author Mingxun
 * @time on 2019/3/7 11:25
 * 选择性别
 */
public class FitRecordSelectSexFrag extends BaseMvpFragment<FitRecordSexPre.FitRecordSexView, FitRecordSexPre>
        implements FitRecordSexPre.FitRecordSexView, View.OnClickListener {

    private ImageView mIvMan;
    private ImageView mIvWoman;
    private int mSexVal = -1;//0 女 1 男


    @Override
    public FitRecordSexPre createPresenter() {
        return new FitRecordSexPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_fit_record_select_sex;
    }

    @Override
    public void initView() {
        super.initView();

        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(true,0.2f)
                .init();

        mIvMan = findViewById(R.id.iv_fit_sex_man);
        mIvWoman = findViewById(R.id.iv_fit_sex_woman);

        mIvMan.setOnClickListener(this);
        mIvWoman.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fit_sex_man:
                mSexVal = 1;
                selectSex();
                break;
            case R.id.iv_fit_sex_woman:
                mSexVal = 0;
                selectSex();
                break;
        }
    }

    private void selectSex() {
        new AlertDialog.Builder(mFragmentActivity)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        getPresenter().setSex(mSexVal);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        mSexVal = -1;
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("性别选择只能选择一次哦！")
                .show();
    }

    @Override
    public void setResult(ResultMsgBean result) {
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        startWithPop(FitRecordForAddFrag.newInstance(mSexVal));
    }
}
