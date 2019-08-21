package com.scj.beilu.app.ui.find;

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
import com.scj.beilu.app.ui.dialog.MyBottomSheetDialog;

/**
 * @author Mingxun
 * @time on 2019/3/2 16:16
 */
public class FindShareDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private FrameLayout mFlContentView;
    private TextView mTvShareWeChat;
    private TextView mTvShareFriends;
    private TextView mTvDel;

    private static final String USER_ID = "user_id";
    private int mUserId = 0;
    private onDelFind mOnDelFind;

    public interface onDelFind {
        void onDel(int viewId);
    }

    public void setOnDelFind(onDelFind onDelFind) {
        mOnDelFind = onDelFind;
    }

    public static FindShareDialog newInstance(int userId) {

        Bundle args = new Bundle();
        args.putInt(USER_ID, userId);
        FindShareDialog fragment = new FindShareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyBottomSheetDialog(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUserId = arguments.getInt(USER_ID, mUserId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_find_share, container);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        mFlContentView = view.findViewById(R.id.fl_dialog_bg_view);
        mTvShareWeChat = view.findViewById(R.id.tv_share_to_wechat);
        mTvShareFriends = view.findViewById(R.id.tv_share_to_wechat_firend);
        mTvDel = view.findViewById(R.id.tv_share_del);

        mFlContentView.setOnClickListener(this);
        mTvShareFriends.setOnClickListener(this);
        mTvShareWeChat.setOnClickListener(this);
        mTvDel.setOnClickListener(this);

        mTvDel.setVisibility(mUserId == -1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mOnDelFind != null) {
            mOnDelFind.onDel(v.getId());
        }

    }

}
