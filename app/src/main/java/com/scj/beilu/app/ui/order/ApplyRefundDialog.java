package com.scj.beilu.app.ui.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/3/14 15:24
 * 申請退款
 */
public class ApplyRefundDialog extends AppCompatDialogFragment {

    private onRefundContent mOnRefundContent;

    public interface onRefundContent {
        void onContent(String content);
    }

    public void setOnRefundContent(onRefundContent onRefundContent) {
        mOnRefundContent = onRefundContent;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_apply_refund, container);
        final EditText etContent = view.findViewById(R.id.et_order_refund_content);
        TextView tvApply = view.findViewById(R.id.tv_refund_apply);
        tvApply.setOnClickListener(v -> {
            if (mOnRefundContent != null) {
                mOnRefundContent.onContent(etContent.getText().toString());
            }
            dismiss();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            window.setBackgroundDrawableResource(R.drawable.shape_dialog_cancel);
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }

    }


}
