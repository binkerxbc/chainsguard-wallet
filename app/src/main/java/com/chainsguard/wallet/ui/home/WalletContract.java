package com.chainsguard.wallet.ui.home;

import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.ui.base.IMVPPresenter;
import com.chainsguard.wallet.ui.base.IMVPView;

/**
 * @author i11m20n
 * 注意：这个合约类是 MVP 模式中的合约类，并未智能合约。
 */
final class WalletContract {

    interface IView extends IMVPView {

        /**
         * 查询账户余额回调
         *
         * @param balanceInfo 账户余额信息
         */
        void accountBalance(AccountBalanceInfo balanceInfo);

        /**
         * 查询余额失败时回调
         *
         * @param errorMsg 错误消息
         */
        void acquireBalanceFailed(String errorMsg);
    }

    interface IPresenter extends IMVPPresenter<IView> {

        /**
         * 获取钱包名称
         * <p>
         * 获取速度很快，无需通过回调进行交互。
         * </p>
         */
        String acquireWalletName();

        /**
         * 获取钱包地址
         * <p>
         * 获取速度很快，无须通过回调进行交互。
         * </p>
         */
        String acquireWalletAddress();

        /**
         * 获取账户余额
         */
        void acquireAccountBalance();
    }
}
