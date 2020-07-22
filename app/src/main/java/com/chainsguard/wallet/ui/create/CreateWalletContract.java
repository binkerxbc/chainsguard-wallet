package com.chainsguard.wallet.ui.create;

import androidx.annotation.NonNull;

import com.chainsguard.wallet.data.bean.WalletInfo;
import com.chainsguard.wallet.ui.base.IMVPPresenter;
import com.chainsguard.wallet.ui.base.IMVPView;

/**
 * @author i11m20n
 * MVP 合约类，用以约定 M 和 V 的交互接口。
 */
final class CreateWalletContract {
    interface IView extends IMVPView {
        /**
         * 当钱包创建或导入完成时回调
         *
         * @param walletInfo 钱包信息
         */
        void onWalletCreated(@NonNull WalletInfo walletInfo);

        /**
         * 当创建或导入钱包失败时回调
         *
         * @param errorMsg 错误消息
         */
        void onCreateFailed(@NonNull String errorMsg);
    }

    interface IPresenter extends IMVPPresenter<IView> {

        /**
         * 保存钱包名称
         */
        void saveWalletName(String walletName);

        /**
         * 保存钱包密码
         */
        void saveWalletPassword(String walletPassword);

        /**
         * 创建钱包
         */
        void createWallet();

        /**
         * 导入钱包
         *
         * @param mnemonic 助记词
         */
        void importWallet(@NonNull String mnemonic);
    }
}
