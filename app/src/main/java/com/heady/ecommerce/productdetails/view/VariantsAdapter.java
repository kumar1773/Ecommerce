package com.heady.ecommerce.productdetails.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.data.source.local.Entity.Variant;
import com.heady.ecommerce.databinding.VariantItemBinding;
import com.heady.ecommerce.util.Utils;

import java.util.List;

class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.ViewHolder> {
    private final List<Variant> mVariants;

    public VariantsAdapter(List<Variant> variants) {
        this.mVariants = variants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VariantItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.variant_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Variant variant = mVariants.get(position);
        holder.binding.container.setBackgroundColor(Utils.getRandomColor(holder.binding.getRoot().getContext()));
        holder.binding.tvValueColor.setText(variant.color);
        holder.binding.tvValuePrice.setText(variant.price + "");
        holder.binding.tvValueSize.setText(variant.size + "");
    }

    @Override
    public int getItemCount() {
        return mVariants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private VariantItemBinding binding;

        public ViewHolder(VariantItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
