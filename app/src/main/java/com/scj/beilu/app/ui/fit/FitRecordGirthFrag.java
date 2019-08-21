package com.scj.beilu.app.ui.fit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.fit.FitRecordGirthPre;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthInfoBean;
import com.scj.beilu.app.ui.dialog.RuleViewDialog;
import com.scj.beilu.app.ui.fit.adapter.FitRecordGirthListAdapter;
import com.scj.beilu.app.util.ToastUtils;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/11 20:11
 * 添加围度
 */
public class FitRecordGirthFrag extends BaseMvpFragment<FitRecordGirthPre.FitRecordGirthView, FitRecordGirthPre>
        implements FitRecordGirthPre.FitRecordGirthView, OnItemClickListener<FitRecordGirthInfoBean>, RuleViewDialog.OnGirth {

    private FitRecordGirthListAdapter mFitRecordGirthListAdapter;
    private FitRecordGirthInfoBean mGirthInfoBean;
    private int mPosition = -1;
    public static final int GIRTH_REQUEST = 0x001;
    public static final int GIRTH_ADD_RESULT_OK = 0x100;

    @Override
    public FitRecordGirthPre createPresenter() {
        return new FitRecordGirthPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_fit_record_girth;
    }

    @Override
    public void initView() {
        super.initView();

        mFitRecordGirthListAdapter = new FitRecordGirthListAdapter(mFragmentActivity);
        mFitRecordGirthListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mFitRecordGirthListAdapter);

        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getFitRecordInfo();
    }

    @Override
    public void onGirthListResult(List<FitRecordGirthInfoBean> girthInfoBeans) {
        mFitRecordGirthListAdapter.setFitRecordGirthInfoBeanList(girthInfoBeans);
    }

    @Override
    public void onAddResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        setFragmentResult(GIRTH_ADD_RESULT_OK, null);
        if (mPosition != -1) {
            mFitRecordGirthListAdapter.notifyItemChanged(mPosition);
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, FitRecordGirthInfoBean entity, View view) {
        mPosition = position;
        mGirthInfoBean = entity;
        RuleViewDialog dialog = RuleViewDialog.newInstance(mGirthInfoBean.getUnit());
        dialog.setOnGirth(this);
        dialog.show(getChildFragmentManager(), "tag");
    }

    @Override
    public void onGirthVal(float val) {
        if (mGirthInfoBean == null) return;
        getPresenter().addFitRecord(mPosition, mGirthInfoBean.getRecordKey(), val);
    }
}
