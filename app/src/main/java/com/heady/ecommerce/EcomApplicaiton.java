package com.heady.ecommerce;

import android.app.Application;

import com.heady.ecommerce.di.component.ApplicationComponent;
import com.heady.ecommerce.di.component.DaggerApplicationComponent;
import com.heady.ecommerce.di.modules.ContextModule;
import com.heady.ecommerce.di.modules.NetworkModule;
import com.heady.ecommerce.di.modules.RepositoryModule;


public class EcomApplicaiton extends Application {
    private static EcomApplicaiton app;
    private ApplicationComponent applicationComponent;

    public static EcomApplicaiton get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        applicationComponent.injectApplication(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
