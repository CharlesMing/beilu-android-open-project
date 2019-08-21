package com.scj.beilu.app.ui.find;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.mx.pro.lib.mvp.network.config.AppConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.find.FindPre;
import com.scj.beilu.app.mvp.find.bean.EventAttentionNotify;
import com.scj.beilu.app.ui.find.adapter.FindManagerPagerAdapter;
import com.scj.beilu.app.widget.BaseDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FindInfoFrag extends BaseMvpFragment<FindPre.FindView, FindPre>
        implements FindPre.FindView, View.OnClickListener, AttentionListener {
    private FrameLayout fl_search;
    private ImageView iv_issue;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FindManagerPagerAdapter mFindManagerPagerAdapter;
    private SharedPreferences mSharedPreferences;

    private final String[] mTitles = {"热门", "关注"};
    private FindInfoListFrag mFindInfoListFragHot;
    private FindInfoAttentionListFrag mFindInfoListFragAttention;


    @Override
    public FindPre createPresenter() {
        return new FindPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_find;
    }

    @Override
    public void initView() {
        super.initView();
        fl_search = findViewById(R.id.fl_search);
        iv_issue = findViewById(R.id.iv_publish);
        fl_search.setOnClickListener(this);
        iv_issue.setOnClickListener(this);

        mViewPager = findViewById(R.id.view_pager_find_manager);
        mTabLayout = findViewById(R.id.tab_msg_manager);
        mTabLayout.setupWithViewPager(mViewPager);
        mFindInfoListFragHot = FindInfoListFrag.newInstance(0);
        mFindInfoListFragHot.setAttentionListener(this);
        mFindInfoListFragAttention = FindInfoAttentionListFrag.newInstance(1);
        mFindInfoListFragAttention.setAttentionListener(this);
        List<SupportFragment> findInfoList = new ArrayList<>(2);
        findInfoList.add(mFindInfoListFragHot);
        findInfoList.add(mFindInfoListFragAttention);

        mFindManagerPagerAdapter = new FindManagerPagerAdapter(mFragmentActivity.getSupportFragmentManager());
        mFindManagerPagerAdapter.setFindInfoListFrags(findInfoList);
        mViewPager.setAdapter(mFindManagerPagerAdapter);

        int count = mTabLayout.getTabCount();
        for (int i = 0; i < count; i++) {
            mTabLayout.getTabAt(i).setCustomView(getPresenter().getTabLayout(mTitles[i]));
        }

        getPresenter().setTabSelector(mTabLayout.getTabAt(0), true);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getPresenter().setTabSelector(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                getPresenter().setTabSelector(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setOffscreenPageLimit(3);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fl_search:
                Intent intent = new Intent(mFragmentActivity, SearchFindAct.class);
                startActivity(intent);
                break;
            case R.id.iv_publish:
                getPresenter().checkUserIsLogin(view.getId());
                break;

        }
    }

    public void startFindPublish() {
        Intent intent = new Intent(mFragmentActivity, FindPublishWayAct.class);
        startActivityForResult(intent, 1000);
    }

    //第一次发布动态提示框
    public void showDialog(int gravity, int animationStyle) {

        BaseDialog.Builder builder = new BaseDialog.Builder(mFragmentActivity);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_find_publish)
                .setGravity(gravity)
                .setPaddingdp(15, 0, 15, 0)
                .setAnimation(animationStyle)
                .isOnTouchCanceled(true)
                .builder();
        dialog.show();
        TextView mTextView = dialog.getView(R.id.tv_sure);
        mTextView.setOnClickListener(v -> {
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putBoolean(Constants.VAL_SHOW_CREATE_HINT, true);
            edit.apply();
            dialog.close();
            startFindPublish();
        });
    }

    @Override
    protected void userStartAction(int viewId) {
        if (mSharedPreferences == null) {
            mSharedPreferences = mFragmentActivity.getApplication().getSharedPreferences(AppConfig.HINT, Context.MODE_PRIVATE);
        }
        boolean showHint = mSharedPreferences.getBoolean(Constants.VAL_SHOW_CREATE_HINT, false);
        if (showHint) {
            startFindPublish();
        } else {
            showDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
        }
    }

    @Override
    public void onAttention(int index, int userId) {
        //用于监听状态
        EventBus.getDefault().post(new EventAttentionNotify( userId,index == 0 ? 1 : 0));
    }
}
