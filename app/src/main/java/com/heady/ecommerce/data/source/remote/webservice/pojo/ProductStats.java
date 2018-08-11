package com.heady.ecommerce.data.source.remote.webservice.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductStats {
    private int id;
    @JsonProperty("view_count")
    private int viewCount;
    @JsonProperty("order_count")
    private int orderCount;
    @JsonProperty("shares")
    private int shares;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

}
