package com.scj.beilu.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.scj.beilu.app.R;

/**
 * Created by shucc on 17/4/6.
 * cc@cchao.org
 */
public class SwitchButton extends View {

    private final String TAG = getClass().getName();

    //选中时背景色
    private int selectedColor;

    //未选中时背景色
    private int unSelectedColor;

    //选择按钮颜色
    private int buttonColor;

    //选择按钮padding
    private int padding;

    //回弹比例
    private int springback;

    //是否有颜色渐变效果
    private boolean canGradient;

    //动画时间
    private int duration;

    //是否可以滑动
    private boolean canMove;

    //当前是否选中
    private boolean checked = false;

    //是否滑动中
    private boolean isScrolling = false;

    private OnSwitchChangeListener onSwitchChangeListener;

    private Paint paint;
    private RectF rectF;
    private RectF leftArcRectF;
    private RectF rightArcRectF;

    private int width;
    private int height;

    //按钮半径
    private float buttonRadius;

    //按钮圆心距离左侧距离
    private float currentX = 0;

    //按钮圆心距离左侧最小距离
    private float minCurrentX;

    //按钮圆心距离左侧最大距离
    private float maxCurrentX;

    //是否初始化，是否onDraw
    private boolean initSwitchButton = false;
    
    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwitchButton, defStyleAttr, 0);
        canMove = typedArray.getBoolean(R.styleable.SwitchButton_switchButton_move, false);
        canGradient = typedArray.getBoolean(R.styleable.SwitchButton_switchButton_gradient, false);
        selectedColor = typedArray.getColor(R.styleable.SwitchButton_switchButton_selectedColor, Color.RED);
        unSelectedColor = typedArray.getColor(R.styleable.SwitchButton_switchButton_unSelectedColor, Color.GRAY);
        buttonColor = typedArray.getColor(R.styleable.SwitchButton_switchButton_color, Color.WHITE);
        padding = typedArray.getDimensionPixelSize(R.styleable.SwitchButton_switchButton_padding
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        duration = typedArray.getInt(R.styleable.SwitchButton_switchButton_duration, 300);
        springback = typedArray.getInt(R.styleable.SwitchButton_switchButton_springback, 6);
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //初始化各种数据
        if (width == 0) {
            width = getWidth();
            height = getHeight();
            rectF = new RectF(0, 0, width, height);
            leftArcRectF = new RectF(0, 0, height, height);
            rightArcRectF = new RectF(width - height, 0, width, height);
            buttonRadius = height - 2 * padding >> 1;
            minCurrentX = buttonRadius + padding;
            maxCurrentX = width - buttonRadius - padding;
        }

        //初始化设置按钮
        if (!initSwitchButton) {
            currentX = checked ? maxCurrentX : minCurrentX;
            initSwitchButton = true;
        }
        
        //绘制底部背景色
        paint.setColor(checked ? selectedColor : unSelectedColor);
        canvas.drawRoundRect(rectF, height >> 1, height >> 1, paint);

        float x = minCurrentX;
        if (currentX >= x) {
            x = currentX;
        }
        if (x >= maxCurrentX) {
            x = maxCurrentX;
        }
        if (x == maxCurrentX) {
            paint.setColor(selectedColor);
            canvas.drawRoundRect(rectF, height >> 1, height >> 1, paint);
        } else if (x == minCurrentX) {
            paint.setColor(unSelectedColor);
            canvas.drawRoundRect(rectF, height >> 1, height >> 1, paint);
        }

        if (canGradient) {
            //滑动过程中
            if (currentX > minCurrentX && currentX < maxCurrentX) {
                if (checked) {
                    paint.setColor(unSelectedColor);
                    //画圆弧
                    canvas.drawArc(rightArcRectF, -90, 180, true, paint);
                    canvas.drawRect(currentX, 0, width - height / 2, height, paint);
                } else {
                    paint.setColor(selectedColor);
                    //画圆弧
                    canvas.drawArc(leftArcRectF, 90, 180, true, paint);
                    canvas.drawRect(height >> 1, 0, currentX, height, paint);
                }
            }
        }

        //绘制按钮
        paint.setColor(buttonColor);
        canvas.drawCircle(x, minCurrentX, buttonRadius, paint);

        if (isScrolling) {
            //已经滑动到最左边
            if (currentX <= minCurrentX) {
                currentX = minCurrentX;
                isScrolling = false;
            }
            //已经滑动到最右边
            if (currentX >= maxCurrentX) {
                currentX = maxCurrentX;
                isScrolling = false;
            }
        } else {
            //滑动完成
            if (checked && x <= minCurrentX) {
                checked = false;
                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onChange(false);
                }
            } else if (!checked && x >= maxCurrentX) {
                checked = true;
                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onChange(true);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (canMove) {
                    currentX = event.getX();
                    if (currentX <= minCurrentX || currentX >= maxCurrentX) {
                        return false;
                    }
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (canMove) {
                    if (currentX <= minCurrentX || currentX >= maxCurrentX) {
                        return false;
                    } else if (!isScrolling) {
                        isScrolling = true;
                        reset();
                    }
                } else if (!isScrolling) {
                    toggle();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        boolean toRight;
        if (checked) {
            //选中时左滑距离小于四分之一可滑动距离则回弹
            toRight = (currentX- buttonRadius >= (width - buttonRadius * 2 - (width - buttonRadius * 2 ) / springback));
        } else {
            //未选中时右滑距离小于四分之一可滑动距离泽回弹
            toRight = (currentX - buttonRadius >= (width - buttonRadius * 2 ) / springback);
        }
        float distance = toRight ? maxCurrentX - currentX : currentX - minCurrentX;
        SwitchButtonAnim anim = new SwitchButtonAnim(toRight, distance);
        anim.setDuration((long) (distance * duration / (maxCurrentX - minCurrentX)));
        startAnimation(anim);
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    public boolean isChecked() {
        return checked;
    }

    /**
     * 设置选中与不选中
     * @param checked
     *      是否选中
     */
    public void setChecked(final boolean checked) {
        if (this.checked == checked) {
            return;
        }
        if (isScrolling) {
            return;
        }
        isScrolling = true;
        if (this.checked) {
            currentX = maxCurrentX;
        } else {
            currentX = minCurrentX;
        }
        if (getWindowToken() == null) {
            this.checked = checked;
            postInvalidate();
        } else {
            SwitchButtonAnim anim = new SwitchButtonAnim(checked, maxCurrentX - minCurrentX);
            anim.setDuration(duration);
            startAnimation(anim);
        }
    }

    public void toggle() {
        setChecked(!checked);
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanGradient(boolean canGradient) {
        this.canGradient = canGradient;
    }

    public boolean isCanGradient() {
        return canGradient;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getUnSelectedColor() {
        return unSelectedColor;
    }

    public void setUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    /**
     * 按钮自动滑动动画
     */
    private class SwitchButtonAnim extends Animation {

        //需要移动距离
        private float maxDistance;

        //是否右移
        private boolean toRight;

        //当前左侧距离
        private float initCurrentX;

        /**
         *
         * @param toRight
         *      是否向右滑动
         * @param maxDistance
         *      滑动的间距
         */
        public SwitchButtonAnim(boolean toRight, float maxDistance) {
            this.toRight = toRight;
            this.maxDistance = maxDistance;
            //TODO 防止重复动画
            initCurrentX = currentX + (toRight ? 0.01f : -0.01f);
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            setInterpolator(new LinearInterpolator());
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (toRight) {
                currentX = initCurrentX + maxDistance * interpolatedTime;
            } else {
                currentX = initCurrentX - maxDistance * interpolatedTime;
            }
            postInvalidate();
        }
    }

    public interface OnSwitchChangeListener {
        void onChange(boolean isChecked);
    }
}
