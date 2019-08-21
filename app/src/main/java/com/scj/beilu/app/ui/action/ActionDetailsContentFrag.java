package com.scj.beilu.app.ui.action;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.action.ActionDetailsContentPre;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.widget.ActionDetailsWebView;
import com.scj.beilu.app.widget.video.EmptyControlVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

/**
 * @author Mingxun
 * @time on 2019/3/18 21:12
 */
public class ActionDetailsContentFrag extends BaseMvpFragment<ActionDetailsContentPre.ActionDetailsContentView, ActionDetailsContentPre>
        implements ActionDetailsContentPre.ActionDetailsContentView {

    private ActionDetailsWebView mActionDetailsWebView;
    private EmptyControlVideo mGSYVideoPlayer;
    private static final String EXTRA_ACTION_ID = "action_id";
    private static final String EXTRA_ACTION_TITLE = "action_title";

    private int mActionId;
    private String mActionTitle;

    private GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public static ActionDetailsContentFrag newInstance(int actionId, String actionTitle) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_ACTION_ID, actionId);
        args.putString(EXTRA_ACTION_TITLE, actionTitle);
        ActionDetailsContentFrag fragment = new ActionDetailsContentFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mActionId = arguments.getInt(EXTRA_ACTION_ID);
            mActionTitle = arguments.getString(EXTRA_ACTION_TITLE);
        }
    }

    @Override
    public ActionDetailsContentPre createPresenter() {
        return new ActionDetailsContentPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_action_details;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar
                .with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(false)
                .init();
        mGSYVideoPlayer = findViewById(R.id.action_detail_video);
        mActionDetailsWebView = findViewById(R.id.web_action_details);
        mAppToolbar.setToolbarTitle(mActionTitle);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getActionDetailsById(mActionId);
    }

    @Override
    public void onActionInfoResult(ActionInfoBean actionInfo) {
        String videoAddr = actionInfo.getActionVideoAddr();
        if (videoAddr != null) {
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
            gsyVideoOptionBuilder
                    .setIsTouchWiget(false)
                    .setUrl(videoAddr)
                    .setVideoTitle("")
                    .setCacheWithPlay(true)
                    .setStartAfterPrepared(true)
                    .setLooping(true)
                    .build(mGSYVideoPlayer);
            mGSYVideoPlayer.startPlayLogic();
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        }

        mActionDetailsWebView.loadDataWithBaseURL(null, actionInfo.getActionContent(), "text/html", "utf-8", null);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mGSYVideoPlayer != null) {
            if (!mGSYVideoPlayer.isInPlayingState()) {
                mGSYVideoPlayer.startPlayLogic();
            }
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (mGSYVideoPlayer != null) {
            if (mGSYVideoPlayer.isInPlayingState()) {
                mGSYVideoPlayer.onVideoPause();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGSYVideoPlayer.release();

    }

    @Override
    public boolean onBackPressedSupport() {
        //释放所有
        mGSYVideoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
        return super.onBackPressedSupport();
    }
}
