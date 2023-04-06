package com.woleapp.model;

import androidx.room.Entity;
import androidx.room.Fts4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "inventoryFts")
@Fts4(contentEntity = Inventory.class)
public class InventoryFts
{
    @SerializedName("product_name")
    @Expose
    private String product_name;


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

}
