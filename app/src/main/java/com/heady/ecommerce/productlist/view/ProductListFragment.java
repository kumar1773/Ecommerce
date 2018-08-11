package com.heady.ecommerce.productlist.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.databinding.ProductListFragBinding;
import com.heady.ecommerce.productdetails.view.ProductDetailFragment;
import com.heady.ecommerce.productlist.viewmodel.ProductListViewModel;
import com.heady.ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.heady.ecommerce.categorylisting.CategoryListFragment.COLUMN_COUNT;
import static com.heady.ecommerce.categorylisting.CategoryListFragment.ITEM_WIDTH;

public class ProductListFragment extends Fragment {
    private static final String ARG_CAT_ID = "catId";

    private int mCatId;
    private ProductListFragBinding mDataBinding;
    private ProductListViewModel mViewModel;
    private ProductsAdapter mAdapter;

    public ProductListFragment() {
    }

    public static ProductListFragment newInstance(int catId) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CAT_ID, catId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCatId = getArguments().getInt(ARG_CAT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.product_list_frag, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductListViewModel.class);
        initRecylerView();
        fetchData();
        intiListeners();
    }

    private void initRecylerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        mDataBinding.rvProductList.setLayoutManager(layoutManager);
        mAdapter = new ProductsAdapter(new ArrayList<ProductEntity>(), 0, mViewModel);
        mDataBinding.rvProductList.setAdapter(mAdapter);
    }

    private void fetchData() {
        mViewModel.getProductList(mCatId).observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (Utils.isValidCollection(productEntities)) {
                    mAdapter.invalidateData(productEntities);
                }
            }
        });
    }

    private void intiListeners() {
        mViewModel.getProductClickedEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer productId) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ProductDetailFragment.newInstance(productId))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
