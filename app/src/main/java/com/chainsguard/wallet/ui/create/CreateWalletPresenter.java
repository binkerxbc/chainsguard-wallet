package com.chainsguard.wallet.ui.create;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.chainsguard.wallet.data.bean.WalletInfo;
import com.chainsguard.wallet.ui.base.BasePresenter;
import com.chainsguard.wallet.util.RxUtils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author i11m20n
 */
final class CreateWalletPresenter extends BasePresenter<CreateWalletContract.IView> implements CreateWalletContract.IPresenter {

    @Override
    public void saveWalletName(String walletName) {
        mDataModel.saveWalletName(walletName);
    }

    @Override
    public void saveWalletPassword(String walletPassword) {
        mDataModel.saveWalletPassword(walletPassword);
    }

    @Override
    public void createWallet() {
        addSubscribe(Observable.create((ObservableOnSubscribe<WalletInfo>) emitter -> emitter.onNext(mDataModel.createWallet()))
                .compose(RxUtils.rxObservableSchedulerHelper())
                .subscribe(walletInfo -> {
                    if (isViewAttach()) {
                        mView.onWalletCreated(walletInfo);
                    }
                }, onError -> {
                    if (!isViewAttach()) {
                        return;
                    }

                    if (TextUtils.isEmpty(onError.getMessage())) {
                        mView.onCreateFailed("钱包创建失败！");
                        return;
                    }

                    mView.onCreateFailed(onError.getMessage());
                }));
    }

    @Override
    public void importWallet(@NonNull String mnemonic) {
        addSubscribe(Observable.just(mnemonic)
                .map(mnemonics -> mDataModel.importWallet(mnemonics))
                .compose(RxUtils.rxObservableSchedulerHelper())
                .subscribe(walletInfo -> {
                    if (isViewAttach()) {
                        mView.onWalletCreated(walletInfo);
                    }
                }, onError -> {
                    if (!isViewAttach()) {
                        return;
                    }

                    if (TextUtils.isEmpty(onError.getMessage())) {
                        mView.onCreateFailed("请检查助记词是否正确！");
                        return;
                    }

                    mView.onCreateFailed(onError.getMessage());
                }));
    }
}
