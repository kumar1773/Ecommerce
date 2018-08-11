package com.heady.ecommerce.productdetails.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;
import com.heady.ecommerce.databinding.ProductDetailFragmentBinding;
import com.heady.ecommerce.productdetails.view.pojo.KeyValuePair;
import com.heady.ecommerce.productdetails.view.pojo.ProductHeader;
import com.heady.ecommerce.productdetails.view.pojo.ProductName;
import com.heady.ecommerce.productdetails.viewmodel.ProductDetailViewModel;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class ProductDetailFragment extends Fragment {

    public static final String PRODUCT_ID = "product_id";
    private ProductDetailViewModel mViewModel;
    private ProductDetailFragmentBinding binding;
    private ProductDetailsAdapter adapter;

    public static ProductDetailFragment newInstance(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, productId);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_detail_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);
        initRecyclerView();
        fetchData();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        binding.rvProductDetails.setLayoutManager(layoutManager);
        adapter = new ProductDetailsAdapter();
        binding.rvProductDetails.setAdapter(adapter);
    }

    private void fetchData() {
        int productId = getArguments().getInt(PRODUCT_ID);
        mViewModel.getProductVariants(productId).observe(this, new Observer<List<Variant>>() {
            @Override
            public void onChanged(@Nullable List<Variant> variants) {
                adapter.addHeader(new ProductHeader(variants));
            }
        });
        mViewModel.getProduct(productId).observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(@Nullable ProductEntity product) {
                adapter.addData(new ProductName(product.name));
                adapter.addData(new KeyValuePair(getResources().getString(R.string.label_date), product.date));
                adapter.addData(new KeyValuePair(product.tax.getName(), product.tax.getValue() + ""));
                adapter.addData(new KeyValuePair(getResources().getString(R.string.label_view_count), product.viewCount + ""));
                adapter.addData(new KeyValuePair(getResources().getString(R.string.label_order_count), product.orderCount + ""));
                adapter.addData(new KeyValuePair(getResources().getString(R.string.label_share_count), product.shareCount + ""));
            }
        });

    }

}
