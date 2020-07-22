package com.chainsguard.wallet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chainsguard.wallet.databinding.ActivityGuideBinding;
import com.chainsguard.wallet.ui.base.BaseActivity;
import com.chainsguard.wallet.ui.create.CreateWalletActivity;

/**
 * @author i11m20n
 * 创建钱包导航界面
 */
public final class GuideActivity extends BaseActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, GuideActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGuideBinding binding = ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rlCreateWallet.setOnClickListener(v -> CreateWalletActivity.startActivity(this,
                CreateWalletActivity.ACTION_CREATE_WALLET));
        binding.rlImportWallet.setOnClickListener(v -> CreateWalletActivity.startActivity(this,
                CreateWalletActivity.ACTION_IMPORT_WALLET));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CreateWalletActivity.CODE_REQUEST && resultCode == CreateWalletActivity.CODE_RESULT) {
            // 钱包创建或导入完成
            finish();
        }
    }
}
