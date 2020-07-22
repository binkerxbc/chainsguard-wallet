package com.chainsguard.wallet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.databinding.FragmentSettingsBinding;
import com.chainsguard.wallet.ui.AboutActivity;
import com.chainsguard.wallet.ui.LicenseActivity;
import com.chainsguard.wallet.ui.PwdResetActivity;
import com.chainsguard.wallet.ui.addrbook.AddressBookActivity;
import com.chainsguard.wallet.ui.base.BaseFragment;

public final class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding mViewBinding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolBar();
        initListens();
    }

    private void initToolBar() {
        mViewBinding.settingsToolbar.toolBarTv.setText(R.string.settings);
    }

    private void initListens() {
        mViewBinding.settingsAddressBookRl.setOnClickListener(v -> AddressBookActivity.startActivity(this));
        mViewBinding.settingsModifyPasswordRl.setOnClickListener(v -> PwdResetActivity.startActivity(mContext));
        mViewBinding.settingsCodeLicenseRl.setOnClickListener(v -> LicenseActivity.startActivity(mContext));
        mViewBinding.settingsAboutRl.setOnClickListener(v -> AboutActivity.startActivity(mContext));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding = null;
    }
}
