package com.scj.beilu.app.ui.merchant;

import android.content.Intent;

import com.mx.pro.lib.album.listener.OnFragmentInteractionListener;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:23
 */
public class MerchantPicPreviewAct extends BaseSupportAct implements OnFragmentInteractionListener {

    public static final String PARENT_POSITION = "parent_position";
    public static final String CHILD_POSITION = "child_position";
    public static final String EXTRA_DATA = "data";
    public static final String MERCHANT_ID = "merchant_id";

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) return;
        try {
            int mParentPosition = intent.getIntExtra(PARENT_POSITION, 0);
            int mChildPosition = intent.getIntExtra(CHILD_POSITION, 0);
            int merchantId = intent.getIntExtra(MERCHANT_ID, 0);
            ArrayList<MerchantPicInfoBean> picInfoList = intent.getParcelableArrayListExtra(MerchantPicPreviewAct.EXTRA_DATA);
            loadRootFragment(R.id.fl_content, MerchantPhotoPreviewManagerFrag.newInstance(mParentPosition, mChildPosition, picInfoList,merchantId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick() {
        // TODO: 2019/4/16
    }
}
