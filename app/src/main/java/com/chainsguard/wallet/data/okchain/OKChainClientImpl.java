package com.chainsguard.wallet.data.okchain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.chainsguard.wallet.BuildConfig;
import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.data.bean.TransferInfo;
import com.chainsguard.wallet.data.bean.TransferResult;
import com.chainsguard.wallet.data.bean.WalletInfo;
import com.google.gson.Gson;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.common.ConstantIF;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.AccountInfo;
import com.okchain.types.BaseModel;
import com.okchain.types.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author i11m20n
 */
public final class OKChainClientImpl implements IOKChainClient {

    // OKChain RPC 客户端
    private OKChainRPCClientImpl mOKChainRPCClientImpl;
    private Gson gson;

    private OKChainClientImpl() {
        gson = new Gson();
        // See: https://okchain-docs.readthedocs.io/en/latest/api/sdk/java-sdk.html
        mOKChainRPCClientImpl = OKChainRPCClientImpl.getOKChainClient(BuildConfig.RPC_URL);
    }

    private static final class SingletonHolder {
        private static final OKChainClientImpl INSTANCE = new OKChainClientImpl();
    }

    public static OKChainClientImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public WalletInfo createWallet() {
        AccountInfo account = mOKChainRPCClientImpl.createAccount();
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setPrivateKey(account.getPrivateKey());
        walletInfo.setPublicKey(account.getPublicKey());
        walletInfo.setAddress(account.getUserAddress());
        return walletInfo;
    }

    @Override
    public WalletInfo importWallet(@NonNull String mnemonic) {
        AccountInfo account = mOKChainRPCClientImpl.getAccountInfoFromMnemonic(mnemonic);
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setPrivateKey(account.getPrivateKey());
        walletInfo.setPublicKey(account.getPublicKey());
        walletInfo.setAddress(account.getUserAddress());
        return walletInfo;
    }

    @Override
    @Nullable
    public TransferResult transfer(@NonNull String privateKey, @NonNull TransferInfo transferInfo) {
        BuildTransaction.setMode(ConstantIF.TX_SEND_MODE_BLOCK);

        AccountInfo accountInfo = mOKChainRPCClientImpl.getAccountInfo(privateKey);
        String toAddress = transferInfo.getToAddress();
        String memorandum = transferInfo.getMemorandum();

        List<Token> tokens = new ArrayList<>();
        Token token = new Token(transferInfo.getAmount(), transferInfo.getSymbol());
        tokens.add(token);

        try {
            JSONObject result = mOKChainRPCClientImpl.sendSendTransaction(accountInfo, toAddress, tokens, memorandum);
            return gson.fromJson(result.toJSONString(), TransferResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AccountBalanceInfo queryAccountBalance(@NonNull String address) {
        // partial: 返回部分币种，包括：tbtc、tokb、tokt 和 tusdk 等。
        BaseModel accountALLTokens = mOKChainRPCClientImpl.getAccountALLTokens(address, "partial");
        return gson.fromJson(accountALLTokens.getData(), AccountBalanceInfo.class);
    }
}
