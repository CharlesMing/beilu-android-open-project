package com.scj.beilu.app.ui.mine.address;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.mine.address.AddressUpdatePre;
import com.scj.beilu.app.mvp.mine.bean.DistrictInfoBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 更改地址(修改、删除)
 */
public class AddressUpdateAct extends BaseMvpActivity<AddressUpdatePre.AddressUpdateView, AddressUpdatePre> implements AddressUpdatePre.AddressUpdateView, View.OnClickListener {
    private EditText mEtName;
    private EditText mEtTelePhone;
    private EditText mEtDetailPlace;
    private SwitchButton mSbDefaultPlace;
    private RelativeLayout mRlPlace;
    private TextView mTvPlace;
    private Toolbar mToolbar;
    private AddressInfoBean mUserAddressBean;
    private TextView mTvDelete;

    private List<DistrictInfoBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items;
    private int mPosition1;
    private int mPosition2;
    private String opt1tx;
    private String opt2tx;

    @Override
    public int getLayout() {
        return R.layout.act_address_add;
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbar = findViewById(R.id.toolbar);
        mEtName = findViewById(R.id.et_name);
        mRlPlace = findViewById(R.id.rl_place);
        mEtTelePhone = findViewById(R.id.et_phone);
        mEtDetailPlace = findViewById(R.id.et_detail_place);
        mSbDefaultPlace = findViewById(R.id.sb_defaultPlace);
        mTvDelete = findViewById(R.id.tv_delete);
        mTvDelete.setVisibility(View.VISIBLE);
        mTvPlace = findViewById(R.id.tv_place);
        mRlPlace.setOnClickListener(this);

        Intent intent = getIntent();
        mUserAddressBean = (AddressInfoBean) intent.getSerializableExtra(AddressManagerAct.EXTRA_ADDRESS_INFO);
        mEtName.setText(mUserAddressBean.getUserAddrName());
        mEtTelePhone.setText(mUserAddressBean.getUserAddrTel());
        mEtDetailPlace.setText(mUserAddressBean.getUserAddrDetail());
        mSbDefaultPlace.setChecked(mUserAddressBean.getUserAddrDefault() == 0 ? false : true);
        mTvPlace.setText(mUserAddressBean.getUserAddrProvince() + " " + mUserAddressBean.getUserAddrCity());
        mTvDelete.setOnClickListener(this);
        //光标移到文字后
        mEtName.setSelection(mEtName.getText().length());
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    CheckUtils.checkStringIsNull(mEtName.getText().toString(), "输入姓名");
                    CheckUtils.checkPhone(mEtTelePhone.getText().toString(), "输入电话号码");
                    CheckUtils.checkStringIsNull(mTvPlace.getText().toString(), "输入所在地区");
                    CheckUtils.checkStringIsNull(mEtDetailPlace.getText().toString(), "输入详细信息");
                    mUserAddressBean.setUserAddrName(mEtName.getText().toString());
                    mUserAddressBean.setUserAddrTel(mEtTelePhone.getText().toString());
                    mUserAddressBean.setUserAddrProvince(mTvPlace.getText().toString().split(" ")[0]);
                    mUserAddressBean.setUserAddrCity(mTvPlace.getText().toString().split(" ")[1]);
                    mUserAddressBean.setUserAddrDetail(mEtDetailPlace.getText().toString());
                    mUserAddressBean.setUserAddrDefault(mSbDefaultPlace.isChecked() ? 1 : 0);
                    mUserAddressBean.setAddrId(mUserAddressBean.getAddrId());
                    getPresenter().modifyUserInfo(mUserAddressBean);
                } catch (Exception e) {
                    ToastUtils.showToast(AddressUpdateAct.this, e.getMessage());
                } finally {

                }
                return false;
            }
        });
    }


    @NonNull
    @Override
    public AddressUpdatePre createPresenter() {
        return new AddressUpdatePre(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_place:
                showPickerView();
                break;
            case R.id.tv_delete:
                getPresenter().delUserAddrById(mUserAddressBean.getAddrId());
                break;
        }
    }
    /**
     * 城市选择
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//
                mPosition1 = options1;
                mPosition2 = options2;
                //返回的分别是三个级别的选中位置
                opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                mTvPlace.setText(opt1tx + " " + opt2tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSelectOptions(mPosition1, mPosition2)//默认选中项
                .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
                .build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }


    @Override
    public void onUpdateResult(String result) {
        ToastUtils.showToast(AddressUpdateAct.this, result);
        Intent intent = new Intent();
        intent.putExtra(AddressManagerAct.EXTRA_ADDRESS_INFO, mUserAddressBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDistrictListResult(DictrictOptionBean optionBean) {
        options1Items.clear();
        options1Items.addAll(optionBean.getOptions1Items());
        this.options2Items = optionBean.getOptions2Items();
    }
}
