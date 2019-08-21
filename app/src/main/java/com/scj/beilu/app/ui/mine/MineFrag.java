package com.scj.beilu.app.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.AppConfig;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.MinePre;
import com.scj.beilu.app.mvp.mine.bean.TotalCountBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.ui.act.AboutUsAct;
import com.scj.beilu.app.ui.act.FitRecordAct;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.ui.act.MsgManagerAct;
import com.scj.beilu.app.ui.act.SetSystemAct;
import com.scj.beilu.app.ui.act.UserInfoEditAct;
import com.scj.beilu.app.ui.mine.adapter.MeNavListAdapter;
import com.scj.beilu.app.ui.mine.address.AddressManagerAct;
import com.scj.beilu.app.ui.order.OrderManagerAct;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;

/**
 * @author Mingxun
 * @time on 2019/1/11 15:35
 * 我的
 */
public class MineFrag extends BaseMvpFragment<MinePre.MineView, MinePre>
        implements MinePre.MineView, OnItemClickListener<Integer>, View.OnClickListener {
    private ImageView mIvAvatar;
    private TextView mTvNickName;
    private TextView mTvId_Like;
    private LinearLayout mDynamic, mCollect, mAttention, mFans;
    private TextView mTvDynamicNum, mTvCollectNum, mTvAttentionNum, mTvFansNum;
    private ConstraintLayout mClNotLogin;
    private RelativeLayout mRlIsLogin;
    private ImageButton mBtnUserHome;

    private RecyclerView mRvMeNavList;
    private MeNavListAdapter mMeNavListAdapter;
    private String mToken;
    private SharedPreferences mSharedPreferences;
    private long mUserID;


    @Override
    public MinePre createPresenter() {
        return new MinePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_mine;
    }

    @Override
    public void initView() {
        super.initView();
        mAppToolbar = findViewById(R.id.toolbar1);
        mIvAvatar = findViewById(R.id.iv_avatar);
        mTvNickName = findViewById(R.id.tv_user_nick);
        mTvId_Like = findViewById(R.id.tv_user_id_like);
        mRvMeNavList = findViewById(R.id.rv_me_nav);
        mDynamic = findViewById(R.id.me_dynamic);
        mCollect = findViewById(R.id.me_collect);
        mTvId_Like = findViewById(R.id.tv_user_id_like);
        mAttention = findViewById(R.id.me_attention);
        mFans = findViewById(R.id.me_fans);
        mTvDynamicNum = findViewById(R.id.me_tv_dynamic_num);
        mTvCollectNum = findViewById(R.id.me_tv_collect_num);
        mTvAttentionNum = findViewById(R.id.me_tv_attention_num);
        mTvFansNum = findViewById(R.id.me_tv_fans_num);
        mBtnUserHome = findViewById(R.id.btn_user_home);
        mRlIsLogin = findViewById(R.id.rl_is_login);
        mClNotLogin = findViewById(R.id.cl_user_not_login);

        mRvMeNavList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 40, 0, 0);
            }
        });

        mAppToolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(mFragmentActivity, SetSystemAct.class);
            startActivityForResult(intent, SetSystemAct.REQUEST);
        });

        mAppToolbar.setOnMenuItemClickListener(item -> {
            getPresenter().checkUserIsLogin(R.id.menu_edit_info);
            return false;
        });
        final String[] navTitle = getResources().getStringArray(R.array.me_nav_title);
        final int[] mResIcon = {R.drawable.ic_me_msg, R.drawable.ic_me_order,
                    R.drawable.ic_me_record, R.drawable.ic_me_address, R.drawable.ic_me_about};
        mMeNavListAdapter = new MeNavListAdapter(navTitle, mResIcon);
        mMeNavListAdapter.setOnItemClickListener(this);
        mRvMeNavList.setAdapter(mMeNavListAdapter);
        mDynamic.setOnClickListener(this);
        mCollect.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mFans.setOnClickListener(this);
        mBtnUserHome.setOnClickListener(this);
        mClNotLogin.setOnClickListener(this);

        if (isAdded()) {
//            mUserID = getString(R.string.txt_user_id);
        }

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    @Override
    public void onItemClick(int position, Integer entity, View view) {
        if (position == 4) {
            AboutUsAct.startAboutUsAct(mFragmentActivity);
        } else {
            getPresenter().checkUserIsLogin(position);

        }
    }

    private boolean getToken() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mFragmentActivity.getApplicationContext().getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE);
        }
        mToken = mSharedPreferences.getString(AppConfig.USER_TOKEN, null);
        return mToken == null;
    }

    @Override
    public void onClick(View v) {

        if (getToken()) {
            Intent intent = new Intent(mFragmentActivity, LoginAndRegisterAct.class);
            startActivityForResult(intent, LoginAndRegisterAct.LOGIN_REQUEST);
            return;
        }
        switch (v.getId()) {
            case R.id.me_dynamic:
            case R.id.btn_user_home:
                Intent intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                startActivity(intent);
                break;
            case R.id.me_collect:
                CollectManagerAct.startCollectMangerAct(mFragmentActivity);
                break;
            case R.id.me_attention:
                MineFocusPeopleAct.startMeAttentionPeopleAct(mFragmentActivity);
                break;
            case R.id.me_fans:
                MineFansAct.startMeFansAct(mFragmentActivity);
                break;

        }
    }

    @Override
    protected void userStartAction(int viewId) {
        Intent intent;
        switch (viewId) {
            case R.id.menu_edit_info:
                UserInfoEditAct.startUserInfoEditAct(mFragmentActivity);
                break;
            case 0://msg
                MsgManagerAct.startMsgManagerAct(mFragmentActivity);
                break;
            case 1://order
                OrderManagerAct.startOrderManagerAct(mFragmentActivity);
                break;
            case 2://record
                intent = new Intent(mFragmentActivity, FitRecordAct.class);
                startActivity(intent);
                break;
            case 3://address
                intent = new Intent(mFragmentActivity, AddressManagerAct.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getPresenter().getMineInfo();
    }

    @Override
    public void onUserInfo(UserInfoEntity userInfoEntity) {
        try {
            if (mRlIsLogin.getVisibility() == View.GONE) {
                mClNotLogin.setVisibility(View.GONE);
                mRlIsLogin.setVisibility(View.VISIBLE);
            }
            mTvNickName.setSelected(true);
            mTvNickName.setText(userInfoEntity.getUserNickName());
            GlideApp.with(this)
                    .load(userInfoEntity.getUserOriginalHead())
                    .circleCrop()
                    .into(mIvAvatar);
            mUserID = userInfoEntity.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTotalCount(TotalCountBean totalCountBean) {
        try {
            if (mRlIsLogin.getVisibility() == View.GONE) {
                mClNotLogin.setVisibility(View.GONE);
                mRlIsLogin.setVisibility(View.VISIBLE);
            }
            mTvDynamicNum.setText(String.valueOf(totalCountBean.getUserDynamicCount()));
            mTvCollectNum.setText(String.valueOf(totalCountBean.getCollectionCount()));
            mTvAttentionNum.setText(String.valueOf(totalCountBean.getUserFocusCount()));
            mTvFansNum.setText(String.valueOf(totalCountBean.getUserFansCount()));
            mTvId_Like.setText("ID:" + mUserID + " 被赞次数:" + totalCountBean.getUserLikeCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showError(int errorCode, String throwableContent) {
        if (errorCode == ShowConfig.ERROR_USER) {
            if (mClNotLogin.getVisibility() == View.GONE) {
                mRlIsLogin.setVisibility(View.GONE);
                mClNotLogin.setVisibility(View.VISIBLE);
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SetSystemAct.REQUEST && resultCode == SetSystemAct.LOGIN_OUT) {
//            getPresenter().getMineInfo();
//        }
//    }
}
