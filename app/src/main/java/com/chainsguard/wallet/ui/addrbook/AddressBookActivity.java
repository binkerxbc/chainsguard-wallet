package com.chainsguard.wallet.ui.addrbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.databinding.ActivityAddressBookBinding;
import com.chainsguard.wallet.ui.OnItemClickListener;
import com.chainsguard.wallet.ui.base.MVPActivity;

import java.util.List;

public final class AddressBookActivity extends MVPActivity<AddressBookPresenter> implements AddressBookContract.IView,
        OnItemClickListener<Address> {

    public static final int REQUEST_CODE = 21;
    public static final int RESULT_CODE = 22;
    public static final String EXTRA_DATA = "address";

    private ActivityAddressBookBinding mViewBinding;
    private AddressBookAdapter mAdapter;

    public static void startActivity(Fragment fragment) {
        Context context = fragment.requireContext();
        context.startActivity(new Intent(context, AddressBookActivity.class));
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, AddressBookActivity.class);
        intent.putExtra("source", "activity");
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @NonNull
    @Override
    protected AddressBookPresenter initPresenter() {
        return new AddressBookPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityAddressBookBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initToolbar();
        initData();
    }

    private void initData() {
        mAdapter = new AddressBookAdapter();
        mAdapter.setOnItemClickListener(this);
        mViewBinding.addressBookListRv.setAdapter(mAdapter);
        refresh();
    }

    private void initToolbar() {
        mViewBinding.addressBookToolbar.toolBarTv.setText("地址本");
        Drawable backDrawable = getDrawable(R.drawable.ic_arrow_back_black_24dp);
        assert backDrawable != null;
        backDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
        mViewBinding.addressBookToolbar.toolBar.setNavigationIcon(backDrawable);
        mViewBinding.addressBookToolbar.toolBar.inflateMenu(R.menu.address_book_menu);
        mViewBinding.addressBookToolbar.toolBar.setNavigationOnClickListener(v -> finish());
        mViewBinding.addressBookToolbar.toolBar.setOnMenuItemClickListener(item -> {
            AddAddressActivity.startActivity(this);
            return true;
        });
    }

    @Override
    public void allAddress(List<Address> addresses) {
        if (addresses.isEmpty()) {
            mViewBinding.addressBookListRv.setVisibility(View.GONE);
            mViewBinding.addressBookNoDataTv.setVisibility(View.VISIBLE);
            return;
        }

        mAdapter.setData(addresses);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddAddressActivity.REQUEST_CODE && resultCode == AddAddressActivity.RESULT_CODE) {
            // 保存地址成功，刷新 UI。
            refresh();
        }
    }

    private void refresh() {
        mViewBinding.addressBookListRv.setVisibility(View.VISIBLE);
        mViewBinding.addressBookNoDataTv.setVisibility(View.GONE);
        mPresenter.acquireAllAddress();
    }

    @Override
    public void saveAddressCallback(boolean isSucceeded) {
        // do nothing
    }


    @Override
    public void onItemClick(View v, Address address, int position) {
        if (getIntent().hasExtra("source")) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_DATA, address);
            setResult(RESULT_CODE, resultIntent);
            finish();
        }
    }
}