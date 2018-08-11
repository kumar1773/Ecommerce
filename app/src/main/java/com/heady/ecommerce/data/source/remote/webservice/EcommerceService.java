package com.heady.ecommerce.data.source.remote.webservice;

import com.heady.ecommerce.data.source.remote.webservice.pojo.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EcommerceService {
    @GET("json")
    Call<ProductList> getProducts();
}
