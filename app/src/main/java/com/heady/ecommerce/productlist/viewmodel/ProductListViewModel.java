package com.heady.ecommerce.productlist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.SingleLiveEvent;
import com.heady.ecommerce.data.source.Repository;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;

import java.util.List;

import javax.inject.Inject;

public class ProductListViewModel extends ViewModel implements ProductClickListener {
    @Inject
    Repository repository;

    private SingleLiveEvent<Integer> openProductDetails = new SingleLiveEvent<>();

    public ProductListViewModel() {
        EcomApplicaiton.get().getApplicationComponent().injectProductListViewModel(this);
    }

    @Override
    public void onProductClicked(int productId) {
        openProductDetails.setValue(productId);
    }

    public MutableLiveData<Integer> getProductClickedEvent() {
        return openProductDetails;
    }

    public LiveData<List<ProductEntity>> getProductList(int catId) {
        return repository.getProducts (catId);
    }
}
