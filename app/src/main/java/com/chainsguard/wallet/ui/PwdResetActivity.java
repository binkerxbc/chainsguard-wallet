package com.chainsguard.wallet.ui;

import android.content.Context;
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

import com.chainsguard.safeinput.SafeInputComponent;
import com.chainsguard.wallet.R;
import com.chainsguard.wallet.data.DataManager;
import com.chainsguard.wallet.databinding.ActivityPwdResetBinding;
import com.chainsguard.wallet.ui.base.BaseActivity;
import com.chainsguard.wallet.util.AnimationUtil;
import com.chainsguard.wallet.util.RegexUtils;

import java.util.Objects;

public class PwdResetActivity extends BaseActivity implements TextWatcher {

    private ActivityPwdResetBinding mViewBinding;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PwdResetActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityPwdResetBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initToolbar();
        initViews();
        initListens();
    }

    private void initToolbar() {
        mViewBinding.resetToolbar.toolBarTv.setText("修改钱包密码");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        mViewBinding.resetToolbar.toolBar.setNavigationIcon(backDrawable);
        mViewBinding.resetToolbar.toolBar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        SafeInputComponent passwordComponent = new SafeInputComponent();
        passwordComponent.bind(this, mViewBinding.resetCurrentPasswordEt);
        SafeInputComponent newPasswordComponent = new SafeInputComponent();
        newPasswordComponent.bind(this, mViewBinding.resetNewPasswordEt);
        SafeInputComponent secondNewPasswordComponent = new SafeInputComponent();
        secondNewPasswordComponent.bind(this, mViewBinding.resetSecondNewPasswordEt);
    }

    private void initListens() {
        mViewBinding.resetCurrentPasswordEt.addTextChangedListener(this);
        mViewBinding.resetNewPasswordEt.addTextChangedListener(this);
        mViewBinding.resetSecondNewPasswordEt.addTextChangedListener(this);
        mViewBinding.resetConfirmBt.setOnClickListener(v -> {
            String currentPwd = Objects.requireNonNull(mViewBinding.resetCurrentPasswordEt.getText()).toString().trim();
            String newPwd = Objects.requireNonNull(mViewBinding.resetNewPasswordEt.getText()).toString().trim();
            String secondNewPwd =
                    Objects.requireNonNull(mViewBinding.resetSecondNewPasswordEt.getText()).toString().trim();

            if (!checkInputValid(currentPwd, newPwd, secondNewPwd)) {
                return;
            }

            // 重置密码
            DataManager dataManager = DataManager.getInstance();
            dataManager.saveWalletPassword(secondNewPwd);
            Toast.makeText(this, "密码重置成功！", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private boolean checkInputValid(String currentPwd, String newPwd, String secondNewPwd) {
        if (TextUtils.isEmpty(currentPwd)) {
            mViewBinding.resetCurrentPasswordTil.setError("必须输入当前钱包密码！");
            mViewBinding.resetCurrentPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(newPwd)) {
            mViewBinding.resetNewPasswordTil.setError("请输入新的钱包密码！");
            mViewBinding.resetNewPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(secondNewPwd)) {
            mViewBinding.resetSecondNewPasswordTil.setError("请再次输入新钱包密码！");
            mViewBinding.resetSecondNewPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        DataManager dataManager = DataManager.getInstance();
        if (!TextUtils.equals(currentPwd, dataManager.acquireWalletPassword())) {
            mViewBinding.resetCurrentPasswordTil.setError("当前密码不正确！");
            mViewBinding.resetCurrentPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (newPwd.length() < 10) {
            mViewBinding.resetNewPasswordTil.setError("新密码位数不正确！");
            mViewBinding.resetNewPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (!RegexUtils.isMatchPassword(newPwd)) {
            mViewBinding.resetNewPasswordTil.setError("新密码必须包含大小写字母和数字！");
            mViewBinding.resetNewPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (!TextUtils.equals(newPwd, secondNewPwd)) {
            mViewBinding.resetSecondNewPasswordTil.setError("两次输入密码不一致！");
            mViewBinding.resetSecondNewPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String currentPwd = Objects.requireNonNull(mViewBinding.resetCurrentPasswordEt.getText()).toString().trim();
        String newPwd = Objects.requireNonNull(mViewBinding.resetNewPasswordEt.getText()).toString().trim();
        String secondNewPwd =
                Objects.requireNonNull(mViewBinding.resetSecondNewPasswordEt.getText()).toString().trim();

        // 清除输入框错误提示
        if (!TextUtils.isEmpty(currentPwd) && mViewBinding.resetCurrentPasswordTil.isErrorEnabled()) {
            mViewBinding.resetCurrentPasswordTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(newPwd) && mViewBinding.resetNewPasswordTil.isErrorEnabled()) {
            mViewBinding.resetNewPasswordTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(secondNewPwd) && mViewBinding.resetSecondNewPasswordTil.isErrorEnabled()) {
            mViewBinding.resetSecondNewPasswordTil.setErrorEnabled(false);
        }

        // 显示或隐藏查看密码图标
        mViewBinding.resetCurrentPasswordTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(currentPwd));
        mViewBinding.resetNewPasswordTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(newPwd));
        mViewBinding.resetSecondNewPasswordTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(secondNewPwd));
    }
}