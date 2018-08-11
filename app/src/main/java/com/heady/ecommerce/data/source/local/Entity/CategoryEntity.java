package com.heady.ecommerce.data.source.local.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "category",
        indices = {@Index(value = {"parent_id"})})
public class CategoryEntity {
    @PrimaryKey
    public int id;
    public String name;
    public int parent_id;
}
