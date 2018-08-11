package com.heady.ecommerce.productdetails.view.pojo;

import com.heady.ecommerce.data.source.local.Entity.Variant;

import java.util.List;

import static com.heady.ecommerce.productdetails.view.ProductDetailsAdapter.TYPE_HEADER;

public class ProductHeader implements ProductDetailsItem {
    public List<Variant> variants;

    public ProductHeader(List<Variant> variants) {
        this.variants = variants;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
