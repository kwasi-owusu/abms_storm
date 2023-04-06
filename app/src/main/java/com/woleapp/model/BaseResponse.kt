package com.woleapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @Expose var message: String,
    @Expose var data: T?,
    @Expose @SerializedName("store_exists") var storeExists: Boolean?
)