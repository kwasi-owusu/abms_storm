package com.woleapp.util

import com.auth0.android.jwt.JWT
import java.util.*

object JWTHelper {
    @JvmStatic
    fun getStormId(token: String?): String? {
        val jwt = JWT(token!!)
        return if (!jwt.claims.containsKey("stormId")) null else JWT(token).getClaim("stormId")
            .asString()
    }

    @JvmStatic
    fun isAgent(token: String?): Boolean? {
        val jwt = JWT(token!!)
        return if (!jwt.claims.containsKey("isAgent")) false else JWT(token).getClaim("isAgent")
            .asBoolean()
    }

    @JvmStatic
    fun isMerchant(token: String?): Boolean? {
        val jwt = JWT(token!!)
        return if (!jwt.claims.containsKey("isMerchant")) false else JWT(token).getClaim("isMerchant")
            .asBoolean()
    }

    @JvmStatic
    fun isExpired(token: String?): Boolean {
        val jwt = JWT(token!!)
        return jwt.expiresAt!!.time < Date().time
    }
}