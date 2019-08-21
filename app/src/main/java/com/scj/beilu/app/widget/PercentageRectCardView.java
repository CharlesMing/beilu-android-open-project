package com.scj.beilu.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/3/16 17:04
 */
public class PercentageRectCardView extends CardView {

    private float mPercentage = 0.72f;

    public PercentageRectCardView(@NonNull Context context) {
        this(context, null);
    }

    public PercentageRectCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentageRectCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.PercentageRectCardView);
        if (attributes.hasValue(R.styleable.PercentageRectCardView_height_percentage)) {
            mPercentage = attributes.getFloat(R.styleable.PercentageRectCardView_height_percentage, mPercentage);
        }
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int) (width * mPercentage);
        setMeasuredDimension(width, height);
    }
}
