package com.scj.beilu.app.ui.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mx.pro.lib.mvp.network.config.AppConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.mine.MineAccountPre;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.ItemLayout;

/**
 * @author Mingxun
 * @time on 2019/4/12 19:51
 */
public class MineAccountSettingFrag extends BaseMvpFragment<MineAccountPre.MineAccountView, MineAccountPre>
        implements MineAccountPre.MineAccountView, View.OnClickListener {

    private ItemLayout mItemLayoutCache, mItemLayoutAccount;


    @Override
    public MineAccountPre createPresenter() {
        return new MineAccountPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_account_setting;
    }

    @Override
    public void initView() {
        super.initView();
        mItemLayoutCache = findViewById(R.id.item_val_cache);
        mItemLayoutAccount = findViewById(R.id.item_account);
        mItemLayoutCache.setOnClickListener(this);
        mItemLayoutAccount.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCacheSize();
    }

    @Override
    public void onCacheSize(String cacheSize) {
        mItemLayoutCache.setRightText(cacheSize);
    }

    private SharedPreferences mSharedPreferences;
    private String mToken;

    private boolean getToken() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mFragmentActivity.getApplicationContext().getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE);
        }
        mToken = mSharedPreferences.getString(AppConfig.USER_TOKEN, null);
        return mToken == null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_val_cache:
                getPresenter().clearCache();
                break;
            case R.id.item_account:
                if (getToken()) {
                    ToastUtils.showToast(mFragmentActivity, "你当前没有登录任何账号");
                } else {
                    start(new MineAccountManagerFrag());
                }
                break;
        }
    }
}
