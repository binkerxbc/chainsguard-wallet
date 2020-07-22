package com.chainsguard.wallet.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.databinding.ActivityLicenseBinding;
import com.chainsguard.wallet.databinding.ItemLicenseBinding;
import com.chainsguard.wallet.ui.base.BaseActivity;

import java.util.ArrayList;

public final class LicenseActivity extends BaseActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LicenseActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLicenseBinding binding = ActivityLicenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.toolBarTv.setText("开源许可");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        binding.toolbarLayout.toolBar.setNavigationIcon(backDrawable);
        binding.toolbarLayout.toolBar.setNavigationOnClickListener(v -> finish());

        binding.licenseRv.setAdapter(new LicenseAdapter());
    }

    static final class LicenseAdapter extends RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder> {

        private ArrayList<License> licenses;

        private LicenseAdapter() {
            licenses = new ArrayList<>();

            License okChainJavaSdkLicense = new License();
            okChainJavaSdkLicense.name = "okchain-java-sdk";
            okChainJavaSdkLicense.desc = "okchain java sdk";
            okChainJavaSdkLicense.link = "https://github.com/okex/okchain-java-sdk";
            licenses.add(okChainJavaSdkLicense);

            License rxJavaLicense = new License();
            rxJavaLicense.name = "RxJava";
            rxJavaLicense.desc = "RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and" +
                    " event-based programs using observable sequences for the Java VM.";
            rxJavaLicense.link = "https://github.com/ReactiveX/RxJava";
            licenses.add(rxJavaLicense);

            License rxAndroidLicense = new License();
            rxAndroidLicense.name = "RxAndroid";
            rxAndroidLicense.desc = "RxJava bindings for Android";
            rxAndroidLicense.link = "https://github.com/ReactiveX/RxAndroid";
            licenses.add(rxAndroidLicense);

            License gsonLicense = new License();
            gsonLicense.name = "gson";
            gsonLicense.desc = "A Java serialization/deserialization library to convert Java Objects into JSON and " +
                    "back";
            gsonLicense.link = "https://github.com/google/gson";
            licenses.add(gsonLicense);

            License fastjsonLicense = new License();
            fastjsonLicense.name = "fastjson";
            fastjsonLicense.desc = "A fast JSON parser/generator for Java.";
            fastjsonLicense.link = "https://github.com/alibaba/fastjson";
            licenses.add(fastjsonLicense);

            License web3jLicense = new License();
            web3jLicense.name = "web3j";
            web3jLicense.desc = "Lightweight Java and Android library for integration with Ethereum clients";
            web3jLicense.link = "https://github.com/web3j/web3j";
            licenses.add(web3jLicense);

            License bitcoinjLicense = new License();
            bitcoinjLicense.name = "bitcoinj";
            bitcoinjLicense.desc = "A library for working with Bitcoin";
            bitcoinjLicense.link = "https://github.com/bitcoinj/bitcoinj";
            licenses.add(bitcoinjLicense);

            License qrCodeLicense = new License();
            qrCodeLicense.name = "QrCodeLib";
            qrCodeLicense.desc = "A Android library for qrcode scanning and generating, depends on zxing library";
            qrCodeLicense.link = "https://github.com/ahuyangdong/QrCodeLib";
            licenses.add(qrCodeLicense);

            License zxingLicense = new License();
            zxingLicense.name = "zxing";
            zxingLicense.desc = "ZXing (\"Zebra Crossing\") barcode scanning library for Java, Android";
            zxingLicense.link = "https://github.com/zxing/zxing";
            licenses.add(zxingLicense);
        }

        @NonNull
        @Override
        public LicenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemLicenseBinding binding = ItemLicenseBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                    false);
            LicenseViewHolder licenseViewHolder = new LicenseViewHolder(binding);
            binding.getRoot().setOnClickListener(v -> {
                License license = licenses.get(licenseViewHolder.getAdapterPosition());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(license.link));
                v.getContext().startActivity(intent);
            });

            return licenseViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull LicenseViewHolder holder, int position) {
            License license = licenses.get(position);
            holder.licenseBinding.itemLicenseName.setText(license.name);
            holder.licenseBinding.itemLicenseDesc.setText(license.desc);
        }

        @Override
        public int getItemCount() {
            return licenses.size();
        }

        static final class LicenseViewHolder extends RecyclerView.ViewHolder {

            ItemLicenseBinding licenseBinding;

            public LicenseViewHolder(ItemLicenseBinding licenseBinding) {
                super(licenseBinding.getRoot());
                this.licenseBinding = licenseBinding;
            }
        }
    }

    static final class License {
        public String name;
        public String desc;
        public String link;
    }
}