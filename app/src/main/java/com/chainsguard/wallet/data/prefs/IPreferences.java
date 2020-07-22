package com.chainsguard.wallet.data.prefs;

import androidx.annotation.NonNull;

/**
 * @author i11m20n
 * 注意：采用 SharedPreferences 保存敏感信息是出于时间考虑，如果后续有机会迭代，则改用加密数据库保存。
 * 用户输入信息未做过滤，这是不安全的做法。
 */
public interface IPreferences {

    /**
     * 保存钱包名称
     *
     * @param newName 新名称
     */
    void saveWalletName(@NonNull String newName);

    /**
     * 获取钱包名称
     */
    String acquireWalletName();

    /**
     * 保存钱包密码
     *
     * @param newPassword 新密码
     */
    void saveWalletPassword(@NonNull String newPassword);

    /**
     * 获取钱包密码
     */
    String acquireWalletPassword();

    /**
     * 保存物理备份助记词标记
     *
     * @param backup 已备份标记
     */
    void saveBackupMnemonic(boolean backup);

    /**
     * 返回是否已物理备份助记词，默认返回 false。
     *
     * @return 如果返回 true 则表示已物理备份助记词，反之亦然。
     */
    boolean isBackupMnemonic();

    /**
     * 保存私钥
     *
     * @param privateKey 私钥
     */
    void savePrivateKey(@NonNull String privateKey);

    /**
     * 获取私钥
     */
    String acquirePrivateKey();

    /**
     * 保存公钥
     *
     * @param publicKey 公钥
     */
    void savePublicKey(@NonNull String publicKey);

    /**
     * 获取公钥
     */
    String acquirePublicKey();

    /**
     * 保存地址
     *
     * @param address 地址
     */
    void saveAddress(@NonNull String address);

    /**
     * 获取地址
     */
    String acquireAddress();
}
