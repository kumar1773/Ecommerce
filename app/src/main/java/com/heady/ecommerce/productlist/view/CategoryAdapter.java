package com.heady.ecommerce.productlist.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ItemNestedCategoryCardBinding;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.productlist.viewmodel.CategoryClickListener;
import com.heady.ecommerce.util.Utils;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryEntity> mCategories;
    private final CategoryClickListener listener;

    public CategoryAdapter(List<CategoryEntity> categories, CategoryClickListener listener) {
        this.mCategories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNestedCategoryCardBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_nested_category_card, parent, false);
        return new CategoryAdapter.ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryEntity category = mCategories.get(position);
        holder.binding.card.setCardBackgroundColor(Utils.getRandomColor(holder.binding.card.getContext()));
        holder.binding.tvCategory.setText(category.name);
        holder.binding.tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to category
                listener.onCategoryClicked(category.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void invalideData(List<CategoryEntity> categoryEntity) {
        this.mCategories = categoryEntity;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNestedCategoryCardBinding binding;

        public ViewHolder(ItemNestedCategoryCardBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
