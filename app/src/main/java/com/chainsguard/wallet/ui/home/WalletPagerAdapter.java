package com.chainsguard.wallet.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author i11m20n
 */
final class WalletPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentPagerAdapters;

    public WalletPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentPagerAdapters = new ArrayList<>();
        fragmentPagerAdapters.add(WalletFragment.newInstance());
        fragmentPagerAdapters.add(SettingsFragment.newInstance());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentPagerAdapters.get(position);
    }

    @Override
    public int getCount() {
        return fragmentPagerAdapters.size();
    }
}
