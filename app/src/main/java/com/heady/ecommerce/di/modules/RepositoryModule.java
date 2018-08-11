package com.heady.ecommerce.di.modules;

import android.content.Context;

import com.heady.ecommerce.di.scope.ApplicationScope;
import com.heady.ecommerce.data.source.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module()
public class RepositoryModule {

    @ApplicationScope
    @Provides
    Repository getRepository(Context appContext) {
        return new Repository(appContext);
    }
}
