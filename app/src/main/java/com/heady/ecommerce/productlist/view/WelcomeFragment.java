package com.heady.ecommerce.productlist.view;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heady.ecommerce.R;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.databinding.ProductListFragmentBinding;
import com.heady.ecommerce.productlist.viewmodel.WelcomeViewModel;
import com.heady.ecommerce.util.Utils;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.heady.ecommerce.productlist.view.WelcomeListAdapter.TYPE_CATEGORY;
import static com.heady.ecommerce.productlist.view.WelcomeListAdapter.TYPE_MOST_ORDERED;
import static com.heady.ecommerce.productlist.view.WelcomeListAdapter.TYPE_MOST_SHARED;
import static com.heady.ecommerce.productlist.view.WelcomeListAdapter.TYPE_MOST_VIEWED;

public class WelcomeFragment extends Fragment {

    private static final int REQUEST_INTERNET_PERMISSION = 100;
    private WelcomeViewModel mViewModel;
    private ProductListFragmentBinding binding;
    private WelcomeListAdapter adapter;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_list_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initState();
        showNoNetworkToast();
    }

    private void initState() {
        mViewModel = ViewModelProviders.of(getActivity()).get(WelcomeViewModel.class);
        binding.setViewModel(mViewModel);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) == PERMISSION_GRANTED) {
            setupRecyclerView();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET_PERMISSION);
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(binding.rvProductList.getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.rvProductList.setLayoutManager(layoutManager);
        adapter = new WelcomeListAdapter(mViewModel);
        binding.rvProductList.setAdapter(adapter);
        startFetchingData();
    }

    private void startFetchingData() {
        mViewModel.getAllCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categoryEntities) {
                makeProductListVisible();
                if (isValidData(categoryEntities))
                    adapter.addData(new WelcomeListAdapter.ProductListData(TYPE_CATEGORY,
                            getResources().getString(R.string.browse_by_category),
                            categoryEntities));
            }
        });
        mViewModel.getMostViewedProduct().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (isValidData(productEntities))
                    adapter.addData(new WelcomeListAdapter.ProductListData(TYPE_MOST_VIEWED,
                            getResources().getString(R.string.most_viewed),
                            productEntities));
            }
        });
        mViewModel.getMostOrderedProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (isValidData(productEntities))
                    adapter.addData(new WelcomeListAdapter.ProductListData(TYPE_MOST_ORDERED,
                            getResources().getString(R.string.most_ordered),
                            productEntities));
            }
        });
        mViewModel.getMostSharedProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (isValidData(productEntities))
                    adapter.addData(new WelcomeListAdapter.ProductListData(TYPE_MOST_SHARED,
                            getResources().getString(R.string.most_shared),
                            productEntities));
            }
        });

    }

    private boolean isValidData(List data) {
        return data != null && data.size() > 0;
    }

    private void makeProductListVisible() {
        if (binding.rvProductList.getVisibility() != View.VISIBLE) {
            binding.rvProductList.setVisibility(View.VISIBLE);
        }
        binding.pbLoadingData.setVisibility(View.GONE);
        binding.tvErrorMsg.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_INTERNET_PERMISSION && grantResults[0] == PERMISSION_GRANTED) {
            initState();
        }
    }

    private void showNoNetworkToast() {
        if (!Utils.isNetworkAvailable(getActivity()))
            Toast.makeText(getActivity(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
    }

}
