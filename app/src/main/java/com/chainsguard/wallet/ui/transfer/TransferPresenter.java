package com.chainsguard.wallet.ui.transfer;

import com.chainsguard.wallet.data.bean.TransferInfo;
import com.chainsguard.wallet.ui.base.BasePresenter;
import com.chainsguard.wallet.util.RxUtils;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author i11m20n
 */
final class TransferPresenter extends BasePresenter<TransferContract.IView> implements TransferContract.IPresenter {

    @Override
    public String acquireWalletAddress() {
        return mDataModel.acquireAddress();
    }

    @Override
    public String acquireWalletPassword() {
        return mDataModel.acquireWalletPassword();
    }

    @Override
    public void transfer(TransferInfo transferInfo) {
        addSubscribe(Observable.just(transferInfo)
                .map(info -> mDataModel.transfer(info))
                .compose(RxUtils.rxObservableSchedulerHelper())
                .subscribe(transferResult -> {
                    if (isViewAttach()) {
                        mView.transferCompleted(transferResult);
                    }
                }, onError -> {
                    if (isViewAttach()) {
                        mView.transferFailed(onError.getMessage());
                    }
                }));
    }
}
