package com.chainsguard.wallet.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.chainsguard.wallet.BuildConfig;
import com.chainsguard.wallet.R;
import com.chainsguard.wallet.databinding.ActivityAboutBinding;
import com.chainsguard.wallet.ui.base.BaseActivity;

public final class AboutActivity extends BaseActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.toolBarTv.setText("关于我们");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        binding.toolbarLayout.toolBar.setNavigationIcon(backDrawable);
        binding.toolbarLayout.toolBar.setNavigationOnClickListener(v -> finish());

        binding.appVersion.setText(BuildConfig.VERSION_NAME);
    }
}