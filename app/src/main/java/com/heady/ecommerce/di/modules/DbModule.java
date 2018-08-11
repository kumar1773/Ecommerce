package com.heady.ecommerce.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.heady.ecommerce.di.scope.ApplicationScope;
import com.heady.ecommerce.data.source.local.EcomDataBase;
import com.heady.ecommerce.data.source.local.ProductListDao;
import com.heady.ecommerce.data.source.local.ProductListLocalDataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @ApplicationScope
    @Provides
    EcomDataBase getDataBase(Context context) {
        return Room.databaseBuilder(context, EcomDataBase.class, EcomDataBase.NAME).build();
    }

    @ApplicationScope

    @Provides
    public ProductListDao getDao(EcomDataBase dataBase) {
        return dataBase.getProductListDao();
    }


    @ApplicationScope

    @Provides
    public ProductListLocalDataSource getLocalDataSource() {
        return new ProductListLocalDataSource();
    }


}
