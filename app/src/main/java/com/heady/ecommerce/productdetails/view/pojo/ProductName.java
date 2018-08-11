package com.heady.ecommerce.productdetails.view.pojo;

import static com.heady.ecommerce.productdetails.view.ProductDetailsAdapter.TYPE_NAME;

public class ProductName implements ProductDetailsItem {
    public String name;

    public ProductName(String name) {
        this.name = name;
    }

    @Override
    public int getType() {
        return TYPE_NAME;
    }
}
