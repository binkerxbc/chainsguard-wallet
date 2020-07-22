package com.chainsguard.wallet.ui.home;

import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.ui.base.BasePresenter;
import com.chainsguard.wallet.util.RxUtils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author i11m20n
 */
final class WalletPresenter extends BasePresenter<WalletContract.IView> implements WalletContract.IPresenter {

    @Override
    public String acquireWalletName() {
        return mDataModel.acquireWalletName();
    }

    @Override
    public String acquireWalletAddress() {
        return mDataModel.acquireAddress();
    }

    @Override
    public void acquireAccountBalance() {
        addSubscribe(Observable.create((ObservableOnSubscribe<AccountBalanceInfo>) emitter -> {
            String address = mDataModel.acquireAddress();
            AccountBalanceInfo balance = mDataModel.queryAccountBalance(address);
            emitter.onNext(balance);
        }).compose(RxUtils.rxObservableSchedulerHelper())
                .subscribe(balanceInfo -> {
                    if (isViewAttach()) {
                        mView.accountBalance(balanceInfo);
                    }
                }, onError -> {
                    if (isViewAttach()) {
                        mView.acquireBalanceFailed(onError.getMessage());
                    }
                }));
    }
}
