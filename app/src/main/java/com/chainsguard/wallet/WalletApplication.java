package com.chainsguard.wallet;

import android.app.Application;

/**
 * @author i11m20n
 */
public class WalletApplication extends Application {

    private static WalletApplication walletApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        walletApplication = this;
    }

    public static WalletApplication getApplication() {
        return walletApplication;
    }
}
