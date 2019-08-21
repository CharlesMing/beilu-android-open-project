package com.scj.beilu.app.ui.merchant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.merchant.MerchantPicManagerPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;
import com.scj.beilu.app.ui.merchant.adapter.MerchantInfoPicManagerListAdapter;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:26
 */
public class MerchantPhotoManagerFrag extends BaseMvpFragment<MerchantPicManagerPre.MerchantPicManagerView, MerchantPicManagerPre>
        implements MerchantPicManagerPre.MerchantPicManagerView, TabLayout.OnTabSelectedListener {

    private ViewPager view_pager_merchant_img;
    private TabLayout tab_merchant_nav;

    private int mMerchantId;

    private static final String MERCHANT_ID = "merchantId";

    private MerchantInfoPicManagerListAdapter mInfoPicManagerListAdapter;

    public static MerchantPhotoManagerFrag newInstance(int merchantId) {

        Bundle args = new Bundle();
        args.putInt(MERCHANT_ID, merchantId);
        MerchantPhotoManagerFrag fragment = new MerchantPhotoManagerFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMerchantId = arguments.getInt(MERCHANT_ID);
        }
    }

    @Override
    public MerchantPicManagerPre createPresenter() {
        return new MerchantPicManagerPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_pic_manager;
    }

    @Override
    public void initView() {
        super.initView();
        view_pager_merchant_img = findViewById(R.id.view_pager_merchant_img);
        tab_merchant_nav = findViewById(R.id.tab_merchant_nav);
        tab_merchant_nav.setupWithViewPager(view_pager_merchant_img);

        mInfoPicManagerListAdapter = new MerchantInfoPicManagerListAdapter(getChildFragmentManager(), mMerchantId);
        view_pager_merchant_img.setAdapter(mInfoPicManagerListAdapter);

        tab_merchant_nav.addOnTabSelectedListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getMerchantPicTypeList(mMerchantId);
    }


    private View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mFragmentActivity).inflate(R.layout.layout_tab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setText(title);
        return view;
    }

    private void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) return;
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        ImageView ivLine = view.findViewById(R.id.iv_tab_bottom_line);
        tvTab.setSelected(selected);
        ivLine.setSelected(selected);
        if (selected) {
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }

    @Override
    public void onTypeList(ArrayList<MerchantPicTypeBean> typeList) {
        mInfoPicManagerListAdapter.setPicTypeList(typeList);
        int size = tab_merchant_nav.getTabCount();
        for (int i = 0; i < size; i++) {
            MerchantPicTypeBean picTypeBean = typeList.get(i);
            tab_merchant_nav.getTabAt(i).setCustomView(getTabLayout(picTypeBean.getRegionName() + "(" + picTypeBean.getTypeCount() + ")"));
        }

        setTabSelector(tab_merchant_nav.getTabAt(0), true);

        view_pager_merchant_img.setOffscreenPageLimit(size);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabSelector(tab, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabSelector(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
