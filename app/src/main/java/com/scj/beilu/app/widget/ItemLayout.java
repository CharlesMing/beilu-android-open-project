package com.scj.beilu.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/1/12 16:30
 */
public class ItemLayout extends LinearLayout {

    TextView leftTextView;
    TextView rightTextView;

    public ItemLayout(Context context) {
        this(context, null);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.layout_item, this, true);
        leftTextView = findViewById(R.id.tv_item_left_text);
        rightTextView = findViewById(R.id.tv_item_right_text);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout);
            if (array.hasValue(R.styleable.ItemLayout_left_text)) {
                if (leftTextView != null) {
                    setLeftText(array.getString(R.styleable.ItemLayout_left_text));
                }
            }

            if (array.hasValue(R.styleable.ItemLayout_right_text_hint)) {
                if (rightTextView != null) {
                    rightTextView.setHint(array.getString(R.styleable.ItemLayout_right_text_hint));
                }
            }
            if (array.hasValue(R.styleable.ItemLayout_right_text)) {
                if (rightTextView != null) {
                    setRightText(array.getString(R.styleable.ItemLayout_right_text));

                }
            }
            array.recycle();

        }
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
    }

    public void setLeftText(CharSequence text) {
        if (leftTextView != null) {
            leftTextView.setText(text);
        }
    }


    public void setRightText(CharSequence text) {
        if (rightTextView != null) {
            rightTextView.setText(text);
        }
    }


    public String getLeftText() {
        return leftTextView.getText().toString();
    }
}
