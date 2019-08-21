package com.scj.beilu.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2018/12/29 16:04
 */
public class AppToolbar extends Toolbar {

    private boolean mIsShowBack;
    public AppCompatTextView mTitleTextView;

    public AppToolbar(Context context) {
        this(context, null);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    @SuppressLint("RestrictedApi")
    public AppToolbar(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTitle();

        TintTypedArray array =
                TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AppToolbar, defStyleAttr, 0);
        mIsShowBack = array.getBoolean(R.styleable.AppToolbar_showBack, true);
        if (array.hasValue(R.styleable.AppToolbar_menu)) {
            inflateMenu(array.getResourceId(R.styleable.AppToolbar_menu, 0));
        }
        final CharSequence title = array.getText(R.styleable.AppToolbar_toolbar_title);
        if (!TextUtils.isEmpty(title)) {
            setToolbarTitle(title);
        }

        if (array.hasValue(R.styleable.AppToolbar_toolbar_background)) {
            Drawable drawable = array.getDrawable(R.styleable.AppToolbar_toolbar_background);
            ViewCompat.setBackground(this, drawable);
        } else {
            ViewCompat.setBackground(this, ContextCompat.getDrawable(context, android.R.color.white));
        }

        if (array.hasValue(R.styleable.AppToolbar_toolbar_title_text_color)) {
            int mTextColor = array.getColor(R.styleable.AppToolbar_toolbar_title_text_color, 0);
            setToolbarTitleTextColor(mTextColor);
        }
        if (array.hasValue(R.styleable.AppToolbar_toolbar_title_text_size)) {
            float mTextSize = array.getDimensionPixelOffset(R.styleable.AppToolbar_toolbar_title_text_size, 0);
            setTitleTextSize(mTextSize);
        }

        if (!array.hasValue(R.styleable.AppToolbar_toolbar_show_elevation)) {
            ViewCompat.setElevation(this, 10);
        }

        array.recycle();

        if (mIsShowBack) {
            setNavigationIcon(ContextCompat.getDrawable(context, R.drawable.ic_back));
        }

    }

    private void initTitle() {
        if (mTitleTextView == null) {
            Context context = getContext();
            View view = LayoutInflater
                    .from(context).inflate(R.layout.toolbar_title, this, true);
            mTitleTextView = view.findViewById(R.id.tv_toolbar_title);
        }
    }

    public void setToolbarTitle(CharSequence title) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }

    }

    public void setToolbarTitleTextColor(@ColorInt int color) {
        if (mTitleTextView != null) {
            if (color != 0) {
                mTitleTextView.setTextColor(color);
            }
        }
    }

    public void setTitleTextSize(float size) {
        if (mTitleTextView != null) {
            if (size != 0) {
                mTitleTextView.getPaint().setTextSize(size);
                invalidate();
            }
        }
    }
}
