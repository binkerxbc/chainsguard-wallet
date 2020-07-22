package com.chainsguard.wallet.data.okchain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.data.bean.TransferInfo;
import com.chainsguard.wallet.data.bean.TransferResult;
import com.chainsguard.wallet.data.bean.WalletInfo;

/**
 * @author i11m20n
 */
public interface IOKChainClient {

    /**
     * 创建钱包，钱包信息包含私钥、公钥和地址等。
     *
     * @return 钱包实体
     */
    WalletInfo createWallet();

    /**
     * 根据助记词导入钱包，钱包信息包含私钥、公钥和地址等。
     *
     * @param mnemonic 助记词
     * @return 钱包实体
     */
    WalletInfo importWallet(@NonNull String mnemonic);

    /**
     * 发送转账交易
     *
     * @param privateKey   私钥
     * @param transferInfo 转账交易信息
     * @return 转账交易结果
     */
    @Nullable
    TransferResult transfer(@NonNull String privateKey, @NonNull TransferInfo transferInfo);

    /**
     * 查询账户余额
     *
     * @param address 账户地址
     * @return 账户余额信息
     */
    AccountBalanceInfo queryAccountBalance(@NonNull String address);
}
