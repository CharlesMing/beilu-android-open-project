package com.scj.beilu.app.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.widget.RuleView;

/**
 * @author Mingxun
 * @time on 2019/3/12 15:26
 */
public class RuleViewDialog extends AppCompatDialogFragment implements RuleView.OnValueChangedListener, View.OnClickListener {


    private TextView mTvRuleVal;
    private TextView mTvUnit;
    private RuleView mRuleView;
    private TextView mTvCancel, mTvSave;
    private static final String UNIT = "unit";
    private String mUnit;

    private OnGirth mOnGirth;
    private float mVal;

    public interface OnGirth {
        void onGirthVal(float val);
    }

    public static RuleViewDialog newInstance(String unit) {

        Bundle args = new Bundle();
        args.putString(UNIT, unit);
        RuleViewDialog fragment = new RuleViewDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnGirth(OnGirth onGirth) {
        mOnGirth = onGirth;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUnit = arguments.getString(UNIT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rule_view, container);
        mRuleView = view.findViewById(R.id.rule_view);
        mTvRuleVal = view.findViewById(R.id.tv_rule_val);
        mTvUnit = view.findViewById(R.id.tv_unit);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvSave = view.findViewById(R.id.tv_save);
        mRuleView.setOnValueChangedListener(this);
        mTvSave.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mUnit.equals("kg")) {
            mRuleView.setValue(0, 200, 50, 0.1f, 10);
        } else if (mUnit.equals("%")) {
            mRuleView.setValue(0, 100, 1, 0.1f, 10);
        } else {
            mRuleView.setValue(0, 150, 30, 0.1f, 10);
        }
        mTvRuleVal.setText(Float.toString(mRuleView.getCurrentValue()));
        mTvUnit.setText(mUnit);
    }

    @Override
    public void onValueChanged(float value) {
        mVal = value;
        mTvRuleVal.setText(Float.toString(value));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_save:
                if (mOnGirth != null) {
                    mOnGirth.onGirthVal(mVal);
                }
                dismiss();
                break;
        }
    }

}
