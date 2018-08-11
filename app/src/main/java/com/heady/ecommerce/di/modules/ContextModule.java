package com.heady.ecommerce.di.modules;


import android.content.Context;

import com.heady.ecommerce.di.qualifiers.ApplicationContext;
import com.heady.ecommerce.di.scope.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context provideContext() {
        return context;
    }
}
