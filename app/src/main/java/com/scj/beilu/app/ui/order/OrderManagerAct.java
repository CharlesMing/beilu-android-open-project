package com.scj.beilu.app.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.mine.order.OrderManagerPre;
import com.scj.beilu.app.ui.order.adapter.OrderManagerPagerAdapter;

/**
 * @author Mingxun
 * @time on 2019/1/15 21:11
 */
public class OrderManagerAct extends BaseMvpActivity<OrderManagerPre.OrderManagerView, OrderManagerPre>
        implements OrderManagerPre.OrderManagerView {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private OrderManagerPagerAdapter mOrderManagerPagerAdapter;

    private static final String[] mTabs = {"全部", "待付款", "退款中", "已完成"};

    public static void startOrderManagerAct(Activity activity) {
        Intent intent = new Intent(activity, OrderManagerAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_order_manager;
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager = findViewById(R.id.view_pager_order_manager);
        mTabLayout = findViewById(R.id.tab_order_manager);
        mTabLayout.setupWithViewPager(mViewPager);


        mOrderManagerPagerAdapter = new OrderManagerPagerAdapter(getSupportFragmentManager());
        mOrderManagerPagerAdapter.setTabs(mTabs);
        mViewPager.setAdapter(mOrderManagerPagerAdapter);

        mTabLayout.getTabAt(0).setCustomView(getPresenter().getTabLayout("全部"));
        mTabLayout.getTabAt(1).setCustomView(getPresenter().getTabLayout("待付款"));
        mTabLayout.getTabAt(2).setCustomView(getPresenter().getTabLayout("退款"));
        mTabLayout.getTabAt(3).setCustomView(getPresenter().getTabLayout("已完成"));


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
        mViewPager.setOffscreenPageLimit(4);
    }

    @NonNull
    @Override
    public OrderManagerPre createPresenter() {
        return new OrderManagerPre(this);
    }
}
