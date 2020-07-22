package com.chainsguard.wallet.ui.base;

/**
 * @author i11m20n
 */
public interface IMVPPresenter<V extends IMVPView> {

    void attachView(V view);

    boolean isViewAttach();

    void detachView();
}
