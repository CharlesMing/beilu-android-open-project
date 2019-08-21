package com.scj.beilu.app.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author Mingxun
 * @time on 2019/2/11 16:50
 */
public class NewsDetailsWebView extends WebView {

    private ProgressBar mProgressBar;

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            view.getSettings().setBlockNetworkImage(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.getSettings().setBlockNetworkImage(false);

        }
    };
    private boolean isLoad = false;
    private WebChromeClient mChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
            } else {
                mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                mProgressBar.setProgress(newProgress);//设置进度值
            }
            super.onProgressChanged(view, newProgress);
            if (newProgress > 70 && !isLoad) {
                if (getIsFocus() == 1) {//1已关注，0未关注
                    //已关注
                    view.loadUrl("javascript:changeAttention(false)");
                } else {
                    //为关注
                    view.loadUrl("javascript:changeAttention(true)");
                }
                isLoad = true;
            }


        }
    };

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    private int isFocus;

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public NewsDetailsWebView(Context context) {
        this(context, null);
    }

    public NewsDetailsWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsDetailsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWebViewClient(client);
        this.setWebChromeClient(mChromeClient);
        initWebViewSettings();
    }


    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            webSetting.setSafeBrowsingEnabled(false);
//        }

        String ua = getSettings().getUserAgentString();
        webSetting.setUserAgentString(ua + ";beiluKit");
        this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        IX5WebViewExtension ix5 = getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }
        // settings 的设计
    }

}
