package com.chainsguard.wallet.ui.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author i11m20n
 */
public abstract class MVPActivity<P extends IMVPPresenter> extends BaseActivity implements IMVPView {

    protected P mPresenter;

    @NonNull
    protected abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        //noinspection unchecked
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}