package com.heady.ecommerce.data.source.remote.webservice.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<Product> products = null;
    @JsonProperty("child_categories")
    private List<Integer> childCategories = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @JsonProperty("child_categories")
    public List<Integer> getChildCategories() {
        return childCategories;
    }

    @JsonProperty("child_categories")
    public void setChildCategories(List<Integer> childCategories) {
        this.childCategories = childCategories;
    }

}
