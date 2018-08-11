package com.heady.ecommerce.productdetails.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ProductDetailHeaderBinding;
import com.heady.ecommerce.databinding.ProductDetailKeyValueBinding;
import com.heady.ecommerce.databinding.ProductDetailNameBinding;
import com.heady.ecommerce.productdetails.view.pojo.KeyValuePair;
import com.heady.ecommerce.productdetails.view.pojo.ProductDetailsItem;
import com.heady.ecommerce.productdetails.view.pojo.ProductHeader;
import com.heady.ecommerce.productdetails.view.pojo.ProductName;

import java.util.ArrayList;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NAME = 1;
    public static final int TYPE_KEY_VALUE = 2;
    private static final int NOT_FOUND = -1;
    private ArrayList<ProductDetailsItem> mData = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_detail_header, parent, false);
                return new ViewHolder(dataBinding);
            case TYPE_NAME:
                dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_detail_name, parent, false);
                return new ViewHolder(dataBinding);
            case TYPE_KEY_VALUE:
                dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_detail_key_value, parent, false);
                return new ViewHolder(dataBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetailsItem productDetailsItem = mData.get(position);
        switch (productDetailsItem.getType()) {
            case TYPE_HEADER:
                ProductHeader header = (ProductHeader) productDetailsItem;
                final ProductDetailHeaderBinding headerBinding = (ProductDetailHeaderBinding) holder.binding;
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false);
                headerBinding.rvProductVariants.setLayoutManager(layoutManager);
                headerBinding.rvProductVariants.setAdapter(new VariantsAdapter(header.variants));
                headerBinding.viewPagerIndicator.attachToRecyclerView(headerBinding.rvProductVariants);
                SnapHelper snapHelper = new PagerSnapHelper() {
                    @Override
                    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                        int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                        headerBinding.viewPagerIndicator.onPageSelected(targetPos);
                        return targetPos;
                    }
                };
                snapHelper.attachToRecyclerView(headerBinding.rvProductVariants);
                break;
            case TYPE_NAME:
                ProductName productName = (ProductName) productDetailsItem;
                ProductDetailNameBinding nameBinding = (ProductDetailNameBinding) holder.binding;
                nameBinding.tvProduct.setText(productName.name);
                break;
            case TYPE_KEY_VALUE:
                KeyValuePair keyValuePair = (KeyValuePair) productDetailsItem;
                ProductDetailKeyValueBinding keyValueBinding = (ProductDetailKeyValueBinding) holder.binding;
                keyValueBinding.tvLabel.setText(keyValuePair.key);
                keyValueBinding.tvValue.setText(keyValuePair.value);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    public void addData(ProductDetailsItem data) {
        if (isNewData(data.getType())) {
            mData.add(data);
            notifyItemInserted(mData.size() - 1);
        } else {
            int pos = getPosition(data.getType());
            mData.set(pos, data);
            notifyItemChanged(pos);
        }
    }

    private int getPosition(int type) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(0).getType() == type) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private boolean isNewData(int type) {
        return getPosition(type) == NOT_FOUND;
    }

    public void addHeader(ProductHeader productHeader) {
        if (mData.size() > 0 && mData.get(0) instanceof ProductHeader) {
            mData.set(0, productHeader);
            notifyItemChanged(0);
        } else {
            mData.add(0, productHeader);
            notifyItemInserted(0);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
