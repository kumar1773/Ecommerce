package com.heady.ecommerce.productdetails.view.pojo;

import static com.heady.ecommerce.productdetails.view.ProductDetailsAdapter.TYPE_KEY_VALUE;

public class KeyValuePair implements ProductDetailsItem {
    public String key;
    public String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int getType() {
        return TYPE_KEY_VALUE;
    }
}
