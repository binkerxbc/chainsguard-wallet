package com.chainsguard.wallet.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chainsguard.wallet.R;
import com.chainsguard.wallet.WalletApplication;
import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.databinding.FragmentWalletBinding;
import com.chainsguard.wallet.ui.QrCodeActivity;
import com.chainsguard.wallet.ui.base.MVPFragment;
import com.chainsguard.wallet.ui.transfer.TransferActivity;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.Constant;


public final class WalletFragment extends MVPFragment<WalletPresenter> implements WalletContract.IView,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    static final int CAMERA_REQUEST_CODE = 101;

    private FragmentWalletBinding mViewBinding;
    private WalletAssetsAdapter mAssetsAdapter;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @NonNull
    @Override
    protected WalletPresenter initPresenter() {
        return new WalletPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentWalletBinding.inflate(inflater, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initListens();
    }

    private void initViews() {
        // toolbar
        mViewBinding.walletToolbar.toolBarTv.setText(R.string.app_name);

        // content
        mViewBinding.walletNameTv.setText(mPresenter.acquireWalletName());
        mViewBinding.walletAddressTv.setText(mPresenter.acquireWalletAddress());
        mAssetsAdapter = new WalletAssetsAdapter();
        // 解决嵌套滚动冲突
        mViewBinding.walletAssetsRv.setNestedScrollingEnabled(false);
        mViewBinding.walletAssetsRv.setAdapter(mAssetsAdapter);

        // 查询账户余额
        refresh();
    }

    private void initListens() {
        mViewBinding.walletAddressTv.setOnClickListener(this);
        mViewBinding.walletQrCodeScannerTv.setOnClickListener(this);
        mViewBinding.walletQrCodeTv.setOnClickListener(this);
        mViewBinding.walletTransferTv.setOnClickListener(this);
        mViewBinding.walletHistoryTv.setOnClickListener(this);
        mViewBinding.walletRefreshSrl.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_address_tv:
                ClipboardManager clipboard = (ClipboardManager) WalletApplication.getApplication()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("OKChain Address", mViewBinding.walletAddressTv.getText());
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "已复制地址到剪贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wallet_qr_code_scanner_tv:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        PermissionChecker.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                                != PermissionChecker.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                } else {
                    CaptureActivity.startActivityForResult(this);
                }
                break;
            case R.id.wallet_qr_code_tv:
                QrCodeActivity.startActivity(mContext);
                break;
            case R.id.wallet_transfer_tv:
                TransferActivity.startActivityForResult(this);
                break;
            case R.id.wallet_history_tv:
                Toast.makeText(mContext, "OKChain 尚未支持！", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            int grantResult = grantResults[0];
            if (grantResult == PermissionChecker.PERMISSION_GRANTED) {
                CaptureActivity.startActivityForResult(this);
            } else {
                Toast.makeText(mContext, "相机权限被拒绝！", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TransferActivity.TRANSFER_REQUEST_CODE &&
                resultCode == TransferActivity.TRANSFER_RESULT_CODE) {
            Toast.makeText(mContext, "转账成功！", Toast.LENGTH_LONG).show();
            // 刷新余额
            refresh();
        } else if (requestCode == Constant.REQ_QR_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                // 获取二维码扫描结果并转账
                String address = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
                TransferActivity.startActivityForResult(this, address);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding = null;
    }

    @Override
    public void accountBalance(AccountBalanceInfo balanceInfo) {
        mAssetsAdapter.setData(balanceInfo);
        mViewBinding.walletRefreshSrl.setRefreshing(false);
    }

    @Override
    public void acquireBalanceFailed(String errorMsg) {
        mViewBinding.walletRefreshSrl.setRefreshing(false);
        Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 主动刷新
     */
    private void refresh() {
        mViewBinding.walletRefreshSrl.setRefreshing(true);
        mPresenter.acquireAccountBalance();
    }

    @Override
    public void onRefresh() {
        mPresenter.acquireAccountBalance();
    }
}
