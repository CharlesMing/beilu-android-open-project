package com.scj.beilu.app.ui.comment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.ui.dialog.MyBottomSheetDialog;

/**
 * @author Mingxun
 * @time on 2019/4/2 21:02
 */
public class SelectPaymentDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private TextView mTvTotalPrice;
    private TextView mTvStartPay;
    private FrameLayout mFlWxPay, mFlAliPay, mFlBgView;
    private ImageView mIvWxPay, mIvAliPay;
    private ImageButton mBtnClose;

    private static final String TOTAL_PRICE = "total_price";
    private String mPrice;
    private boolean mSelectPayment;//true wx false alipay
    private onStartPay mOnStartPay;

    public interface onStartPay {
        /**
         * @param mSelectPayment true wx false alipay
         */
        void startPay(boolean mSelectPayment);
    }

    public void setOnStartPay(onStartPay onStartPay) {
        mOnStartPay = onStartPay;
    }

    public static SelectPaymentDialog newInstance(String price) {

        Bundle args = new Bundle();
        args.putString(TOTAL_PRICE, price);
        SelectPaymentDialog fragment = new SelectPaymentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPrice = arguments.getString(TOTAL_PRICE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_payment, container);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mTvTotalPrice = view.findViewById(R.id.tv_pay_total_price);
        mFlWxPay = view.findViewById(R.id.fl_payment_wx);
        mFlAliPay = view.findViewById(R.id.fl_payment_alipay);
        mFlBgView = view.findViewById(R.id.fl_dialog_bg_view);
        mIvWxPay = view.findViewById(R.id.iv_payment_select_wx);
        mIvAliPay = view.findViewById(R.id.iv_payment_select_alipay);
        mBtnClose = view.findViewById(R.id.btn_payment_close);
        mTvStartPay = view.findViewById(R.id.tv_payment_start);

        mBtnClose.setOnClickListener(this);
        mTvStartPay.setOnClickListener(this);
        mFlBgView.setOnClickListener(this);
        mFlWxPay.setOnClickListener(this);
        mFlAliPay.setOnClickListener(this);

        mTvTotalPrice.setText(mPrice);

        mIvWxPay.setSelected(true);
        mSelectPayment = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fl_payment_wx:
                mIvWxPay.setSelected(true);
                mIvAliPay.setSelected(false);
                mSelectPayment = true;
                break;
            case R.id.fl_payment_alipay:
                mIvAliPay.setSelected(true);
                mIvWxPay.setSelected(false);
                mSelectPayment = false;
                break;
            case R.id.tv_payment_start:
                if (mOnStartPay != null) {
                    mOnStartPay.startPay(mSelectPayment);
                }
                break;
            case R.id.btn_payment_close:
            case R.id.fl_dialog_bg_view:
                dismiss();
                break;

        }
    }
}
