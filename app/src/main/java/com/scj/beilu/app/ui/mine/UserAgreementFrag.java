package com.scj.beilu.app.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.R;
import com.scj.beilu.app.widget.AppToolbar;
import com.scj.beilu.app.widget.MyWebView;

/**
 * @author Mingxun
 * @time on 2019/4/24 22:55
 */
public class UserAgreementFrag extends SupportFragment {

    private AppToolbar mAppToolbar;
    private MyWebView webView;
    private static final String EXTRA_URL = "url";
    private String mUrl;

    public static UserAgreementFrag newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(EXTRA_URL, url);
        UserAgreementFrag fragment = new UserAgreementFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUrl = arguments.getString(EXTRA_URL);
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_user_agreement;
    }

    @Override
    public void initView() {
        super.initView();
        mAppToolbar = findViewById(R.id.toolbar);
        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(true,0.2f)
                .keyboardEnable(true)
                .init();
        webView = findViewById(R.id.webView);
        webView.loadUrl(mUrl);

        mAppToolbar.setNavigationOnClickListener(v -> mFragmentActivity.onBackPressed());
    }
}
