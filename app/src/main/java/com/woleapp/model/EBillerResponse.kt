package com.woleapp.model

import com.google.gson.annotations.SerializedName

data class EBillerResponse(
    val billerId: String,
    val fields: Fields,
    val formId: String,
    val productId: String,
    val step: String,
    val user: String
)

data class Fields(
    val BillofLadingNumber: String? = null,
    val InvoiceDate: String? = null,
    @SerializedName("CustomerCode")
    val customer: String? = null,
    val customerCode: String? = null,
    val AgentId: String? = null,
    val Amount: String? = null
)