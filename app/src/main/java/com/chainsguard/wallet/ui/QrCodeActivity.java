package com.chainsguard.wallet.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.data.DataManager;
import com.chainsguard.wallet.databinding.ActivityQrCodeBinding;
import com.chainsguard.wallet.ui.base.BaseActivity;
import com.chainsguard.wallet.util.PixelUtils;
import com.google.zxing.util.QrCodeGenerator;

public final class QrCodeActivity extends BaseActivity {

    private ActivityQrCodeBinding mViewBinding;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, QrCodeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityQrCodeBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initToolbar();
        initViews();
        initListens();
    }

    private void initToolbar() {
        mViewBinding.toolbarLayout.toolBarTv.setText("收款");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        mViewBinding.toolbarLayout.toolBar.setNavigationIcon(backDrawable);
    }

    private void initViews() {
        DataManager dataManager = DataManager.getInstance();
        String address = dataManager.acquireAddress();
        Bitmap bitmap = QrCodeGenerator.getQrCodeImage(address, PixelUtils.dp2px(200), PixelUtils.dp2px(200));
        mViewBinding.qrCodeIv.setImageBitmap(bitmap);

        mViewBinding.qrCodeAddressTv.setText(address);
        mViewBinding.qrCodeNameTv.setText(dataManager.acquireWalletName());
    }

    private void initListens() {
        mViewBinding.toolbarLayout.toolBar.setNavigationOnClickListener(v -> finish());
        mViewBinding.qrCodeAddressTv.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("OKChain Address", mViewBinding.qrCodeAddressTv.getText());
            assert cm != null;
            cm.setPrimaryClip(clip);
            Toast.makeText(this, "已复制地址到剪贴板", Toast.LENGTH_SHORT).show();
        });
        mViewBinding.qrCodeShareBt.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "收款地址");
            intent.putExtra(Intent.EXTRA_TEXT, mViewBinding.qrCodeAddressTv.getText());
            startActivity(Intent.createChooser(intent, "链安 Wallet"));
        });
    }
}