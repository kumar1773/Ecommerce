package com.heady.ecommerce.data.source.remote.webservice.pojo;

import android.arch.persistence.room.ColumnInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductList {
    private List<Category> categories = null;
    private List<Ranking> rankings = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }
}
