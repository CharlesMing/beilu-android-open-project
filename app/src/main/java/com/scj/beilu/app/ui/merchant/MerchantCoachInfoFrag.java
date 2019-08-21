package com.scj.beilu.app.ui.merchant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.merchant.MerchantInfoCoachPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachResultBean;
import com.scj.beilu.app.ui.act.adapter.ImageSelectedListAdapter;
import com.scj.beilu.app.ui.merchant.adapter.MerchantCoachAdvantageCourseListAdapter;
import com.scj.beilu.app.ui.merchant.adapter.MerchantCoachInfoPicListPagerAdapter;
import com.scj.beilu.app.util.ToastUtils;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:27
 */
@RuntimePermissions
public class MerchantCoachInfoFrag extends BaseMvpFragment<MerchantInfoCoachPre.MerchantInfoCoachView, MerchantInfoCoachPre>
        implements MerchantInfoCoachPre.MerchantInfoCoachView, ViewPager.OnPageChangeListener {

    private static final String MERCHANT_ID = "merchantId";
    private static final String COACH_ID = "coachId";

    private ViewPager view_pager_merchant_img;
    private RecyclerView rv_image_selected_list;
    private TextView tv_coach_name;
    private TextView tv_coach_authentication;
    private TextView tv_coach_info_description;
    private RecyclerView rv_advantage_course_list;
    private ImageView iv_authentication;
    private ImageView iv_coach_avatar;
    private TextView tv_coach_img_count;
    private LinearLayout ll_merchant_img;
    private TextView tv_consult;

    private int mMerchantId;
    private int mCoachId;

    private String phoneNum;

    private MerchantCoachInfoPicListPagerAdapter mMerchantCoachInfoPicListPagerAdapter;
    private MerchantCoachAdvantageCourseListAdapter mMerchantCoachAdvantageCourseListAdapter;
    private ImageSelectedListAdapter mImageSelectedListAdapter;//用于显示底部圆点

    public static MerchantCoachInfoFrag newInstance(int merchantId, int coachId) {

        Bundle args = new Bundle();
        args.putInt(MERCHANT_ID, merchantId);
        args.putInt(COACH_ID, coachId);
        MerchantCoachInfoFrag fragment = new MerchantCoachInfoFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMerchantId = arguments.getInt(MERCHANT_ID);
            mCoachId = arguments.getInt(COACH_ID);
        }
    }

    @Override
    public MerchantInfoCoachPre createPresenter() {
        return new MerchantInfoCoachPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_coach_info;
    }

    @Override
    public void initView() {
        super.initView();

        ImmersionBar.with(this).statusBarView(R.id.top_view)
                .addTag("PicAndColor")
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();

        ll_merchant_img = findViewById(R.id.ll_merchant_img);
        view_pager_merchant_img = findViewById(R.id.view_pager_merchant_img);
        rv_image_selected_list = findViewById(R.id.rv_image_selected_list);
        tv_coach_name = findViewById(R.id.tv_coach_name);
        tv_coach_authentication = findViewById(R.id.tv_coach_authentication);
        tv_coach_info_description = findViewById(R.id.tv_coach_info_description);
        rv_advantage_course_list = findViewById(R.id.rv_advantage_course_list);
        iv_authentication = findViewById(R.id.iv_authentication);
        tv_coach_img_count = findViewById(R.id.tv_coach_img_count);
        iv_coach_avatar = findViewById(R.id.iv_coach_avatar);
        tv_consult = findViewById(R.id.tv_consult);

        mMerchantCoachInfoPicListPagerAdapter = new MerchantCoachInfoPicListPagerAdapter(this);
        view_pager_merchant_img.setAdapter(mMerchantCoachInfoPicListPagerAdapter);

        mMerchantCoachAdvantageCourseListAdapter = new MerchantCoachAdvantageCourseListAdapter();
        rv_advantage_course_list.setAdapter(mMerchantCoachAdvantageCourseListAdapter);

        mImageSelectedListAdapter = new ImageSelectedListAdapter();
        rv_image_selected_list.setAdapter(mImageSelectedListAdapter);

        view_pager_merchant_img.addOnPageChangeListener(this);

        final int size = getResources().getDimensionPixelSize(R.dimen.rect_5);

        rv_image_selected_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(size, 0, size, 0);
            }
        });
        ll_merchant_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MerchantCoachAlbumListFrag.newInstance(mMerchantId, mCoachId));
            }
        });

        rv_advantage_course_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(size, 0, size, 0);
            }
        });

        tv_consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantCoachInfoFragPermissionsDispatcher.needsCallPhoneWithPermissionCheck(MerchantCoachInfoFrag.this);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCoachInfo(mMerchantId, mCoachId);
    }

    @Override
    public void onCoachInfo(MerchantInfoCoachResultBean coachInfo) {
        try {
            MerchantInfoCoachBean coachEntity = coachInfo.getCoachEntity();
            tv_coach_img_count.setText(String.valueOf(coachInfo.getCountAlbum()));
            getPresenter().initSelectorList(coachEntity.getCoachPicList());
            mMerchantCoachInfoPicListPagerAdapter.setInfoCoachPicInfoBeans(coachEntity.getCoachPicList());
            GlideApp.with(this)
                    .load(coachEntity.getCoachHead())
                    .thumbnail(GlideApp.with(this).load(coachEntity.getCoachHeadZip()).optionalCircleCrop())
                    .optionalCircleCrop()
                    .into(iv_coach_avatar);
            tv_coach_name.setText(coachEntity.getCoachName());
            String coachCertifiDec = coachEntity.getCoachCertifiDec();
            coachCertifiDec = (coachCertifiDec == null) ? "无" : coachCertifiDec;
            tv_coach_authentication.setText(coachCertifiDec);
            tv_coach_info_description.setText(coachEntity.getCoachSummary());

            GlideApp.with(this)
                    .load(coachEntity.getCoachCertifi())
                    .thumbnail(GlideApp.with(this).load(coachEntity.getCoachCertifiZip()).optionalCenterCrop())
                    .optionalCenterCrop()
                    .into(iv_authentication);

            mMerchantCoachAdvantageCourseListAdapter.setCoachExperts(coachEntity.getCoachExpert());
            // TODO: 2019/4/17
            phoneNum = coachEntity.getCoachCertifi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImgSelectedListResult(List<Boolean> imageSelectorList) {
        mImageSelectedListAdapter.setImageSelectedList(imageSelectorList);
        getPresenter().setSelectedByPosition(0);
    }

    @Override
    public void onNotify(int position) {
        mImageSelectedListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getPresenter().setSelectedByPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void needsCallPhone() {
        if (phoneNum != null) {
            new AlertDialog.Builder(mFragmentActivity)
                    .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + phoneNum);
                            intent.setData(data);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage(phoneNum)
                    .show();
        } else {
            ToastUtils.showToast(mFragmentActivity, "暂时无法拨打电话～");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MerchantCoachInfoFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void rationaleCallPhone(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_call_phone, request);
    }
}
