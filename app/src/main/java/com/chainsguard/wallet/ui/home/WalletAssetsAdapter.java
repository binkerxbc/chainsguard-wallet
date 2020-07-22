package com.chainsguard.wallet.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chainsguard.wallet.data.bean.AccountBalanceInfo;
import com.chainsguard.wallet.databinding.ItemWalletAssetsBinding;

/**
 * @author i11m20n
 */
final class WalletAssetsAdapter extends RecyclerView.Adapter<WalletAssetsAdapter.WalletAdapterViewHolder> {

    private AccountBalanceInfo balanceInfo;

    public final void setData(AccountBalanceInfo balanceInfo) {
        this.balanceInfo = balanceInfo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWalletAssetsBinding viewBinding =
                ItemWalletAssetsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WalletAdapterViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapterViewHolder holder, int position) {
        if (balanceInfo != null && balanceInfo.getCurrencies() != null) {
            AccountBalanceInfo.Currencies currencies = balanceInfo.getCurrencies().get(position);
            holder.mItemViewBinding.assetsTokenSymbol.setText(currencies.getSymbol());
            holder.mItemViewBinding.assetsBalance.setText(currencies.getAvailable());
            return;
        }

        if (position == 0) {
            holder.mItemViewBinding.assetsTokenSymbol.setText("tokt");
        } else if (position == 1) {
            holder.mItemViewBinding.assetsTokenSymbol.setText("tokb");
        } else if (position == 2) {
            holder.mItemViewBinding.assetsTokenSymbol.setText("tusdk");
        } else if (position == 3) {
            holder.mItemViewBinding.assetsTokenSymbol.setText("tbtc");
        }
    }

    @Override
    public int getItemCount() {
        if (balanceInfo != null && balanceInfo.getCurrencies() != null) {
            return balanceInfo.getCurrencies().size();
        }

        // 如果查询链上数据失败则显示默认的余额
        return 4;
    }

    static final class WalletAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemWalletAssetsBinding mItemViewBinding;

        public WalletAdapterViewHolder(@NonNull ItemWalletAssetsBinding viewBinding) {
            super(viewBinding.getRoot());
            mItemViewBinding = viewBinding;
        }
    }
}
