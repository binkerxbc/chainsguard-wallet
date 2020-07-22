package com.chainsguard.wallet.ui.addrbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.databinding.ActivityAddAddressBinding;
import com.chainsguard.wallet.ui.base.MVPActivity;
import com.chainsguard.wallet.util.AnimationUtil;
import com.okchain.crypto.Crypto;

import java.util.List;
import java.util.Objects;

public class AddAddressActivity extends MVPActivity<AddressBookPresenter> implements AddressBookContract.IView,
        TextWatcher {

    static final int REQUEST_CODE = 11;
    static final int RESULT_CODE = 12;

    private ActivityAddAddressBinding mViewBinding;

    static void startActivity(Activity activity) {
        activity.startActivityForResult(new Intent(activity, AddAddressActivity.class), REQUEST_CODE);
    }

    @NonNull
    @Override
    protected AddressBookPresenter initPresenter() {
        return new AddressBookPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());
        initToolbar();
        initListens();
    }

    private void initToolbar() {
        mViewBinding.toolbarLayout.toolBarTv.setText("添加地址");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        mViewBinding.toolbarLayout.toolBar.setNavigationIcon(backDrawable);
        mViewBinding.toolbarLayout.toolBar.setNavigationOnClickListener(v -> finish());
    }

    private void initListens() {
        mViewBinding.addAddressAddressEt.addTextChangedListener(this);
        mViewBinding.addAddressNameEt.addTextChangedListener(this);
        mViewBinding.addAddressSaveBt.setOnClickListener(v -> {
            String address = Objects.requireNonNull(mViewBinding.addAddressAddressEt.getText()).toString().trim();
            String name = Objects.requireNonNull(mViewBinding.addAddressNameEt.getText()).toString().trim();
            String desc = Objects.requireNonNull(mViewBinding.addAddressDescEt.getText()).toString().trim();

            if (!checkInputValid(address, name, desc)) {
                return;
            }

            Address addr = new Address();
            addr.setAddress(address);
            addr.setName(name);
            addr.setDesc(desc);
            addr.setTokenSymbol("");// 目前 OKChain 的地址支持所有符号
            mPresenter.saveAddress(addr);
        });
    }

    private boolean checkInputValid(String address, String name, String desc) {
        if (TextUtils.isEmpty(address)) {
            mViewBinding.addAddressAddressTil.setError("常用地址不能为空！");
            mViewBinding.addAddressAddressTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        try {
            Crypto.validateAddress(address);
        } catch (Exception e) {
            mViewBinding.addAddressAddressTil.setError("请检查地址是否有效！");
            mViewBinding.addAddressAddressTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            mViewBinding.addAddressNameTil.setError("备注名称不能为空！");
            mViewBinding.addAddressNameTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        return true;
    }

    @Override
    public void allAddress(List<Address> addresses) {
        // do nothing
    }

    @Override
    public void saveAddressCallback(boolean isSucceeded) {
        if (isSucceeded) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CODE);
            finish();
        } else {
            Toast.makeText(this, "该地址已存在", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        String address = Objects.requireNonNull(mViewBinding.addAddressAddressEt.getText()).toString().trim();
        String name = Objects.requireNonNull(mViewBinding.addAddressNameEt.getText()).toString().trim();

        if (!TextUtils.isEmpty(address) && mViewBinding.addAddressAddressTil.isErrorEnabled()) {
            mViewBinding.addAddressAddressTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(name) && mViewBinding.addAddressNameTil.isErrorEnabled()) {
            mViewBinding.addAddressNameTil.setErrorEnabled(false);
        }
    }
}