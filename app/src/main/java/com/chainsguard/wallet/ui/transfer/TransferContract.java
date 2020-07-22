package com.chainsguard.wallet.ui.transfer;

import com.chainsguard.wallet.data.bean.TransferInfo;
import com.chainsguard.wallet.data.bean.TransferResult;
import com.chainsguard.wallet.ui.base.IMVPPresenter;
import com.chainsguard.wallet.ui.base.IMVPView;

/**
 * @author i11m20n
 */
final class TransferContract {

    interface IView extends IMVPView {

        /**
         * 转账完成
         *
         * @param transferResult 转账结果
         */
        void transferCompleted(TransferResult transferResult);

        /**
         * 转账失败
         *
         * @param errorMsg 失败消息
         */
        void transferFailed(String errorMsg);
    }

    interface IPresenter extends IMVPPresenter<IView> {

        /**
         * 获取钱包地址
         * <p>
         * 获取速度很快，无须通过回调进行交互。
         * </p>
         */
        String acquireWalletAddress();

        /**
         * 获取钱包密码
         *
         * @return 钱包密码
         */
        String acquireWalletPassword();

        /**
         * 发送转账
         *
         * @param transferInfo 转账信息
         */
        void transfer(TransferInfo transferInfo);
    }
}
