package com.heady.ecommerce.categorylisting;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.SingleLiveEvent;
import com.heady.ecommerce.data.source.Repository;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.productlist.viewmodel.CategoryClickListener;

import java.util.List;

import javax.inject.Inject;

public class CategoryViewModel extends ViewModel implements CategoryClickListener {
    private SingleLiveEvent<Integer> onSubcatClicked = new SingleLiveEvent<>();

    @Inject
    Repository repository;

    public CategoryViewModel() {
        EcomApplicaiton.get().getApplicationComponent().injectCategoryViewModel(this);
    }

    @Override
    public void onCategoryClicked(int subCatId) {
        onSubcatClicked.setValue(subCatId);
    }

    public MutableLiveData<Integer> getOnSubcatEvent() {
        return onSubcatClicked;
    }

    public LiveData<List<CategoryEntity>> getSubcategoryListing(int catId) {
        return repository.getSubCategoryList(catId);
    }
}
