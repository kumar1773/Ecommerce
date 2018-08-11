package com.heady.ecommerce.categorylisting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heady.ecommerce.R;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.databinding.CategoryListFragmentBinding;
import com.heady.ecommerce.productdetails.view.ProductDetailFragment;
import com.heady.ecommerce.productlist.view.CategoryAdapter;
import com.heady.ecommerce.productlist.view.ProductListFragment;
import com.heady.ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class CategoryListFragment extends Fragment {
    private static final String ARG_CATEGORY_ID = "catid";
    public static final int ITEM_WIDTH = 100;
    public static final int COLUMN_COUNT = 3;

    private int mCatId;
    private CategoryListFragmentBinding mDataBinding;
    private CategoryViewModel mViewModel;
    private CategoryAdapter mAdapter;

    public CategoryListFragment() {
    }

    public static CategoryListFragment newInstance(int catId) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, catId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCatId = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.category_list_fragment, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        initRecycler();
        fetchData();
        initListeners();
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        mDataBinding.rvSubcatList.setLayoutManager(layoutManager);
        mAdapter = new CategoryAdapter(new ArrayList<CategoryEntity>(), mViewModel);
        mDataBinding.rvSubcatList.setAdapter(mAdapter);
    }

    private void fetchData() {
        mViewModel.getSubcategoryListing(mCatId).observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categoryEntity) {
                mAdapter.invalideData(categoryEntity);
            }
        });
    }

    private void initListeners() {
        mViewModel.getOnSubcatEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer catId) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ProductListFragment.newInstance(catId))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
