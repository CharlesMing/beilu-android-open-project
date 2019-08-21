package com.scj.beilu.app.widget.expandtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Layout;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;

import android.view.View;


import com.scj.beilu.app.R;

/**
 * author:SunGuiLan
 * date:2019/2/2
 * descriptin:可折叠的文字
 */
public class FolderTextView extends android.support.v7.widget.AppCompatTextView {

    // TAG
    private static final String TAG = "xiaobo";

    // 默认打点文字
    private static final String DEFAULT_ELLIPSIZE = "...";
    // 默认收起文字
    private static final String DEFAULT_FOLD_TEXT = "[收起]";
    // 默认展开文字
    private static final String DEFAULT_UNFOLD_TEXT = "[查看全部]";
    // 默认固定行数
    private static final int DEFAULT_FOLD_LINE = 2;
    // 默认收起和展开文字颜色
    private static final int DEFAULT_TAIL_TEXT_COLOR = Color.GRAY;
    // 默认是否可以再次收起
    private static final boolean DEFAULT_CAN_FOLD_AGAIN = true;

    // 收起文字
    private String mFoldText;
    // 展开文字
    private String mUnFoldText;
    // 固定行数
    private int mFoldLine;
    // 尾部文字颜色
    private int mTailColor;
    // 是否可以再次收起
    private boolean mCanFoldAgain;

    // 收缩状态
    private boolean mIsFold = false;
    // 绘制，防止重复进行绘制
    private boolean mHasDrawn = false;
    // 内部绘制
    private boolean mIsInner = false;

    // 全文本
    private String mFullText;
    // 行间距倍数
    private float mLineSpacingMultiplier = 1.2f;
    // 行间距额外像素
    private float mLineSpacingExtra = 0.0f;

    // 统计使用二分法裁剪源文本的次数
    private int mCountBinary = 0;
    // 统计使用备用方法裁剪源文本的次数
    private int mCountBackUp = 0;
    // 统计onDraw调用的次数
    private int mCountOnDraw = 0;

    // 点击处理
    private ClickableSpan clickSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            mIsFold = !mIsFold;
            mHasDrawn = false;
            invalidate();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mTailColor);
        }
    };

    /**
     * 构造
     *
     * @param context 上下文
     */
    public FolderTextView(Context context) {
        this(context, null);
    }

    /**
     * 构造
     *
     * @param context 上下文
     * @param attrs   属性
     */
    public FolderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造
     *
     * @param context      上下文
     * @param attrs        属性
     * @param defStyleAttr 样式
     */
    public FolderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FolderTextView);
        mFoldText = a.getString(R.styleable.FolderTextView_foldText);
        if (null == mFoldText) {
            mFoldText = DEFAULT_FOLD_TEXT;
        }
        mUnFoldText = a.getString(R.styleable.FolderTextView_unFoldText);
        if (null == mUnFoldText) {
            mUnFoldText = DEFAULT_UNFOLD_TEXT;
        }
        mFoldLine = a.getInt(R.styleable.FolderTextView_foldLine, DEFAULT_FOLD_LINE);
        if (mFoldLine < 1) {
            throw new RuntimeException("foldLine must not less than 1");
        }
        mTailColor = a.getColor(R.styleable.FolderTextView_tailTextColor, DEFAULT_TAIL_TEXT_COLOR);
        mCanFoldAgain = a.getBoolean(R.styleable.FolderTextView_canFoldAgain, DEFAULT_CAN_FOLD_AGAIN);
        a.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(mFullText) || !mIsInner) {
            mHasDrawn = false;
            mFullText = String.valueOf(text);
        }
        super.setText(text, type);
    }

    @Override
    public void setLineSpacing(float extra, float multiplier) {
        mLineSpacingExtra = extra;
        mLineSpacingMultiplier = multiplier;
        super.setLineSpacing(extra, multiplier);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 必须解释下：由于为了得到实际一行的宽度（makeTextLayout()中需要使用），必须要先把源文本设置上，然后再裁剪至指定行数；
        // 这就导致了该TextView会先布局一次高度很高（源文本行数高度）的布局，裁剪后再次布局成指定行数高度，因而下方View会抖动；
        // 这里的处理是，super.onMeasure()已经计算出了源文本的实际宽高了，取出指定行数的文本再次测量一下其高度，
        // 然后把这个高度设置成最终的高度就行了！
        if (!mIsFold) {
            Layout layout = getLayout();
            int line = getFoldLine();
            if (line < layout.getLineCount()) {
                int index = layout.getLineEnd(line - 1);
                if (index > 0) {
                    // 得到一个字符串，该字符串恰好占据mFoldLine行数的高度
                    String strWhichHasExactlyFoldLine = getText().subSequence(0, index).toString();
                    Log.d(TAG, "strWhichHasExactlyFoldLine-->" + strWhichHasExactlyFoldLine);
                    layout = makeTextLayout(strWhichHasExactlyFoldLine);
                    // 把这个高度设置成最终的高度，这样下方View就不会抖动了
                    setMeasuredDimension(getMeasuredWidth(), layout.getHeight() + getPaddingTop() + getPaddingBottom());
                }
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() " + mCountOnDraw++ + ", getMeasuredHeight() " + getMeasuredHeight());
        if (!mHasDrawn) {
            resetText();
        }
        super.onDraw(canvas);
        mHasDrawn = true;
        mIsInner = false;
    }

    /**
     * 获取折叠文字
     *
     * @return 折叠文字
     */
    public String getFoldText() {
        return mFoldText;
    }

    /**
     * 设置折叠文字
     *
     * @param foldText 折叠文字
     */
    public void setFoldText(String foldText) {
        mFoldText = foldText;
        invalidate();
    }

    /**
     * 获取展开文字
     *
     * @return 展开文字
     */
    public String getUnFoldText() {
        return mUnFoldText;
    }

    /**
     * 设置展开文字
     *
     * @param unFoldText 展开文字
     */
    public void setUnFoldText(String unFoldText) {
        mUnFoldText = unFoldText;
        invalidate();
    }

    /**
     * 获取折叠行数
     *
     * @return 折叠行数
     */
    public int getFoldLine() {
        return mFoldLine;
    }

    /**
     * 设置折叠行数
     *
     * @param foldLine 折叠行数
     */
    public void setFoldLine(int foldLine) {
        mFoldLine = foldLine;
        invalidate();
    }

    /**
     * 获取尾部文字颜色
     *
     * @return 尾部文字颜色
     */
    public int getTailColor() {
        return mTailColor;
    }

    /**
     * 设置尾部文字颜色
     *
     * @param tailColor 尾部文字颜色
     */
    public void setTailColor(int tailColor) {
        mTailColor = tailColor;
        invalidate();
    }

    /**
     * 获取是否可以再次折叠
     *
     * @return 是否可以再次折叠
     */
    public boolean isCanFoldAgain() {
        return mCanFoldAgain;
    }

    /**
     * 获取全文本
     *
     * @return 全文本
     */
    public String getFullText() {
        return mFullText;
    }

    /**
     * 设置是否可以再次折叠
     *
     * @param canFoldAgain 是否可以再次折叠
     */
    public void setCanFoldAgain(boolean canFoldAgain) {
        mCanFoldAgain = canFoldAgain;
        invalidate();
    }

    /**
     * 获取TextView的Layout，注意这里使用getWidth()得到宽度
     *
     * @param text 源文本
     * @return Layout
     */
    private Layout makeTextLayout(String text) {
        return new StaticLayout(text, getPaint(), getWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment
                .ALIGN_NORMAL, mLineSpacingMultiplier, mLineSpacingExtra, true);
    }

    /**
     * 重置文字
     */
    private void resetText() {
        // 文字本身就小于固定行数的话，不添加尾部的收起/展开文字
        Layout layout = makeTextLayout(mFullText);
        if (layout.getLineCount() <= getFoldLine()) {
            setText(mFullText);
            return;
        }

        SpannableString spanStr = new SpannableString(mFullText);

        if (mIsFold) {
            // 收缩状态
            if (mCanFoldAgain) {
                spanStr = createUnFoldSpan(mFullText);
            }
        } else {
            // 展开状态
            spanStr = createFoldSpan(mFullText);
        }

        updateText(spanStr);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 不更新全文本下，进行展开和收缩操作
     *
     * @param text 源文本
     */
    private void updateText(CharSequence text) {
        mIsInner = true;
        setText(text);
    }

    /**
     * 创建展开状态下的Span
     *
     * @param text 源文本
     * @return 展开状态下的Span
     */
    private SpannableString createUnFoldSpan(String text) {
        String destStr = text + mFoldText;
        int start = destStr.length() - mFoldText.length();
        int end = destStr.length();

        SpannableString spanStr = new SpannableString(destStr);
        spanStr.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 创建收缩状态下的Span
     *
     * @param text
     * @return 收缩状态下的Span
     */
    private SpannableString createFoldSpan(String text) {
        String destStr = tailorText(text);

        int start = destStr.length() - mUnFoldText.length();
        int end = destStr.length();

        SpannableString spanStr = new SpannableString(destStr);
        spanStr.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 裁剪文本至固定行数（备用方法）
     *
     * @param text 源文本
     * @return 裁剪后的文本
     */
    private String tailorTextBackUp(String text) {
        Log.d(TAG, "使用备用方法: tailorTextBackUp() " + mCountBackUp++);

        String destStr = text + DEFAULT_ELLIPSIZE + mUnFoldText;
        Layout layout = makeTextLayout(destStr);

        // 如果行数大于固定行数
        if (layout.getLineCount() > getFoldLine()) {
            int index = layout.getLineEnd(getFoldLine() - 1);
            if (text.length() < index) {
                index = text.length();
            }
            // 从最后一位逐渐试错至固定行数（可以考虑用二分法改进）
            if (index <= 1) {
                return DEFAULT_ELLIPSIZE + mUnFoldText;
            }
            String subText = text.substring(0, index - 1);
            return tailorText(subText);
        } else {
            return destStr;
        }
    }

    /**
     * 裁剪文本至固定行数（二分法）。经试验，在文字长度不是很长时，效率比备用方法高不少；当文字长度过长时，备用方法则优势明显。
     *
     * @param text 源文本
     * @return 裁剪后的文本
     */
    private String tailorText(String text) {
        // return tailorTextBackUp(text);

        int start = 0;
        int end = text.length() - 1;
        int mid = (start + end) / 2;
        int find = finPos(text, mid);
        while (find != 0 && end > start) {
            Log.d(TAG, "使用二分法: tailorText() " + mCountBinary++);
            if (find > 0) {
                end = mid - 1;
            } else if (find < 0) {
                start = mid + 1;
            }
            mid = (start + end) / 2;
            find = finPos(text, mid);
        }
        Log.d(TAG, "mid is: " + mid);

        String ret;
        if (find == 0) {
            ret = text.substring(0, mid) + DEFAULT_ELLIPSIZE + mUnFoldText;
        } else {
            ret = tailorTextBackUp(text);
        }
        return ret;
    }

    /**
     * 查找一个位置P，到P时为mFoldLine这么多行，加上一个字符‘A’后则刚好为mFoldLine+1这么多行
     *
     * @param text 源文本
     * @param pos  位置
     * @return 查找结果
     */
    private int finPos(String text, int pos) {
        String destStr = text.substring(0, pos) + DEFAULT_ELLIPSIZE + mUnFoldText;
        Layout layout = makeTextLayout(destStr);
        Layout layoutMore = makeTextLayout(destStr + "A");

        int lineCount = layout.getLineCount();
        int lineCountMore = layoutMore.getLineCount();

        if (lineCount == getFoldLine() && (lineCountMore == getFoldLine() + 1)) {
            // 行数刚好到折叠行数
            return 0;
        } else if (lineCount > getFoldLine()) {
            // 行数比折叠行数多
            return 1;
        } else {
            // 行数比折叠行数少
            return -1;
        }
    }

}

