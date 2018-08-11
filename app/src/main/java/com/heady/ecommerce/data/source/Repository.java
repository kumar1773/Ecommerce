package com.heady.ecommerce.data.source;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.heady.ecommerce.EcomApplicaiton;
import com.heady.ecommerce.data.source.local.Entity.CategoryEntity;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;
import com.heady.ecommerce.data.source.local.Entity.Variant;
import com.heady.ecommerce.data.source.local.ProductListLocalDataSource;
import com.heady.ecommerce.data.source.remote.webservice.EcommerceService;
import com.heady.ecommerce.data.source.remote.webservice.pojo.Category;
import com.heady.ecommerce.data.source.remote.webservice.pojo.Product;
import com.heady.ecommerce.data.source.remote.webservice.pojo.ProductList;
import com.heady.ecommerce.data.source.remote.webservice.pojo.ProductStats;
import com.heady.ecommerce.data.source.remote.webservice.pojo.Ranking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository implements ProductListDataSource {
    private static final String TAG = Repository.class.getSimpleName();
    private static final String PREFS = "shared_prefs";
    private static final String DATA_FETCHED = "key_data_fetched";
    @Inject
    Context context;

    @Inject
    ProductListLocalDataSource dataSource;

    @Inject
    EcommerceService ecommerceService;
    private MutableLiveData<Boolean> isDataFetched;
    private MutableLiveData<Boolean> isErrorOccured;

    public Repository(Context context) {
        this.context = context;
        EcomApplicaiton.get().getApplicationComponent().injectRepo(this);
        initState();

        populateDb();
    }

    private void initState() {
        isDataFetched = new MutableLiveData<>();
        isDataFetched.setValue(false);
        isErrorOccured = new MutableLiveData<>();
        isErrorOccured.setValue(false);
    }

    @Override
    public LiveData<List<CategoryEntity>> getAllCategories() {
        return dataSource.getAllCategories();
    }

    @Override
    public LiveData<List<ProductEntity>> getMostViewedProducts() {
        return dataSource.getMostViewedProducts();
    }

    @Override
    public LiveData<List<ProductEntity>> getMostOrderedProducts() {
        return dataSource.getMostOrderedProducts();
    }

    @Override
    public LiveData<List<ProductEntity>> getMostSharedProducts() {
        return dataSource.getMostSharedProducts();
    }

    @Override
    public LiveData<List<Variant>> getVariants(int productId) {
        return dataSource.getVariants(productId);
    }

    @Override
    public void insertCategories(Collection<CategoryEntity> categories) {
        dataSource.insertCategories(categories);
    }

    @Override
    public void insertProducts(Collection<ProductEntity> products) {
        dataSource.insertProducts(products);
    }

    @Override
    public void insertVariants(List<Variant> variants) {
        dataSource.insertVariants(variants);
    }

    @Override
    public LiveData<Boolean> isDataFetched() {
        return isDataFetched;
    }

    @Override
    public LiveData<Boolean> isErrorOccured() {
        return isErrorOccured;
    }

    @Override
    public LiveData<ProductEntity> getProduct(int productId) {
        return dataSource.getProduct(productId);
    }

    private void populateDb() {
        if (!isLocalDbPopulated()) {
            fetchProducts();
        } else {
            isDataFetched.setValue(true);
        }
    }

    private boolean isLocalDbPopulated() {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(DATA_FETCHED, false);
    }

    private void setLocalDbPopulated(boolean isPopulated) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        edit.putBoolean(DATA_FETCHED, isPopulated);
        edit.apply();
        isDataFetched.postValue(true);
    }

    private void fetchProducts() {
        ecommerceService.getProducts().enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                new InsertProducts(response.body()).execute();
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                isErrorOccured.setValue(true);
            }
        });
    }

    @Override
    public LiveData<Integer> getSubCategoryCount(int catId) {
        return dataSource.getSubCategoryCount(catId);
    }

    @Override
    public LiveData<List<CategoryEntity>> getSubCategoryList(int catId) {

        return dataSource.getSubCategoryList(catId);
    }

    @Override
    public LiveData<List<ProductEntity>> getProducts(int catId) {
        return dataSource.getProducts(catId);
    }

    class InsertProducts extends AsyncTask<Void, Void, Boolean> {

        private final ProductList products;

        public InsertProducts(ProductList body) {
            this.products = body;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                List<Category> categories = products.getCategories();
                HashMap<Integer, CategoryEntity> categoryMap = new HashMap<>();
                HashMap<Integer, ProductEntity> productMap = new HashMap<>();
                HashMap<Integer, Integer> childToParentCatMap = new HashMap<>(categories.size());
                ArrayList<Variant> variantList = new ArrayList<>();
                for (Category category : categories) {
                    addCategory(categoryMap, category);
                    addChildMapping(childToParentCatMap, category);
                    addProducts(productMap, category, variantList);
                }
                updateCatMapping(categoryMap, childToParentCatMap);
                updateCountInProducts(productMap, products.getRankings());
                insertCategories(categoryMap.values());
                insertProducts(productMap.values());
                insertVariants(variantList);
                setLocalDbPopulated(true);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Error while saving results from server", e);
            }
            return false;
        }

        private void updateCountInProducts(HashMap<Integer, ProductEntity> productMap, List<Ranking> rankings) {
            for (Ranking ranking : rankings) {
                List<ProductStats> products = ranking.getProducts();
                for (ProductStats stats : products) {
                    ProductEntity productEntity = productMap.get(stats.getId());
                    if (productEntity != null) {
                        if (stats.getOrderCount() > 0)
                            productEntity.orderCount = stats.getOrderCount();
                        if (stats.getShares() > 0)
                            productEntity.shareCount = stats.getShares();
                        if (stats.getViewCount() > 0)
                            productEntity.viewCount = stats.getViewCount();
                    }
                }
            }
        }

        private void updateCatMapping(HashMap<Integer, CategoryEntity> categoryMap, HashMap<Integer, Integer> childToParentCatMap) {
            for (Map.Entry<Integer, CategoryEntity> entry : categoryMap.entrySet()) {
                Integer parentId = childToParentCatMap.get(entry.getKey());
                if (parentId == null || parentId == 0) {
                    entry.getValue().parent_id = entry.getValue().id;
                } else {
                    entry.getValue().parent_id = parentId;
                }
            }
        }

        private void addProducts(HashMap<Integer, ProductEntity> productMap, Category category,
                                 ArrayList<Variant> variantList) {
            List<Product> products = category.getProducts();
            for (Product product : products) {
                ProductEntity productEntity = productMap.get(product.getId());
                if (productEntity == null) {
                    productEntity = new ProductEntity();
                }
                productEntity.categoryId = category.getId();
                productEntity.name = product.getName();
                productEntity.date = product.getDateAdded();
                productEntity.id = product.getId();
                productEntity.tax = product.getTax();
                productMap.put(product.getId(), productEntity);
                updateVariants(variantList, product);
            }
        }

        private void updateVariants(ArrayList<Variant> variantList, Product product) {
            List<Variant> variants = product.getVariants();
            for (Variant variant : variants) {
                variant.p_id = product.getId();
                variantList.add(variant);
            }
        }

        private void addChildMapping(HashMap<Integer, Integer> childToParentCatMap, Category category) {
            List<Integer> childCategories = category.getChildCategories();
            for (int childCat : childCategories) {
                childToParentCatMap.put(childCat, category.getId());
            }
        }

        private void addCategory(HashMap<Integer, CategoryEntity> categoryMap, Category category) {
            CategoryEntity categoryEntity = categoryMap.get(category.getId());
            if (categoryEntity == null) {
                categoryEntity = new CategoryEntity();
            }
            categoryEntity.id = category.getId();
            categoryEntity.name = category.getName();
            categoryMap.put(category.getId(), categoryEntity);
        }
    }
}
