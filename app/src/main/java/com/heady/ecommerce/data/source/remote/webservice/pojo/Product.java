package com.heady.ecommerce.data.source.remote.webservice.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heady.ecommerce.data.source.local.Entity.Tax;
import com.heady.ecommerce.data.source.local.Entity.Variant;

import java.util.List;

public class Product {
    private int id;
    private String name;
    @JsonProperty("date_added")
    private String dateAdded;
    private List<Variant> variants = null;
    private Tax tax;

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

    @JsonProperty("date_added")
    public String getDateAdded() {
        return dateAdded;
    }

    @JsonProperty("date_added")
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

}
