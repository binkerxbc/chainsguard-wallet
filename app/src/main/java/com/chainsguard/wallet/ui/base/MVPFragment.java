package com.chainsguard.wallet.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author i11m20n
 */
public abstract class MVPFragment<P extends IMVPPresenter> extends BaseFragment implements IMVPView {

    protected P mPresenter;

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = initPresenter();
        //noinspection unchecked
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @NonNull
    protected abstract P initPresenter();
}
