package com.heady.ecommerce.data.source.remote.webservice.pojo;

import java.util.List;

public class Ranking {
    private String ranking;
    private List<ProductStats> products = null;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<ProductStats> getProducts() {
        return products;
    }

    public void setProducts(List<ProductStats> products) {
        this.products = products;
    }

}
