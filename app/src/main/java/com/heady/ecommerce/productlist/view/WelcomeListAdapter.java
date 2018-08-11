package com.heady.ecommerce.productlist.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ItemProductMainBinding;
import com.heady.ecommerce.productlist.viewmodel.WelcomeViewModel;
import com.heady.ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class WelcomeListAdapter extends RecyclerView.Adapter<WelcomeListAdapter.ProductViewHolder> {
    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_MOST_VIEWED = 1;
    public static final int TYPE_MOST_SHARED = 2;
    public static final int TYPE_MOST_ORDERED = 3;
    private static final int NOT_FOUND = -1;
    private final WelcomeViewModel viewModel;

    private ArrayList<ProductListData> mData = new ArrayList<>();

    public WelcomeListAdapter(WelcomeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override

    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductMainBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_product_main, parent, false);
        return new ProductViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductListData data = mData.get(position);
        holder.binding.tvHeader.setText(data.heading);
        holder.binding.tvHeader.setBackgroundColor(Utils.getRandomColor(holder.binding.tvHeader.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.binding.rvProductSublist.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        holder.binding.rvProductSublist.setLayoutManager(layoutManager);

        if (data.type == TYPE_CATEGORY) {
            holder.binding.rvProductSublist.setAdapter(new CategoryAdapter(data.items, viewModel));
        } else {
            holder.binding.rvProductSublist.setAdapter(new ProductsAdapter(data.items, data.type, viewModel));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }

    public void addData(ProductListData data) {
        if (isNewData(data.type)) {
            mData.add(data);
            notifyItemInserted(mData.size() - 1);
        } else {
            int pos = getPosition(data.type);
            mData.set(pos, data);
            notifyItemChanged(pos);
        }
    }

    private int getPosition(int type) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(0).type == type) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private boolean isNewData(int type) {
        return getPosition(type) == NOT_FOUND;
    }

    static class ProductListData {
        private int type;
        private String heading;
        private List items;

        ProductListData(int type, String heading, List items) {
            this.type = type;
            this.heading = heading;
            this.items = items;
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ItemProductMainBinding binding;

        private ProductViewHolder(ItemProductMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
