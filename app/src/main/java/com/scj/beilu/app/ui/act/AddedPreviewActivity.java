package com.scj.beilu.app.ui.act;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.album.listener.OnFragmentInteractionListener;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.ui.preview.adapter.AddedPreviewPagerAdapter;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/2/20 17:54
 */
public class AddedPreviewActivity extends BaseSupportAct implements OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private AddedPreviewPagerAdapter mAddedPreviewPagerAdapter;

    public static final String POSITION = "position";
    public static final String IMAGE_LIST = "IMAGE_LIST";
    public static final String IMAGE_PATH_LIST = "image_path_list";
    private ArrayList<Uri> mUriArrayList;
    private ArrayList<String> mPathArrayList;

    @Override
    public int getLayout() {
        return R.layout.act_added_preview;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this)
                .statusBarColor(android.R.color.black)
                .init();
        mViewPager = findViewById(R.id.view_pager);
        Button btnDel = findViewById(R.id.btn_del);
        Intent intent = getIntent();
        if (intent == null) return;
        mUriArrayList = intent.getParcelableArrayListExtra(IMAGE_LIST);
        mPathArrayList = intent.getStringArrayListExtra(IMAGE_PATH_LIST);
        int position = intent.getIntExtra(POSITION, 0);

        mAddedPreviewPagerAdapter = new AddedPreviewPagerAdapter(getSupportFragmentManager());
        mAddedPreviewPagerAdapter.setImagePathList(mUriArrayList);
        mViewPager.setAdapter(mAddedPreviewPagerAdapter);

        mViewPager.setCurrentItem(position, false);

        btnDel.setOnClickListener(view -> {
            int currentItem = mViewPager.getCurrentItem();
            mAddedPreviewPagerAdapter.remove(currentItem);
            mPathArrayList.remove(currentItem);
            mAddedPreviewPagerAdapter.notifyDataSetChanged();
            if (mAddedPreviewPagerAdapter.getCount() == 0) {
                setBackResult();
            }
        });

    }

    @Override
    public void onClick() {
        setBackResult();
    }

    private void setBackResult() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra(IMAGE_LIST, mUriArrayList);
        data.putStringArrayListExtra(IMAGE_PATH_LIST, mPathArrayList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        setBackResult();
    }
}
