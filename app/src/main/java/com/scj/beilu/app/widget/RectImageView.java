package com.scj.beilu.app.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author Mingxun
 * @time on 2019/2/11 12:20
 */
public class RectImageView extends AppCompatImageView {
    public RectImageView(Context context) {
        this(context, null);
    }

    public RectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = (int) (measuredWidth * 0.75);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
