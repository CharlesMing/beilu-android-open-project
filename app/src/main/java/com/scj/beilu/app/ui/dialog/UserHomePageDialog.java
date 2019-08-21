package com.scj.beilu.app.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/3/2 16:16
 */
public class UserHomePageDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private FrameLayout mFlContentView;
    private TextView mTvEditInfo;
    private TextView mTvEditPicture;
    private TextView mTvClose;
    private onClickListener mOnClickListener;

    public interface onClickListener{
        void onClick(View view);
    }

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyBottomSheetDialog(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_home_page_edit, container);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mFlContentView = view.findViewById(R.id.fl_dialog_bg_view);
        mTvEditInfo = view.findViewById(R.id.tv_edit_user_info);
        mTvEditPicture = view.findViewById(R.id.tv_edit_picture);
        mTvClose = view.findViewById(R.id.tv_close);
        mFlContentView.setOnClickListener(this);
        mTvEditInfo.setOnClickListener(this);
        mTvEditPicture.setOnClickListener(this);
        mTvClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
        dismiss();
    }
}
