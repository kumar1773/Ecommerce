package com.heady.ecommerce.di.component;

import android.content.Context;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.categorylisting.CategoryViewModel;
import com.heady.ecommerce.di.modules.ContextModule;
import com.heady.ecommerce.di.modules.DbModule;
import com.heady.ecommerce.di.modules.NetworkModule;
import com.heady.ecommerce.di.modules.RepositoryModule;
import com.heady.ecommerce.di.scope.ApplicationScope;
import com.heady.ecommerce.data.source.local.ProductListLocalDataSource;
import com.heady.ecommerce.productdetails.viewmodel.ProductDetailViewModel;
import com.heady.ecommerce.productlist.viewmodel.ProductListViewModel;
import com.heady.ecommerce.productlist.viewmodel.WelcomeViewModel;
import com.heady.ecommerce.data.source.Repository;
import com.heady.ecommerce.data.source.remote.webservice.EcommerceService;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RepositoryModule.class,
        NetworkModule.class, DbModule.class})
public interface ApplicationComponent {

    EcommerceService getEcommerceService();

    Context getContext();

    Repository getRepository();

    ProductListLocalDataSource getLocalDataSource();

    void injectApplication(EcomApplicaiton myApplication);

    void injectProductListViewModel(WelcomeViewModel viewModel);

    void injectRepo(Repository repository);

    void injectLocalDataSource(ProductListLocalDataSource localDataSource);

    void injectProductDetailsViewModel(ProductDetailViewModel productDetailViewModel);

    void injectCategoryViewModel(CategoryViewModel categoryViewModel);

    void injectProductListViewModel(ProductListViewModel productListViewModel);
}
