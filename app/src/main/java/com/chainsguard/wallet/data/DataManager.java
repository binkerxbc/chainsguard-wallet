package com.chainsguard.wallet.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.data.bean.TransferInfo;
import com.chainsguard.wallet.data.bean.TransferResult;
import com.chainsguard.wallet.data.bean.WalletInfo;
import com.chainsguard.wallet.data.db.AppDatabaseImpl;
import com.chainsguard.wallet.data.db.IAppDatabase;
import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.data.okchain.IOKChainClient;
import com.chainsguard.wallet.data.okchain.OKChainClientImpl;
import com.chainsguard.wallet.data.prefs.IPreferences;
import com.chainsguard.wallet.data.prefs.PreferencesImpl;

import java.util.List;


/**
 * @author i11m20n
 */
public final class DataManager {

    private IPreferences mPreferences;
    private IOKChainClient mOKChainClient;
    private IAppDatabase mAppDatabase;

    private DataManager() {
        mPreferences = PreferencesImpl.getInstance();
        mOKChainClient = OKChainClientImpl.getInstance();
        mAppDatabase = AppDatabaseImpl.getInstance();
    }

    private static final class SingletonHolder {
        private static final DataManager INSTANCE = new DataManager();
    }

    public static DataManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 保存钱包名称
     *
     * @param newName 新的名称
     */
    public void saveWalletName(@NonNull String newName) {
        mPreferences.saveWalletName(newName);
    }

    /**
     * 获取钱包名称
     */
    public String acquireWalletName() {
        return mPreferences.acquireWalletName();
    }

    /**
     * 保存钱包密码
     *
     * @param newPassword 新的密码
     */
    public void saveWalletPassword(@NonNull String newPassword) {
        mPreferences.saveWalletPassword(newPassword);
    }

    /**
     * 获取钱包密码
     */
    public String acquireWalletPassword() {
        return mPreferences.acquireWalletPassword();
    }

    /**
     * 保存物理备份助记词标记
     *
     * @param backup 已备份标记
     */
    public void saveBackupMnemonic(boolean backup) {
        mPreferences.saveBackupMnemonic(backup);
    }

    /**
     * 返回是否已物理备份助记词，默认返回 false。
     *
     * @return 如果返回 true 则表示已物理备份助记词，反之亦然。
     */
    public boolean isBackupMnemonic() {
        return mPreferences.isBackupMnemonic();
    }

    /**
     * 保存私钥
     *
     * @param privateKey 私钥
     */
    public void savePrivateKey(@NonNull String privateKey) {
        mPreferences.savePrivateKey(privateKey);
    }

    /**
     * 获取私钥
     */
    public String acquirePrivateKey() {
        return mPreferences.acquirePrivateKey();
    }

    /**
     * 保存公钥
     *
     * @param publicKey 公钥
     */
    public void savePublicKey(@NonNull String publicKey) {
        mPreferences.savePublicKey(publicKey);
    }

    /**
     * 获取公钥
     */
    public String acquirePublicKey() {
        return mPreferences.acquirePublicKey();
    }

    /**
     * 保存地址
     *
     * @param address 地址
     */
    public void saveAddress(@NonNull String address) {
        mPreferences.saveAddress(address);
    }

    /**
     * 获取地址
     */
    public String acquireAddress() {
        return mPreferences.acquireAddress();
    }

    /**
     * 创建钱包并保存，钱包信息包括私钥、公钥和地址等。
     *
     * @return 钱包信息
     */
    public WalletInfo createWallet() {
        WalletInfo walletInfo = mOKChainClient.createWallet();
        savePrivateKey(walletInfo.getPrivateKey());
        savePublicKey(walletInfo.getPublicKey());
        saveAddress(walletInfo.getAddress());
        return walletInfo;
    }

    /**
     * 根据助记词导入钱包并保存，钱包信息包含私钥、公钥和地址等。
     * <p>
     * 注意：该函数为耗时操作，需要在子线程中调用。
     * </p>
     *
     * @param mnemonic 助记词
     * @return 钱包实体
     */
    public WalletInfo importWallet(@NonNull String mnemonic) {
        WalletInfo walletInfo = mOKChainClient.importWallet(mnemonic);
        savePrivateKey(walletInfo.getPrivateKey());
        savePublicKey(walletInfo.getPublicKey());
        saveAddress(walletInfo.getAddress());
        return walletInfo;
    }

    /**
     * 发送转账交易
     * <p>
     * 注意：该函数为耗时操作，需要在子线程中调用。
     * </p>
     *
     * @param transferInfo 转账交易信息
     * @return 转账交易结果
     */
    @Nullable
    public TransferResult transfer(@NonNull TransferInfo transferInfo) {
        return mOKChainClient.transfer(acquirePrivateKey(), transferInfo);
    }

    /**
     * 查询账户余额
     * <p>
     * 注意：该函数为耗时操作，需要在子线程中调用。
     * </p>
     *
     * @param address 账户地址
     * @return 账户余额信息
     */
    public AccountBalanceInfo queryAccountBalance(@NonNull String address) {
        return mOKChainClient.queryAccountBalance(address);
    }

    /**
     * 从数据库中获取所有地址实体
     *
     * @return 地址实体列表
     */
    public List<Address> getAllAddress() {
        return mAppDatabase.getAllAddress();
    }

    /**
     * 根据指定地址查找数据库中已存在的地址实体
     *
     * @param address 待查找的地址
     * @return 返回查找到的地址实体，如果查找不到则返回 null。
     */
    public Address findAddressByAddress(String address) {
        return mAppDatabase.findAddressByAddress(address);
    }

    /**
     * 插入指定地址实体到数据库
     *
     * @param address 待插入地址实体
     * @return 如果返回 true 则插入成功，反之亦然。
     */
    public boolean insertAddress(Address address) {
        return mAppDatabase.insertAddress(address);
    }

    /**
     * 从数据库中删除指定地址实体
     *
     * @param address 待删除地址实体
     * @return 如果返回 true 则删除成功，反之亦然。
     */
    public boolean deleteAddress(Address address) {
        return mAppDatabase.deleteAddress(address);
    }
}
