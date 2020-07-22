package com.chainsguard.wallet.ui;

import android.view.View;

/**
 * @author i11m20n
 */
public interface OnItemClickListener<T> {

    void onItemClick(View v, T t, int position);
}
