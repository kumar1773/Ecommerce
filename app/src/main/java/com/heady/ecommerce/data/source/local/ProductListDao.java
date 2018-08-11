package com.heady.ecommerce.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;

import java.util.Collection;
import java.util.List;

@Dao
public interface ProductListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(Collection<CategoryEntity> categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(Collection<ProductEntity> categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVariants(Collection<Variant> categories);


    @Query("select * from category")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Query("select * from product order by view_count DESC")
    LiveData<List<ProductEntity>> getMostViewedProducts();

    @Query("select * from product order by order_count DESC")
    LiveData<List<ProductEntity>> getMostOrderedProducts();

    @Query("select * from product order by share_count DESC")
    LiveData<List<ProductEntity>> getMostSharedProducts();

    @Query("select * from Variant where p_id = :id")
    LiveData<List<Variant>> getProductVariants(int id);

    @Query("select * from product where id = :productId")
    LiveData<ProductEntity> getProduct(int productId);

    @Query("SELECT COUNT(id) FROM category WHERE parent_id = :id")
    LiveData<Integer> getSubCategoryCount(int id);

    @Query("SELECT * FROM category WHERE parent_id = :id")
    LiveData<List<CategoryEntity>> getSubCategoryList(int id);

    @Query("select * from product where cat_id = :catId")
    LiveData<List<ProductEntity>> getProducts(int catId);
}
