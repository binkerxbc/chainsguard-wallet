package com.chainsguard.wallet.ui.addrbook;

import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.ui.base.IMVPPresenter;
import com.chainsguard.wallet.ui.base.IMVPView;

import java.util.List;

/**
 * @author i11m20n
 */
final class AddressBookContract {
    interface IView extends IMVPView {
        void allAddress(List<Address> addresses);

        void saveAddressCallback(boolean isSucceeded);
    }

    interface IPresenter extends IMVPPresenter<IView> {

        /**
         * 获取所有地址
         */
        void acquireAllAddress();

        /**
         * 保存地址
         *
         * @param address 地址信息
         */
        void saveAddress(Address address);
    }
}
