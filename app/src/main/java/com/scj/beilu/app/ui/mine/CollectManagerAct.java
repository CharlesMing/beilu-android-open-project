package com.scj.beilu.app.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.mine.CollectManagerPre;
import com.scj.beilu.app.ui.mine.adapter.CollectManagerListPagerAdapter;

public class CollectManagerAct extends BaseMvpActivity<CollectManagerPre.CollectManagerView, CollectManagerPre>
        implements CollectManagerPre.CollectManagerView {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageButton btn_back;
    private CollectManagerListPagerAdapter mCollectManagerListPagerAdapter;
    private final String[] mTitles = {"资讯", "商品"};

    public static void startCollectMangerAct(Activity activity) {
        Intent intent = new Intent(activity, CollectManagerAct.class);
        activity.startActivity(intent);
    }

    @NonNull
    @Override
    public CollectManagerPre createPresenter() {
        return new CollectManagerPre(this);
    }

    @Override
    public void initView() {
        super.initView();

        mViewPager = findViewById(R.id.view_pager_msg_manager);
        mTabLayout = findViewById(R.id.tab_msg_manager);
        btn_back = findViewById(R.id.btn_back);
        mTabLayout.setupWithViewPager(mViewPager);
        mCollectManagerListPagerAdapter = new CollectManagerListPagerAdapter(getSupportFragmentManager());
        mCollectManagerListPagerAdapter.setTitles(mTitles);
        mViewPager.setAdapter(mCollectManagerListPagerAdapter);

        mTabLayout.getTabAt(0).setCustomView(getPresenter().getTabLayout(mTitles[0]));
        mTabLayout.getTabAt(1).setCustomView(getPresenter().getTabLayout(mTitles[1]));

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
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.act_collect_manager;
    }
}
