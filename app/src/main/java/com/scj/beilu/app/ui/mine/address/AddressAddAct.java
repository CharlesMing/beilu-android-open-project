package com.scj.beilu.app.ui.mine.address;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.mine.address.AddressAddPre;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;
import com.scj.beilu.app.mvp.mine.bean.DistrictInfoBean;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * author: SunGuiLan
 * date:2019/2/28
 * descriptin:添加地址
 */
public class AddressAddAct extends BaseMvpActivity<AddressAddPre.AddressAddView, AddressAddPre>
        implements AddressAddPre.AddressAddView, View.OnClickListener {
    private AddressInfoBean mUserAddressBean;

    private EditText mEtName;
    private EditText mEtTelePhone;
    private EditText mEtDetailPlace;
    private SwitchButton mSbDefaultPlace;
    private RelativeLayout mRlPlace;
    private Toolbar mToolbar;
    private TextView mTvPlace;
    private List<DistrictInfoBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items;
    private int mPosotion1 = 0;
    private int mPosotion2 = 0;
    private String opt1tx;
    private String opt2tx;

    @Override
    protected void initView() {
        super.initView();
        mToolbar = findViewById(R.id.toolbar);
        mEtName = findViewById(R.id.et_name);
        mRlPlace = findViewById(R.id.rl_place);
        mEtTelePhone = findViewById(R.id.et_phone);
        mEtDetailPlace = findViewById(R.id.et_detail_place);
        mSbDefaultPlace = findViewById(R.id.sb_defaultPlace);
        mTvPlace = findViewById(R.id.tv_place);
        mRlPlace.setOnClickListener(this);
        mUserAddressBean = new AddressInfoBean();
        mToolbar.setOnMenuItemClickListener(item -> {
            try {
                CheckUtils.checkStringIsNull(mEtName.getText().toString().trim(), "输入姓名");
                CheckUtils.checkPhone(mEtTelePhone.getText().toString(), "输入电话号码");
                CheckUtils.checkStringIsNull(mTvPlace.getText().toString(), "输入所在地区");
                CheckUtils.checkStringIsNull(mEtDetailPlace.getText().toString(), "输入详细信息");
                mUserAddressBean.setUserAddrName(mEtName.getText().toString());
                mUserAddressBean.setUserAddrTel(mEtTelePhone.getText().toString());
                mUserAddressBean.setUserAddrProvince(mTvPlace.getText().toString().split(" ")[0]);
                mUserAddressBean.setUserAddrCity(mTvPlace.getText().toString().split(" ")[1]);
                mUserAddressBean.setUserAddrDetail(mEtDetailPlace.getText().toString());
                mUserAddressBean.setUserAddrDefault(mSbDefaultPlace.isChecked() ? 1 : 0);
                getPresenter().addUserAddr(mUserAddressBean);
            } catch (Exception e) {
                ToastUtils.showToast(AddressAddAct.this, e.getMessage());
            } finally {

            }
            return false;
        });
    }

    /**
     * 城市选择
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {//
            mPosotion1 = options1;
            mPosotion2 = options2;
            //返回的分别是三个级别的选中位置
            opt1tx = options1Items.size() > 0 ?
                    options1Items.get(options1).getPickerViewText() : "";

            opt2tx = options2Items.size() > 0
                    && options2Items.get(options1).size() > 0 ?
                    options2Items.get(options1).get(options2) : "";
            mTvPlace.setText(opt1tx + " " + opt2tx);
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSelectOptions(mPosotion1, mPosotion2)//默认选中项
                .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
                .build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    @Override
    public int getLayout() {
        return R.layout.act_address_add;
    }

    @NonNull
    @Override
    public AddressAddPre createPresenter() {
        return new AddressAddPre(this);
    }

    @Override
    public void onAddUserAddrResult(String resultMsg) {
        ToastUtils.showToast(AddressAddAct.this, resultMsg);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_place:
                showPickerView();
                break;
        }
    }
}
