package com.chainsguard.wallet.ui.base;

import com.chainsguard.wallet.data.DataManager;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author i11m20n
 */
public abstract class BasePresenter<V extends IMVPView> implements IMVPPresenter<V> {

    protected V mView;
    protected DataManager mDataModel;
    private CompositeDisposable mCompositeDisposable;

    protected BasePresenter() {
        mDataModel = DataManager.getInstance();
    }

    @Override
    public final void attachView(V view) {
        mView = view;
    }

    @Override
    public final boolean isViewAttach() {
        return mView != null;
    }

    @Override
    public final void detachView() {
        mView = null;
        mDataModel = null;
        if (mCompositeDisposable != null) {
            // 清除所有异步操作
            mCompositeDisposable.clear();
        }
    }

    protected final void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(disposable);
    }
}
