package com.heady.ecommerce.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;
import com.heady.ecommerce.data.source.ProductListDataSource;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class ProductListLocalDataSource implements ProductListDataSource {
    @Inject
    ProductListDao dao;

    public ProductListLocalDataSource() {
        EcomApplicaiton.get().getApplicationComponent().injectLocalDataSource(this);
    }


    @Override
    public LiveData<List<CategoryEntity>> getAllCategories() {
        return dao.getAllCategories();
    }

    @Override
    public LiveData<List<ProductEntity>> getMostViewedProducts() {
        return dao.getMostViewedProducts();
    }

    @Override
    public LiveData<List<ProductEntity>> getMostOrderedProducts() {
        return dao.getMostOrderedProducts();
    }

    @Override
    public LiveData<List<ProductEntity>> getMostSharedProducts() {
        return dao.getMostSharedProducts();
    }

    @Override
    public LiveData<List<Variant>> getVariants(int productId) {
        return dao.getProductVariants(productId);
    }

    @Override
    public void insertCategories(Collection<CategoryEntity> categories) {
        dao.insertCategories(categories);
    }

    @Override
    public void insertProducts(Collection<ProductEntity> products) {
        dao.insertProducts(products);
    }

    @Override
    public void insertVariants(List<Variant> varinats) {
        dao.insertVariants(varinats);
    }

    @Override
    public LiveData<Boolean> isDataFetched() {
        return new MutableLiveData<>();
    }

    @Override
    public LiveData<Boolean> isErrorOccured() {
        return new MutableLiveData<>();
    }

    @Override
    public LiveData<ProductEntity> getProduct(int productId) {
        return dao.getProduct(productId);
    }

    @Override
    public LiveData<Integer> getSubCategoryCount(int catId) {
        return dao.getSubCategoryCount(catId);
    }

    @Override
    public LiveData<List<CategoryEntity>> getSubCategoryList(int catId) {
        return dao.getSubCategoryList(catId);
    }
    @Override
    public LiveData<List<ProductEntity>> getProducts(int catId) {
        return dao.getProducts(catId);
    }
}
