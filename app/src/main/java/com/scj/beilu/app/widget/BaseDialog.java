package com.scj.beilu.app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.scj.beilu.app.R;

/**
 * author: SunGuiLan
 * date:2019/2/1
 * descriptin:对话框 位置、大小、布局、动画
 */
public class BaseDialog extends Dialog {
    private Context mContext;
    private boolean mOnTouchCanceled;
    private View mView;
    private int mStyleAnimation;
    private int mGravity;
    private int mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBotton;

    public BaseDialog(Builder builder) {
        super(builder.bContext);
    }

    public BaseDialog(Builder builder, int themeResId) {
        super(builder.bContext, themeResId);
        mContext = builder.bContext;
        mView = builder.bView;
        mGravity = builder.bGravity;
        mStyleAnimation = builder.bStyleAnimation;
        mOnTouchCanceled = builder.bOnTouchCanceled;
        mPaddingLeft = builder.bPaddingLeft;
        mPaddingTop = builder.bPaddingTop;
        mPaddingRight = builder.bPaddingRight;
        mPaddingBotton = builder.bPaddingBotton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        setCanceledOnTouchOutside(mOnTouchCanceled);
        //弹窗大小
        Window window = getWindow();
        window.getDecorView().setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBotton);
        window.setWindowAnimations(mStyleAnimation);
        window.setGravity(mGravity);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    public static final class Builder {
        private Context bContext;
        private boolean bOnTouchCanceled;
        private View bView;
        private int bThemeResId = R.style.dialog_style;
        private int bStyleAnimation;
        private int bGravity;
        private int bPaddingLeft, bPaddingTop, bPaddingRight, bPaddingBotton;


        public Builder(Context mContext) {
            this.bContext = mContext;
        }


        public Builder setViewId(int viewId) {
            this.bView = LayoutInflater.from(bContext).inflate(viewId, null);
            return this;
        }


        public Builder isOnTouchCanceled(boolean var) {
            this.bOnTouchCanceled = var;
            return this;
        }

        public Builder setPaddingdp(int paddingLeft, int paddingTop, int paddingRight, int paddingBotton) {
            this.bPaddingLeft = dip2px(bContext, paddingLeft);
            this.bPaddingTop = dip2px(bContext, paddingTop);
            this.bPaddingRight = dip2px(bContext, paddingRight);
            this.bPaddingBotton = dip2px(bContext, paddingBotton);
            return this;
        }

        public Builder setPaddingpx(int paddingLeft, int paddingTop, int paddingRight, int paddingBotton) {
            this.bPaddingLeft = paddingLeft;
            this.bPaddingTop = paddingTop;
            this.bPaddingRight = paddingRight;
            this.bPaddingBotton = paddingBotton;
            return this;
        }

        public Builder addViewOnClickListener(int viewId, View.OnClickListener listener) {
            this.bView.findViewById(viewId).setOnClickListener(listener);
            return this;
        }


        public Builder setGravity(int gravity) {
            this.bGravity = gravity;
            return this;
        }

        public Builder setAnimation(int styleAnimation) {
            this.bStyleAnimation = styleAnimation;
            return this;
        }


        public BaseDialog builder() {
            return new BaseDialog(this, bThemeResId);
        }
    }

    public void close() {
        if (!((Activity) mContext).isFinishing()) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isShowing()) {
                        dismiss();
                    }
                }
            });
        }
    }

    //将dp转换为px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public <T extends View> T getView(int resId) {
        if (mView != null) {
            return (T) mView.findViewById(resId);
        }
        return null;
    }
}
