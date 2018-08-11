package com.heady.ecommerce.data.source;

import android.arch.lifecycle.LiveData;

import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;

import java.util.Collection;
import java.util.List;

public interface ProductListDataSource {
    public LiveData<List<CategoryEntity>> getAllCategories();

    public LiveData<List<ProductEntity>> getMostViewedProducts();

    public LiveData<List<ProductEntity>> getMostOrderedProducts();

    public LiveData<List<ProductEntity>> getMostSharedProducts();

    public LiveData<List<Variant>> getVariants(int productId);

    public void insertCategories(Collection<CategoryEntity> categories);

    public void insertProducts(Collection<ProductEntity> products);

    public void insertVariants(List<Variant> categories);

    public LiveData<Boolean> isDataFetched();

    public LiveData<Boolean> isErrorOccured();

    LiveData<ProductEntity> getProduct(int productId);

    LiveData<Integer> getSubCategoryCount(int catId);

    LiveData<List<CategoryEntity>> getSubCategoryList(int catId);

    LiveData<List<ProductEntity>> getProducts(int catId);
}
