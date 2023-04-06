package com.woleapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.woleapp.util.createRequestBodyFromString
import okhttp3.RequestBody
import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class Marketplace(
    @Expose
    var id: Int,
    @Expose
    @SerializedName("merchant_id")
    var merchantId: String,
    @Expose
    @SerializedName("store_name")
    var name: String,
    @Expose
    @SerializedName("desription")
    var description: String,
    @Expose
    @SerializedName("store_logo")
    var logo: String,
    @Expose
    @SerializedName("product_count")
    val productCount: Int,
    @Expose
    @SerializedName("total_active_product")
    val totalActiveProduct: Int,
    @Expose
    @SerializedName("created_at")
    val createdAt: String,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String,
    @Expose
    @SerializedName("delivery_fee")
    var deliveryFee:String
) {
    constructor() : this(-1, "", "", "", "", 0, 0, "", "","0")

    fun toMap() = HashMap<String, RequestBody>()
        .apply {
            put("merchant_id", createRequestBodyFromString(merchantId))
            put("store_name", createRequestBodyFromString(name))
            put("desription", createRequestBodyFromString(description))
            put("delivery_fee", createRequestBodyFromString(deliveryFee))
        }
}