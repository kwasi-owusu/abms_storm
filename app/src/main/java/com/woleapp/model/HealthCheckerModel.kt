package com.woleapp.model

import com.google.gson.annotations.SerializedName
import timber.log.Timber
import java.lang.StringBuilder

data class HealthCheckerModel(
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    var phone: String? = null,
    var gender: String? = null,
    @SerializedName("DOB")
    var dateOfBirth: String? = null,
    @SerializedName("communication_channel")
    var communicationChannel: String = "SMS",
    @SerializedName("request_type")
    var requestType: String = "Initial",
    @SerializedName("follow_up_token")
    var followUpToken: String? = null
){
    val nullOrEmptyFields: String
        get() {
            val builder = StringBuilder()
            this.javaClass.declaredFields.forEach {
                if ((it.get(this)) == null && it.name != "followUpToken")
                    builder.append("${it.name}, ")
            }
            return builder.toString()
        }
}

data class HealthCheckerResponse(
    var message: String? = null,
    var errors: List<String>? = null
)