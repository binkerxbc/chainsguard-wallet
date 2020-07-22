package com.chainsguard.wallet.ui;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chainsguard.wallet.data.DataManager;
import com.chainsguard.wallet.ui.base.BaseActivity;
import com.chainsguard.wallet.ui.home.WalletActivity;


/**
 * @author i11m20n
 */
public final class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 将 Window 的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);

        DataManager dataManager = DataManager.getInstance();
        String privateKey = dataManager.acquirePrivateKey();
        String publicKey = dataManager.acquirePublicKey();
        String address = dataManager.acquireAddress();
        if (TextUtils.isEmpty(privateKey) || TextUtils.isEmpty(publicKey) || TextUtils.isEmpty(address)) {
            // 跳转创建钱包界面
            GuideActivity.startActivity(this);
        } else {
            // 跳转首页
            WalletActivity.startActivity(this);
        }

        finish();
    }
}
