package com.chainsguard.wallet.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.chainsguard.wallet.databinding.ActivityWalletBinding;
import com.chainsguard.wallet.ui.base.BaseActivity;

/**
 * @author i11m20n
 */
public final class WalletActivity extends BaseActivity {

    private ActivityWalletBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initViews();
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, WalletActivity.class);
        activity.startActivity(intent);
    }

    private void initViews() {
        WalletPagerAdapter walletPagerAdapter = new WalletPagerAdapter(getSupportFragmentManager());
        mViewBinding.activityWalletViewPager.setAdapter(walletPagerAdapter);
        mViewBinding.activityWalletViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                MenuItem item = mViewBinding.activityWalletBnv.getMenu().getItem(position);
                item.setChecked(true);
            }
        });

        mViewBinding.activityWalletBnv.setOnNavigationItemSelectedListener(item -> {
            mViewBinding.activityWalletViewPager.setCurrentItem(item.getOrder());
            return true;
        });
    }
}
