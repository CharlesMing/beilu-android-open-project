package com.scj.beilu.app.ui.mine;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.R;
import com.scj.beilu.app.util.InputValidate;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.AppToolbar;

/**
 * @author Mingxun
 * @time on 2019/4/16 22:12
 */
public class MinBindPhoneFrag extends SupportFragment {

    private AppToolbar mAppToolbar;
    private Button btn_code;
    private EditText et_phone;

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_bind_phone;
    }

    @Override
    public void initView() {
        super.initView();
        btn_code = findViewById(R.id.btn_code);
        et_phone = findViewById(R.id.et_phone);
        mAppToolbar = findViewById(R.id.toolbar);

        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(true,0.2f)
                .keyboardEnable(true)
                .init();
        et_phone.addTextChangedListener(textWatcher);

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MineStartBindPhoneFrag.newInstance(et_phone.getText().toString()));
            }
        });

        mAppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentActivity.onBackPressed();
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 11) {
                btnEnable(InputValidate.mobileFormat(s.toString()), s.toString());
            } else {
                btnEnable(false, null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void btnEnable(boolean enable, String text) {
        if (enable) {
            btn_code.setEnabled(true);
        } else {
            if (text != null) {
                ToastUtils.showToast(mFragmentActivity, "手机号码格式不正确");
            }
            if (btn_code.isEnabled()) {
                btn_code.setEnabled(false);
            }
        }
    }
}
