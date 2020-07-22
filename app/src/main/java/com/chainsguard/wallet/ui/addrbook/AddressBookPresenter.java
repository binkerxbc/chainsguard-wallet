package com.chainsguard.wallet.ui.addrbook;

import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.ui.base.BasePresenter;
import com.chainsguard.wallet.util.RxUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author i11m20n
 */
final class AddressBookPresenter extends BasePresenter<AddressBookContract.IView> implements AddressBookContract.IPresenter {
    @Override
    public void acquireAllAddress() {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<Address>>) emitter -> emitter.onNext(mDataModel.getAllAddress()))
                .compose(RxUtils.rxObservableSchedulerHelper())
                .subscribe(addresses -> {
                    if (isViewAttach()) {
                        mView.allAddress(addresses);
                    }
                }));
    }

    @Override
    public void saveAddress(Address address) {
        addSubscribe(Observable.just(address)
                .map(address1 -> mDataModel.insertAddress(address1))
                .compose(RxUtils.rxObservableSchedulerHelper())
                .subscribe(isSucceeded -> {
                    if (isViewAttach()) {
                        mView.saveAddressCallback(isSucceeded);
                    }
                }));
    }
}
