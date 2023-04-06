package com.woleapp.model;

import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

import static com.woleapp.util.UtilsAndExtensionsKt.createRequestBodyFromString;

@Parcel(Parcel.Serialization.BEAN)
@Entity(tableName = "inventory")
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "rowid")
    @SerializedName(value = "inventory_id")
    @Expose
    private Integer inventory_id;


    @SerializedName("id")
    @Expose
    private String productId;

    @Expose
    @SerializedName("merchant_id")
    private String merchantId;

    @SerializedName("product_name")
    @Expose
    private String product_name;

    @SerializedName("category_id")
    @Expose
    private String category_name;

    @SerializedName(value = "product_price", alternate = {"price"})
    @Expose
    private String price = "";

    @SerializedName(value = "product_quantity", alternate = {"quantity"})
    @Expose
    private Integer quantity;


    @SerializedName(value = "product_description", alternate = {"description"})
    @Expose
    private String description;

    @SerializedName(value = "product_image", alternate = {"image_url"})
    @Expose
    private String image_path;

    private String imageLocalPath;

    @SerializedName("product_color")
    @Expose
    private String color;

    @SerializedName("product_size")
    @Expose
    private String size;

    private float deliveryFee;

    private String storeName;

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName(){
        return storeName;
    }

    public float getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    @NonNull
    public Integer getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(@NonNull Integer inventory_id) {
        this.inventory_id = inventory_id;
    }

    public String getProduct_name() {
        return product_name;//+": "+inventory_id;
    }

    public Spanned getProduct_nameSpn() {
        //return product_name;
        return Html.fromHtml(product_name);
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        Inventory inventory = (Inventory) obj;

        //return project.id == this.id && project.projectName == this.projectName;
        return inventory.inventory_id.intValue() == this.inventory_id.intValue();// && project.projectName == this.projectName;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "product_name='" + product_name + '\'' +
                " product_quantity" + quantity +
                '}';
    }

    public Map<String, String> toFieldMap() {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("product_image", image_path);
        fieldMap.put("product_description", description);
        fieldMap.put("product_quantity", String.valueOf(quantity));
        if (size != null)
            fieldMap.put("product_size", size);
        if (color != null)
            fieldMap.put("product_color", color);
        fieldMap.put("product_price", price);
        fieldMap.put("product_name", product_name);
        return fieldMap;
    }

    public Map<String, RequestBody> toPartMap() {
        Map<String, RequestBody> partMap = new HashMap<>();
        if (image_path != null)
            partMap.put("product_image", createRequestBodyFromString(image_path));
        partMap.put("product_description", createRequestBodyFromString(description));
        partMap.put("product_quantity", createRequestBodyFromString(String.valueOf(quantity)));
        if (size != null)
            partMap.put("product_size", createRequestBodyFromString(size));
        if (color != null)
            partMap.put("product_color", createRequestBodyFromString(color));
        partMap.put("product_price", createRequestBodyFromString(price));
        partMap.put("product_name", createRequestBodyFromString(product_name));
        return partMap;
    }
}
