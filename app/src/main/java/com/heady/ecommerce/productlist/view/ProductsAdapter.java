package com.heady.ecommerce.productlist.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ItemNestedProductCardBinding;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.productlist.viewmodel.ProductClickListener;
import com.heady.ecommerce.productlist.viewmodel.WelcomeViewModel;

import java.util.List;

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<ProductEntity> mProducts;
    private final int type;
    private final ProductClickListener listener;

    public ProductsAdapter(List<ProductEntity> products, int type, ProductClickListener listener) {
        this.mProducts = products;
        this.type = type;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNestedProductCardBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_nested_product_card, parent, false);
        return new ProductsAdapter.ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductEntity product = mProducts.get(position);
//        holder.binding.card.setCardBackgroundColor(Utils.getRandomColor(holder.binding.card.getContext()));
        holder.binding.tvProduct.setText(product.name);
        holder.binding.tvLabel.setText(getCount(product, type));
        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClicked(product.id);
            }
        });
    }

    private String getCount(ProductEntity product, int type) {
        switch (type) {
            case WelcomeListAdapter.TYPE_MOST_ORDERED:
                return product.orderCount + " Ordered";
            case WelcomeListAdapter.TYPE_MOST_SHARED:
                return product.shareCount + " Shared";
            case WelcomeListAdapter.TYPE_MOST_VIEWED:
            default:
                return product.viewCount + " Viewed";
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void invalidateData(List<ProductEntity> productEntities) {
        this.mProducts = productEntities;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNestedProductCardBinding binding;

        public ViewHolder(ItemNestedProductCardBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
