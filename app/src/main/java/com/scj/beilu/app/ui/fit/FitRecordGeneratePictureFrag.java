package com.scj.beilu.app.ui.fit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.fit.FitRecordGeneratePicturePre;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/15 13:26
 * 生成对比照
 */
public class FitRecordGeneratePictureFrag extends BaseMvpFragment<FitRecordGeneratePicturePre.FitRecordGeneratePictureView, FitRecordGeneratePicturePre>
        implements FitRecordGeneratePicturePre.FitRecordGeneratePictureView, View.OnClickListener {

    private static final String IMG_PATH_LIST = "img_list";
    private List<String> mImgList;
    private ImageView mIvLeftImg, mIvRightImg;
    private LinearLayout mLlGenerateView;
    private TextView tv_share_to_wechat, tv_share_to_wechat_firend, mTvSave;
    private boolean mUseWaterMark = false;
    private IWXAPI mIWXAPI;

    public static FitRecordGeneratePictureFrag newInstance(List<String> imgList) {

        Bundle args = new Bundle();
        args.putSerializable(IMG_PATH_LIST, (Serializable) imgList);
        FitRecordGeneratePictureFrag fragment = new FitRecordGeneratePictureFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mImgList = (List<String>) arguments.getSerializable(IMG_PATH_LIST);
        }
        mIWXAPI = WXAPIFactory.createWXAPI(mFragmentActivity, Constants.WXAPPID);
    }

    @Override
    public FitRecordGeneratePicturePre createPresenter() {
        return new FitRecordGeneratePicturePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_fit_record_generate_contrast;
    }

    @Override
    public void initView() {
        super.initView();
        mIvLeftImg = findViewById(R.id.iv_left_img);
        mIvRightImg = findViewById(R.id.iv_right_img);
        mLlGenerateView = findViewById(R.id.ll_generate_view);
        mTvSave = findViewById(R.id.tv_save);
        tv_share_to_wechat = findViewById(R.id.tv_share_to_wechat);
        tv_share_to_wechat_firend = findViewById(R.id.tv_share_to_wechat_firend);

        try {
            if (mImgList.size() == 0) return;
            GlideApp.with(this)
                    .load(mImgList.get(0))
                    .centerCrop()
                    .into(mIvLeftImg);
            GlideApp.with(this)
                    .load(mImgList.get(1))
                    .centerCrop()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mUseWaterMark = false;
                            getPresenter().startGenerate(mLlGenerateView, mUseWaterMark);
                            return false;
                        }
                    })
                    .into(mIvRightImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mTvSave.setOnClickListener(this);
        tv_share_to_wechat.setOnClickListener(this);
        tv_share_to_wechat_firend.setOnClickListener(this);
    }

    @Override
    public void onGenerateResult(FitRecordImgResultBean result) {

        if (result.getReq() == null) {
            if (!mUseWaterMark) {
                setFragmentResult(RESULT_OK, null);
            }
            ToastUtils.showToast(mFragmentActivity, result.getResult());
        } else {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            mIWXAPI.sendReq(result.getReq());
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_save:
                mUseWaterMark = true;
                getPresenter().startGenerate(mLlGenerateView, mUseWaterMark);
                break;
            case R.id.tv_share_to_wechat:
                getPresenter().shareToImage(mLlGenerateView, SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.tv_share_to_wechat_firend:
                getPresenter().shareToImage(mLlGenerateView, SendMessageToWX.Req.WXSceneTimeline);
                break;
        }
    }

    @Subscribe
    public void onShareResult(BaseResp resp) {
        ToastUtils.showToast(mFragmentActivity, "分享成功");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
