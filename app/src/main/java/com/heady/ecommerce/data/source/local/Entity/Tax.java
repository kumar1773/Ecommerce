package com.heady.ecommerce.data.source.local.Entity;

import android.arch.persistence.room.ColumnInfo;

public class Tax {
    @ColumnInfo(name = "tax_name")
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
