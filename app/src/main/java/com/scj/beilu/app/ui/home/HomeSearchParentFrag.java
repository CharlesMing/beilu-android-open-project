package com.scj.beilu.app.ui.home;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.home.HomeSearchTabPre;
import com.scj.beilu.app.mvp.home.bean.EventSearchTypeBean;
import com.scj.beilu.app.ui.home.adapter.HomeSearchPagerAdapter;
import com.scj.beilu.app.widget.ClearEditText;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:48
 */
public class HomeSearchParentFrag extends BaseMvpFragment<HomeSearchTabPre.HomeSearchTabView, HomeSearchTabPre>
        implements HomeSearchTabPre.HomeSearchTabView, TextWatcher, ViewPager.OnPageChangeListener {

    private ClearEditText mClearEditText;
    private TextView mTvCancel;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private HomeSearchPagerAdapter mHomeSearchPagerAdapter;

    private final String[] mTitles = {"动态", "资讯", "商品", "用户"};
    private final String[] mEtHint = {"搜索动态", "搜索资讯", "搜索商品", "搜索用户"};
    private int mPosition = 0;

    @Override
    public HomeSearchTabPre createPresenter() {
        return new HomeSearchTabPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_home_search;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarDarkFont(true,0.2f)
                .titleBar(R.id.top_view)
                .keyboardEnable(true)
                .init();
        mViewPager = findViewById(R.id.view_pager_search_content);
        mTabLayout = findViewById(R.id.tab_msg_manager);
        mClearEditText = findViewById(R.id.et_search_content);
        mTvCancel = findViewById(R.id.tv_cancel);

        mTabLayout.setupWithViewPager(mViewPager);

        mHomeSearchPagerAdapter = new HomeSearchPagerAdapter(getChildFragmentManager());
        mHomeSearchPagerAdapter.setTitles(mTitles);
        mViewPager.setAdapter(mHomeSearchPagerAdapter);

        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());

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

        mClearEditText.addTextChangedListener(this);
        mTvCancel.setOnClickListener(v -> mFragmentActivity.onBackPressed());
        mViewPager.addOnPageChangeListener(this);
        mClearEditText.setHint(mEtHint[0]);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        getPresenter().startSearch(new EventSearchTypeBean(mPosition, s.toString()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        mClearEditText.setHint(mEtHint[position]);
        getPresenter().startSearch(new EventSearchTypeBean(mPosition, mClearEditText.getText().toString()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
