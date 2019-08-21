package com.scj.beilu.app.ui.mine.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.address.AddressManagerPre;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;
import com.scj.beilu.app.ui.mine.adapter.AddressManagerAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/18 15:48
 * 地址列表
 */
public class AddressManagerAct extends BaseMvpActivity<AddressManagerPre.AddressManagerView,
        AddressManagerPre> implements AddressManagerPre.AddressManagerView, OnItemClickListener {
    private List<AddressInfoBean> mAddressList;
    private RecyclerView mRvAddressList;
    private AddressManagerAdapter mAddressManagerAdapter;
    private LinearLayout mLlEmpty;
    private Toolbar mToolbar;
    public static final int UPDATE_REQUEST_CODE = 0x001;
    public static final int ADD_REQUEST_CODE = 0x002;
    public static final String EXTRA_ADDRESS_INFO = "address_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.act_address_manager;
    }

    @Override
    public void initView() {
        super.initView();
        mToolbar = findViewById(R.id.toolbar);
        mLlEmpty = findViewById(R.id.il_empty);
        mRvAddressList = findViewById(R.id.rv_address_list);
        mRvAddressList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAddressManagerAdapter = new AddressManagerAdapter();
        mRvAddressList.setAdapter(mAddressManagerAdapter);
        getPresenter().getAllAddress();

        mToolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(AddressManagerAct.this, AddressAddAct.class);
            startActivityForResult(intent, ADD_REQUEST_CODE);
            return false;
        });
        mAddressManagerAdapter.setOnItemClickListener(this);
    }

    @NonNull
    @Override
    public AddressManagerPre createPresenter() {
        return new AddressManagerPre(this);
    }


    @Override
    public void onAllAddressInfoResult(List<AddressInfoBean> addressList) {
        mAddressManagerAdapter.setAddressList(addressList);
        mAddressList = addressList;
        //列表有无数据
        if (mAddressList.size() == 0) {
            mRvAddressList.setVisibility(View.GONE);
            mLlEmpty.setVisibility(View.VISIBLE);
        } else {
            mRvAddressList.setVisibility(View.VISIBLE);
            mLlEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        AddressInfoBean userAddressBean = mAddressList.get(position);
        switch (view.getId()) {
            case R.id.tv_edit:
                Intent intent = new Intent(AddressManagerAct.this, AddressUpdateAct.class);
                intent.putExtra(EXTRA_ADDRESS_INFO, userAddressBean);
                startActivityForResult(intent, UPDATE_REQUEST_CODE);
                break;
            case R.id.address_item_content:
                Intent data = getIntent();
                if (data == null) return;
                if (data.getStringExtra("address") == null) return;
                data.putExtra(EXTRA_ADDRESS_INFO, userAddressBean);
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == UPDATE_REQUEST_CODE) && (data != null)) {
            getPresenter().getAllAddress();
        } else if ((resultCode == RESULT_OK) && (requestCode == ADD_REQUEST_CODE) && (data != null)) {
            getPresenter().getAllAddress();
        }
    }
}
