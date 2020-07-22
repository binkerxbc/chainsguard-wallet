package com.chainsguard.wallet.ui.create;

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
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chainsguard.safeinput.SafeInputComponent;
import com.chainsguard.wallet.R;
import com.chainsguard.wallet.data.bean.WalletInfo;
import com.chainsguard.wallet.databinding.ActivityCreateWalletBinding;
import com.chainsguard.wallet.ui.base.MVPActivity;
import com.chainsguard.wallet.ui.home.WalletActivity;
import com.chainsguard.wallet.util.AnimationUtil;
import com.chainsguard.wallet.util.RegexUtils;

import java.util.Objects;

/**
 * @author i11m20n
 * 创建或导入钱包界面
 */
public final class CreateWalletActivity extends MVPActivity<CreateWalletPresenter> implements
        CreateWalletContract.IView, TextWatcher {

    public static final int CODE_REQUEST = 102;
    public static final int CODE_RESULT = 103;
    public static final String ACTION_CREATE_WALLET = "create";
    public static final String ACTION_IMPORT_WALLET = "import";
    private static final String ACTION = "action";

    private ActivityCreateWalletBinding mViewBinding;

    public static void startActivity(Activity activity, String action) {
        Intent intent = new Intent(activity, CreateWalletActivity.class);
        intent.putExtra(ACTION, action);
        activity.startActivityForResult(intent, CODE_REQUEST);
    }

    @NonNull
    @Override
    protected CreateWalletPresenter initPresenter() {
        return new CreateWalletPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityCreateWalletBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initToolbar();
        initViews();
        initListens();
    }

    private void initToolbar() {
        mViewBinding.toolbarLayout.toolBarTv.setText(isImportWallet() ? "导入钱包" : "创建钱包");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        mViewBinding.toolbarLayout.toolBar.setNavigationIcon(backDrawable);
        mViewBinding.toolbarLayout.toolBar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        if (isImportWallet()) {
            mViewBinding.createWalletAb.setText("导入");
            mViewBinding.mnemonicTitleTv.setVisibility(View.VISIBLE);
            mViewBinding.createWalletInputMnemonicTil.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.createWalletAb.setText("创建");
            mViewBinding.mnemonicTitleTv.setVisibility(View.GONE);
            mViewBinding.createWalletInputMnemonicTil.setVisibility(View.GONE);
        }

        // 绑定安全输入组件
        SafeInputComponent mnemonicComponent = new SafeInputComponent();
        mnemonicComponent.bind(this, mViewBinding.createWalletInputMnemonicEt);
        SafeInputComponent passwordComponent = new SafeInputComponent();
        passwordComponent.bind(this, mViewBinding.createWalletInputPasswordEt);
        SafeInputComponent secondPasswordComponent = new SafeInputComponent();
        secondPasswordComponent.bind(this, mViewBinding.createWalletInputSecondPasswordEt);
    }

    private void initListens() {
        mViewBinding.createWalletAb.setOnClickListener(v -> createOrImportWallet());

        mViewBinding.createWalletInputNameEt.addTextChangedListener(this);
        mViewBinding.createWalletInputPasswordEt.addTextChangedListener(this);
        mViewBinding.createWalletInputSecondPasswordEt.addTextChangedListener(this);
        if (isImportWallet()) {
            mViewBinding.createWalletInputMnemonicEt.addTextChangedListener(this);
        }
    }

    private void createOrImportWallet() {
        String mnemonic = Objects.requireNonNull(mViewBinding.createWalletInputMnemonicEt.getText()).toString().trim();
        String walletName = Objects.requireNonNull(mViewBinding.createWalletInputNameEt.getText()).toString().trim();
        String pwd = Objects.requireNonNull(mViewBinding.createWalletInputPasswordEt.getText()).toString().trim();
        String secondPwd =
                Objects.requireNonNull(mViewBinding.createWalletInputSecondPasswordEt.getText()).toString().trim();

        if (!checkInputValid(mnemonic, walletName, pwd, secondPwd)) {
            return;
        }

        // 保存钱包名称
        mPresenter.saveWalletName(walletName);
        // 保存钱包密码
        mPresenter.saveWalletPassword(secondPwd);

        if (isImportWallet()) {
            // 导入钱包
            mPresenter.importWallet(mnemonic);
        } else {
            // 创建钱包
            mPresenter.createWallet();
        }
    }

    /**
     * 检查输入是否有效
     *
     * @return 如果返回 true 则表示输入有效
     */
    private boolean checkInputValid(String mnemonic, String walletName, String pwd, String secondPwd) {
        if (isImportWallet()) {
            if (TextUtils.isEmpty(mnemonic)) {
                mViewBinding.createWalletInputMnemonicTil.setError("助记词不能为空！");
                mViewBinding.createWalletInputMnemonicTil.startAnimation(AnimationUtil.shakeAnimation());
                return false;
            }

            String[] mnemonics = mnemonic.split(" ");
            if (mnemonics.length != 12) {
                mViewBinding.createWalletInputMnemonicTil.setError("助记词必须是 12 组单词！");
                mViewBinding.createWalletInputMnemonicTil.startAnimation(AnimationUtil.shakeAnimation());
                return false;
            }
        }

        if (TextUtils.isEmpty(walletName)) {
            mViewBinding.createWalletInputNameTil.setError("钱包名称不能为空！");
            mViewBinding.createWalletInputNameTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(pwd)) {
            mViewBinding.createWalletInputPasswordTil.setError("密码不能为空！");
            mViewBinding.createWalletInputPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (TextUtils.isEmpty(secondPwd)) {
            mViewBinding.createWalletInputSecondPasswordTil.setError("确认密码不能为空！");
            mViewBinding.createWalletInputSecondPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (pwd.length() < 10) {
            mViewBinding.createWalletInputPasswordTil.setError("密码位数不正确！");
            mViewBinding.createWalletInputPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (!RegexUtils.isMatchPassword(pwd)) {
            mViewBinding.createWalletInputPasswordTil.setError("密码必须包含大小写字母和数字！");
            mViewBinding.createWalletInputPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        if (!TextUtils.equals(pwd, secondPwd)) {
            mViewBinding.createWalletInputSecondPasswordTil.setError("两次输入密码不一致！");
            mViewBinding.createWalletInputSecondPasswordTil.startAnimation(AnimationUtil.shakeAnimation());
            return false;
        }

        return true;
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
        String mnemonic = Objects.requireNonNull(mViewBinding.createWalletInputMnemonicEt.getText()).toString().trim();
        String walletName = Objects.requireNonNull(mViewBinding.createWalletInputNameEt.getText()).toString().trim();
        String password = Objects.requireNonNull(mViewBinding.createWalletInputPasswordEt.getText()).toString().trim();
        String secondPassword =
                Objects.requireNonNull(mViewBinding.createWalletInputSecondPasswordEt.getText()).toString().trim();

        // 清除输入框错误提示
        if (!TextUtils.isEmpty(mnemonic) && mViewBinding.createWalletInputMnemonicTil.isErrorEnabled()) {
            mViewBinding.createWalletInputMnemonicTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(walletName) && mViewBinding.createWalletInputNameTil.isErrorEnabled()) {
            mViewBinding.createWalletInputNameTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(password) && mViewBinding.createWalletInputPasswordTil.isErrorEnabled()) {
            mViewBinding.createWalletInputPasswordTil.setErrorEnabled(false);
        } else if (!TextUtils.isEmpty(secondPassword) && mViewBinding.createWalletInputSecondPasswordTil.isErrorEnabled()) {
            mViewBinding.createWalletInputSecondPasswordTil.setErrorEnabled(false);
        }

        // 显示或隐藏查看密码图标
        mViewBinding.createWalletInputMnemonicTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(mnemonic));
        mViewBinding.createWalletInputPasswordTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(password));
        mViewBinding.createWalletInputSecondPasswordTil.setPasswordVisibilityToggleEnabled(!TextUtils.isEmpty(secondPassword));
    }

    /**
     * 判断是否为导入钱包
     *
     * @return 如果是则返回 true
     */
    private boolean isImportWallet() {
        String action = getIntent().getStringExtra(ACTION);
        return ACTION_IMPORT_WALLET.equals(action);
    }

    @Override
    public void onWalletCreated(@NonNull WalletInfo walletInfo) {
        WalletActivity.startActivity(this);
        setResult(CODE_RESULT);
        finish();
    }

    @Override
    public void onCreateFailed(@NonNull String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }
}