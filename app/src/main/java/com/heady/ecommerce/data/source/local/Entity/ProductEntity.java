package com.heady.ecommerce.data.source.local.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "product",
        foreignKeys = @ForeignKey(entity = CategoryEntity.class,
                parentColumns = "id",
                childColumns = "cat_id",
                onDelete = CASCADE),
        indices = {@Index(value = {"cat_id"})})
public class ProductEntity {
    @PrimaryKey
    public int id;
    public String name;
    public String date;
    @ColumnInfo(name = "cat_id")
    public int categoryId;
    @Embedded
    public Tax tax;
    @ColumnInfo(name = "view_count")
    public int viewCount;
    @ColumnInfo(name = "order_count")
    public int orderCount;
    @ColumnInfo(name = "share_count")
    public int shareCount;
}
