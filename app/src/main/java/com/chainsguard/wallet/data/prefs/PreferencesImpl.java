package com.chainsguard.wallet.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.chainsguard.wallet.WalletApplication;

/**
 * @author i11m20n
 */
public final class PreferencesImpl implements IPreferences {

    private static final String KEY_WALLET_NAME = "wallet_name";
    private static final String KEY_WALLET_PASSWORD = "wallet_password";
    private static final String KEY_BACKUP_MNEMONIC = "backup_mnemonic";
    private static final String KEY_PRIVATE_KEY = "private_key";
    private static final String KEY_PUBLIC_KEY = "public_key";
    private static final String KEY_ADDRESS = "address";

    private final SharedPreferences mPrefs;

    private PreferencesImpl() {
        mPrefs = WalletApplication.getApplication().getSharedPreferences("wallet", Context.MODE_PRIVATE);
    }

    private static final class SingletonHolder {
        private static final PreferencesImpl INSTANCE = new PreferencesImpl();
    }

    public static PreferencesImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void saveWalletName(@NonNull String newName) {
        mPrefs.edit().putString(KEY_WALLET_NAME, newName).apply();
    }

    @Override
    public String acquireWalletName() {
        return mPrefs.getString(KEY_WALLET_NAME, "");
    }

    @Override
    public void saveWalletPassword(@NonNull String newPassword) {
        mPrefs.edit().putString(KEY_WALLET_PASSWORD, newPassword).apply();
    }

    @Override
    public String acquireWalletPassword() {
        return mPrefs.getString(KEY_WALLET_PASSWORD, "");
    }

    @Override
    public void saveBackupMnemonic(boolean backup) {
        mPrefs.edit().putBoolean(KEY_BACKUP_MNEMONIC, backup).apply();
    }

    @Override
    public boolean isBackupMnemonic() {
        return mPrefs.getBoolean(KEY_BACKUP_MNEMONIC, false);
    }

    @Override
    public void savePrivateKey(@NonNull String privateKey) {
        mPrefs.edit().putString(KEY_PRIVATE_KEY, privateKey).apply();
    }

    @Override
    public String acquirePrivateKey() {
        return mPrefs.getString(KEY_PRIVATE_KEY, "");
    }

    @Override
    public void savePublicKey(@NonNull String publicKey) {
        mPrefs.edit().putString(KEY_PUBLIC_KEY, publicKey).apply();
    }

    @Override
    public String acquirePublicKey() {
        return mPrefs.getString(KEY_PUBLIC_KEY, "");
    }

    @Override
    public void saveAddress(@NonNull String address) {
        mPrefs.edit().putString(KEY_ADDRESS, address).apply();
    }

    @Override
    public String acquireAddress() {
        return mPrefs.getString(KEY_ADDRESS, "");
    }
}
