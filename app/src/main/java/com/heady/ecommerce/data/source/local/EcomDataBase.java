package com.heady.ecommerce.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;

@Database(entities = {CategoryEntity.class, ProductEntity.class, Variant.class}, version = 1)
public abstract class EcomDataBase extends RoomDatabase {
    public static final String NAME = "ecommerce_db";

    public abstract ProductListDao getProductListDao();
}
