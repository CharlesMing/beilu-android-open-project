package com.scj.beilu.app.ui.act;

import android.support.annotation.NonNull;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.fit.FitRecordPre;
import com.scj.beilu.app.mvp.fit.bean.FitUserSexBean;
import com.scj.beilu.app.ui.fit.FitRecordForAddFrag;
import com.scj.beilu.app.ui.fit.FitRecordSelectSexFrag;

/**
 * @author Mingxun
 * @time on 2019/3/7 11:25
 */
public class FitRecordAct extends BaseMvpActivity<FitRecordPre.FitRecordView, FitRecordPre>
        implements FitRecordPre.FitRecordView {
    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        getPresenter().getFitSexInfo();
//        loadRootFragment(R.id.fl_content, new FitRecordGirthFrag());
    }

    @NonNull
    @Override
    public FitRecordPre createPresenter() {
        return new FitRecordPre(this);
    }

    @Override
    public void OnFitSexInfo(FitUserSexBean sexBean) {
        if (sexBean.getUserSex() == -1) {//
            loadRootFragment(R.id.fl_content, new FitRecordSelectSexFrag());
        } else {
            loadRootFragment(R.id.fl_content, FitRecordForAddFrag.newInstance(sexBean.getUserSex()));
        }
    }
}
