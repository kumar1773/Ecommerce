package com.heady.ecommerce.data.source.local.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "variant",
        foreignKeys = @ForeignKey(entity = ProductEntity.class,
                parentColumns = "id",
                childColumns = "p_id",
                onDelete = CASCADE),
        indices = {@Index(value = {"p_id"})})
public class Variant {
    @PrimaryKey
    public int id;

    public int p_id;
    public String color;
    public float price;
    public int size;
}
