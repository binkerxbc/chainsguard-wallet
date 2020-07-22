package com.chainsguard.wallet.ui.addrbook;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chainsguard.wallet.data.db.entity.Address;
import com.chainsguard.wallet.databinding.ItemAddressBookBinding;
import com.chainsguard.wallet.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author i11m20n
 */
final class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookViewHolder> {

    private List<Address> addresses = new ArrayList<>();
    private OnItemClickListener<Address> onItemClickListener;

    final void setOnItemClickListener(OnItemClickListener<Address> listener) {
        onItemClickListener = listener;
    }

    final void setData(List<Address> addresses) {
        this.addresses.clear();
        this.addresses.addAll(addresses);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddressBookBinding viewBinding = ItemAddressBookBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        AddressBookViewHolder viewHolder = new AddressBookViewHolder(viewBinding);
        viewBinding.getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                Address address = addresses.get(position);
                onItemClickListener.onItemClick(v, address, position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookViewHolder holder, int position) {
        Address address = addresses.get(position);
        holder.mItemViewBinding.itemAddressBookName.setText(address.getName());
        holder.mItemViewBinding.itemAddressBookAddress.setText(address.getAddress());
        String addressDesc = address.getDesc();
        if (TextUtils.isEmpty(addressDesc)) {
            holder.mItemViewBinding.itemAddressBookDesc.setVisibility(View.GONE);
        } else {
            holder.mItemViewBinding.itemAddressBookDesc.setVisibility(View.VISIBLE);
            holder.mItemViewBinding.itemAddressBookDesc.setText(addressDesc);
        }
    }

    @Override
    public int getItemCount() {
        return addresses.isEmpty() ? 0 : addresses.size();
    }

    static final class AddressBookViewHolder extends RecyclerView.ViewHolder {

        ItemAddressBookBinding mItemViewBinding;

        public AddressBookViewHolder(@NonNull ItemAddressBookBinding viewBinding) {
            super(viewBinding.getRoot());
            mItemViewBinding = viewBinding;
        }
    }
}
