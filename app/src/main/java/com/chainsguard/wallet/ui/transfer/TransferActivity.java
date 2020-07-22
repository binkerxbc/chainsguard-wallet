package com.chainsguard.wallet.ui.transfer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.chainsguard.safeinput.SafeInputComponent;
import com.chainsguard.safeinput.SafeKeyboardComponent;
import com.chainsguard.wallet.R;
import com.chainsguard.wallet.data.bean.TransferInfo;
import com.chainsguard.wallet.data.bean.TransferResult;
import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.databinding.ActivityTransferBinding;
import com.chainsguard.wallet.databinding.DialogConfirmPasswordBinding;
import com.chainsguard.wallet.ui.addrbook.AddressBookActivity;
import com.chainsguard.wallet.ui.base.MVPActivity;
import com.chainsguard.wallet.util.AnimationUtil;
import com.okchain.common.ConstantIF;
import com.okchain.common.StrUtils;
import com.okchain.crypto.Crypto;

import java.util.Objects;

public final class TransferActivity extends MVPActivity<TransferPresenter> implements TransferContract.IView,
        TextWatcher {

    public static final int TRANSFER_REQUEST_CODE = 101;
    public static final int TRANSFER_RESULT_CODE = 102;
    private static final String EXTRA = "address";
    private ActivityTransferBinding mViewBinding;

    public static void startActivityForResult(Fragment fragment) {
        Intent intent = new Intent(fragment.getContext(), TransferActivity.class);
        fragment.startActivityForResult(intent, TRANSFER_REQUEST_CODE);
    }

    public static void startActivityForResult(Fragment fragment, String address) {
        Intent intent = new Intent(fragment.getContext(), TransferActivity.class);
        intent.putExtra(EXTRA, address);
        fragment.startActivityForResult(intent, TRANSFER_REQUEST_CODE);
    }

    @NonNull
    @Override
    protected TransferPresenter initPresenter() {
        return new TransferPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityTransferBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initViews();
        initListens();
    }

    private void initViews() {
        mViewBinding.toolbarLayout.toolBarTv.setText("转账");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        mViewBinding.toolbarLayout.toolBar.setNavigationIcon(backDrawable);

        // 填充付款地址
        mViewBinding.transferPayAddressTv.setText(mPresenter.acquireWalletAddress());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA)) {
            // 填充收款地址
            mViewBinding.transferAddressEt.setText(intent.getStringExtra(EXTRA));
        }

        // 绑定安全键盘组件
        SafeInputComponent addressComponent = new SafeInputComponent();
        addressComponent.bind(this, mViewBinding.transferAddressEt);
        SafeInputComponent amountComponent = new SafeInputComponent();
        amountComponent.bind(this, mViewBinding.transferAmountEt);
        amountComponent.getKeyboardComponent().setDefaultKeyboardMode(SafeKeyboardComponent.MODE_NUMBER);
        SafeInputComponent symbolComponent = new SafeInputComponent();
        symbolComponent.bind(this, mViewBinding.transferTokenSymbolEt);
    }

    private void initListens() {
        mViewBinding.toolbarLayout.toolBar.setNavigationOnClickListener(v -> finish());
        // 跳转地址本
        mViewBinding.transferAddressTil.setEndIconOnClickListener(v -> AddressBookActivity.startActivity(this));

        mViewBinding.transferAddressEt.addTextChangedListener(this);
        mViewBinding.transferAmountEt.addTextChangedListener(this);
        mViewBinding.transferMemorandumEt.addTextChangedListener(this);
        mViewBinding.transferConfirmBt.setOnClickListener(v -> {
            String toAddress = Objects.requireNonNull(mViewBinding.transferAddressEt.getText()).toString().trim();
            String amount = Objects.requireNonNull(mViewBinding.transferAmountEt.getText()).toString().trim();
            String memorandum = Objects.requireNonNull(mViewBinding.transferMemorandumEt.getText()).toString().trim();
            String tokenSymbol = Objects.requireNonNull(mViewBinding.transferTokenSymbolEt.getText()).toString().trim();

            if (!checkInputValid(toAddress, amount, tokenSymbol)) {
                return;
            }

            // 输入密码确认身份
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
            DialogConfirmPasswordBinding dialogViewBinding = DialogConfirmPasswordBinding.inflate(layoutInflater);
            AlertDialog dialog = builder.setView(dialogViewBinding.getRoot())
                    .setCancelable(false)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", null)
                    .create();
            dialog.show();
            // 注意在某些机型上可能出现内存泄露问题
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(positiveBt -> {
                String password = mPresenter.acquireWalletPassword();
                String inputPassword =
                        Objects.requireNonNull(dialogViewBinding.confirmPasswordEt.getText()).toString().trim();
                if (!TextUtils.equals(password, inputPassword)) {
                    dialogViewBinding.confirmPasswordTil.setError("密码输入错误！");
                    dialogViewBinding.confirmPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
                    return;
                }

                TransferInfo transferInfo = new TransferInfo();
                transferInfo.setToAddress(toAddress);
                transferInfo.setSymbol(tokenSymbol);
                transferInfo.setAmount(amount);
                transferInfo.setMemorandum(memorandum);
                mPresenter.transfer(transferInfo);

                // 显示进度条
                mViewBinding.transferPb.setVisibility(View.VISIBLE);
                // 销毁对话框
                dialog.dismiss();
            });

            dialogViewBinding.confirmPasswordEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String password =
                            Objects.requireNonNull(dialogViewBinding.confirmPasswordEt.getText()).toString().trim();
                    if (!TextUtils.isEmpty(password) && dialogViewBinding.confirmPasswordTil.isErrorEnabled()) {
                        dialogViewBinding.confirmPasswordTil.setErrorEnabled(false);
                    }

                    dialogViewBinding.confirmPasswordTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(password));
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddressBookActivity.REQUEST_CODE && resultCode == AddressBookActivity.RESULT_CODE && data != null) {
            Address address = data.getParcelableExtra(AddressBookActivity.EXTRA_DATA);
            if (address != null) {
                // 回填收款地址信息
                mViewBinding.transferAddressEt.setText(address.getAddress());
                mViewBinding.transferTokenSymbolEt.setText(address.getTokenSymbol());
            }
        }
    }

    /**
     * 检查输入是否有效
     *
     * @param toAddress   收款地址
     * @param amount      转账金额
     * @param tokenSymbol 转账代币符号
     * @return 如果返回 true 说明输入有效，反之亦然。
     */
    private boolean checkInputValid(String toAddress, String amount, String tokenSymbol) {
        if (TextUtils.isEmpty(toAddress)) {
            mViewBinding.transferAddressTil.setError("收款地址不能为空！");
            mViewBinding.transferAddressTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(amount)) {
            mViewBinding.transferAmountTil.setError("转账金额不能为空！");
            mViewBinding.transferAmountTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(tokenSymbol)) {
            mViewBinding.transferTokenSymbolTil.setError("代币符号不能为空！");
            mViewBinding.transferTokenSymbolTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        try {
            Crypto.validateAddress(toAddress);
        } catch (Exception e) {
            mViewBinding.transferAddressTil.setError("请检查地址是否有效！");
            mViewBinding.transferAddressTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (!StrUtils.isDecimal(amount, ConstantIF.DECIMAL_N)) {
            mViewBinding.transferAmountTil.setError("转账金额必须有 8 位小数点！");
            mViewBinding.transferAmountTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        // 注意：代币符号是区分大小写的，否则转账会失败。
        if (!"tokt".equals(tokenSymbol) && !"tokb".equals(tokenSymbol) &&
                !"tusdk".equals(tokenSymbol) && !"tbtc".equals(tokenSymbol)) {
            mViewBinding.transferTokenSymbolTil.setError("目前不支持该代币！");
            mViewBinding.transferTokenSymbolTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        // 目前先不对转账备注做校验，虽然这可能是一个对链上进行数据污染或攻击的入口。

        return true;
    }

    @Override
    public void transferCompleted(TransferResult transferResult) {
        mViewBinding.transferPb.setVisibility(View.GONE);
        setResult(TRANSFER_RESULT_CODE);
        finish();
    }

    @Override
    public void transferFailed(String errorMsg) {
        mViewBinding.transferPb.setVisibility(View.GONE);
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
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
        String toAddress = Objects.requireNonNull(mViewBinding.transferAddressEt.getText()).toString().trim();
        String amount = Objects.requireNonNull(mViewBinding.transferAmountEt.getText()).toString().trim();
        String tokenSymbol = Objects.requireNonNull(mViewBinding.transferTokenSymbolEt.getText()).toString().trim();
        String memorandum = Objects.requireNonNull(mViewBinding.transferMemorandumEt.getText()).toString().trim();

        if (!TextUtils.isEmpty(toAddress) && mViewBinding.transferAddressTil.isErrorEnabled()) {
            mViewBinding.transferAddressTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(amount) && mViewBinding.transferAmountTil.isErrorEnabled()) {
            mViewBinding.transferAmountTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(tokenSymbol) && mViewBinding.transferTokenSymbolTil.isErrorEnabled()) {
            mViewBinding.transferTokenSymbolTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(memorandum) && mViewBinding.transferMemorandumTil.isErrorEnabled()) {
            mViewBinding.transferMemorandumTil.setErrorEnabled(false);
        }
    }
}