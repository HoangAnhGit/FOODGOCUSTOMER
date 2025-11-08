package com.example.foodgocustomer.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Import model Address (đã cập nhật)
import com.example.foodgocustomer.Model.Address;
import com.example.foodgocustomer.databinding.ItemAddressBinding;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList;
    private final OnAddressClickListener listener;

    public interface OnAddressClickListener {
        void onAddressClick(Address address);
    }

    public AddressAdapter(List<Address> addressList, OnAddressClickListener listener) {
        this.addressList = addressList;
        this.listener = listener;
    }

    public void updateData(List<Address> newAddressList) {
        this.addressList = newAddressList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddressBinding binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        if (address != null) {
            holder.bind(address, listener);
        }
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        private final ItemAddressBinding binding;

        public AddressViewHolder(@NonNull ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Address address, OnAddressClickListener listener) {
            binding.tvName.setText(address.getCustomerName());
            binding.tvFullAddress.setText(address.getFullAddress());
            binding.tvPhone.setText(address.getCustomerPhone());

            if (address.isDefault()) {
                binding.llDefaultContainer.setVisibility(View.VISIBLE);
            } else {
                binding.llDefaultContainer.setVisibility(View.GONE);
            }

            // Bắt sự kiện click
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddressClick(address);
                }
            });
        }
    }
}