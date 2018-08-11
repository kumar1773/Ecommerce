package com.heady.ecommerce.productdetails.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.data.source.Repository;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;

import java.util.List;

import javax.inject.Inject;

public class ProductDetailViewModel extends ViewModel {
    @Inject
    Repository repository;

    public ProductDetailViewModel() {
        EcomApplicaiton.get().getApplicationComponent().injectProductDetailsViewModel(this);
    }

    public LiveData<ProductEntity> getProduct(int productId) {
        return repository.getProduct(productId);
    }

    public LiveData<List<Variant>> getProductVariants(int productId) {
        return repository.getVariants(productId);
    }
}
