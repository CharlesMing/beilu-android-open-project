package com.scj.beilu.app.ui.mine;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.mine.FeedbackPre;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.AppToolbar;

/**
 * author:SunGuiLan
 * date:2019/3/5
 * descriptin:意见反馈
 */
public class FeedBackFrag extends BaseMvpFragment<FeedbackPre.FeedbackView, FeedbackPre> implements FeedbackPre.FeedbackView {
    private EditText mEtContent;
    private EditText mEtContact;
    private AppToolbar mToolbar;


    @Override
    public FeedbackPre createPresenter() {
        return new FeedbackPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_feedback;
    }

    @Override
    public void initView() {
        super.initView();
        mEtContent = findViewById(R.id.et_content);
        mEtContact = findViewById(R.id.et_contact);
        mToolbar = findViewById(R.id.toolbar);
        mEtContent.addTextChangedListener(mTextWatcher);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    CheckUtils.checkStringIsNull(mEtContent.getText().toString(), "请输入内容");
                } catch (Exception e) {
                    ToastUtils.showToast(mFragmentActivity, e.getMessage());
                } finally {
                    getPresenter().addFeedBack(mEtContent.getText().toString(), mEtContact.getText().toString());
                }
                return false;
            }
        });


    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len >= 120) {
                ToastUtils.showToast(mFragmentActivity, "字数不能超过120字");
            }
        }
    };

    @Override
    public void onAddFeedBackResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        mFragmentActivity.finish();
    }
}
