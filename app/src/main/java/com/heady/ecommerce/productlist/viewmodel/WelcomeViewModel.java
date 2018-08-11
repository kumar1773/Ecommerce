package com.heady.ecommerce.productlist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.SingleLiveEvent;
import com.heady.ecommerce.data.source.Repository;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;

import java.util.List;

import javax.inject.Inject;

public class WelcomeViewModel extends ViewModel implements ProductClickListener,CategoryClickListener {
    @Inject
    Repository repository;

    private SingleLiveEvent<Integer> openProductDetails = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> openCategoryDetails = new SingleLiveEvent<>();

    public WelcomeViewModel() {
        EcomApplicaiton.get().getApplicationComponent().injectProductListViewModel(this);
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return repository.getAllCategories();
    }

    public LiveData<List<ProductEntity>> getMostOrderedProducts() {
        return repository.getMostOrderedProducts();
    }

    public LiveData<List<ProductEntity>> getMostSharedProducts() {
        return repository.getMostSharedProducts();
    }

    public LiveData<List<ProductEntity>> getMostViewedProduct() {
        return repository.getMostViewedProducts();
    }

    public LiveData<Boolean> getIsDataFetched() {
        return repository.isDataFetched();
    }

    public LiveData<Boolean> getIsErrorOccured() {
        return repository.isErrorOccured();
    }


    public LiveData<Integer> getSubCategoryCount(int catId) {
        return repository.getSubCategoryCount(catId);
    }

    public LiveData<List<CategoryEntity>> getSubCategoryList(int catId) {
        return repository.getSubCategoryList(catId);
    }

    public MutableLiveData<Integer> getOpenProductEvent(){
        return openProductDetails;
    }

    public LiveData<Integer> getOpenCategoryEvent() {
        return openCategoryDetails;
    }

    @Override
    public void onProductClicked(int productId) {
        openProductDetails.setValue(productId);
    }

    @Override
    public void onCategoryClicked(int categoryId) {
        openCategoryDetails.setValue(categoryId);
    }
}
