package com.heady.ecommerce.productlist.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.heady.ecommerce.R;
import com.heady.ecommerce.categorylisting.CategoryListFragment;
import com.heady.ecommerce.productdetails.view.ProductDetailFragment;
import com.heady.ecommerce.productlist.viewmodel.WelcomeViewModel;

import static com.heady.ecommerce.productdetails.view.ProductDetailFragment.PRODUCT_ID;

public class MainActivity extends AppCompatActivity {


    private WelcomeViewModel welcomeViewModel;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        addFragment(savedInstanceState);

        addListeners();
    }

    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WelcomeFragment.newInstance())
                    .commitNow();
        }
    }

    private void addListeners() {
        final WelcomeViewModel viewModel = ViewModelProviders.of(this).get(WelcomeViewModel.class);
        viewModel.getOpenProductEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer productId) {
                Bundle bundle = new Bundle();
                bundle.putInt(PRODUCT_ID, productId);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ProductDetailFragment.newInstance(productId))
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewModel.getOpenCategoryEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer catId) {
                viewModel.getSubCategoryCount(catId).observe(MainActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer subCatCount) {
                        if (subCatCount > 0) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, CategoryListFragment.newInstance(catId))
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, ProductListFragment.newInstance(catId))
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
            }
        });
    }
}
