package com.woleapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dagger.multibindings.IntoMap

data class UtilitiesPayload(
    var destinationAccount: String,
    @Transient
    var stringAmount: String,
    var amount: Float,
    var service: String,
    var provider: String,
    @Transient
    var billType: String,
    @Transient
    var billPackage: String,
    var serviceType: String
) {
    constructor() : this("", "", 0f, "", "", "", "", "")
}

data class ServiceFeeResponse(
    var provider: String,
    var service: String,
    var fees: Int
)

data class ValidateBillResponse(
    @SerializedName("Bill")
    var bill: String? = null,
    @SerializedName("BillStatus")
    var billStatus: String? = null,
    @SerializedName("BillAccountId")
    var billAccountId: String,
    @SerializedName("BillVerifiedOnline")
    var billVerifiedOnline: String? = null,
    @SerializedName("BillAccountIdDetails")
    var billAccountIdDetails: String? = null,
    @Transient
    var billPackage: String? = null,
    @Transient
    var provider: String? = null,
    @Transient
    var fees: String? = null,
    @Transient
    var amount: String? = null,
    var message: String? = null
)

data class PayBillResponse(
    var reference: String,
    var otherReference: String,
    var amount: String,
    var action: String,
    var token: String?

)

sealed class NetworkResponse

data class ErrorNetworkResponse(var message: String) : NetworkResponse()
data class SuccessNetworkResponse(var message: String, var data: PayBillResponse?) :
    NetworkResponse()

data class Transact(var transaction: CashTransaction, var appname: String = "storm_app")

data class CashTransaction(
    var stormId: String,
    var terminalId: String,
    var businessName: String,
    var externalReference: String,
    var ourReference: String,
    var transactionStatus: String,
    var amount: Double,
    var transactionType: String,
    var description: String,
    var accountType: String,
    var appname: String,
    var destinationAccount: String = ""
)

fun CashTransaction.toTransactionJson() = Transact(this)

@Entity(tableName = "merchant_transaction")
data class MerchantTransaction(
    @PrimaryKey @Expose var id: Int,
    @Expose var reference: String,
    @Expose @SerializedName("merchant_id") var merchantId: String,
    @Expose @SerializedName("payment_method") var paymentMethod: String,
    @Expose @SerializedName("cust_name") var customerName: String,
    @Expose var status: String,
    @Expose var amount: String,
    @Expose var productId: String?,
    @Expose var productCount: String?,
    var sellerId: String? = null
) {
    @JvmOverloads
    constructor(
        reference: String,
        merchantId: String,
        paymentMethod: String,
        customerName: String,
        status: String,
        amount: String,
        productId: String,
        productCount: String,
        sellerId: String? = null

    ) : this(
        0,
        reference,
        merchantId,
        paymentMethod,
        customerName,
        status,
        amount,
        productId,
        productCount,
        sellerId
    )
}

fun MerchantTransaction.toFieldMap(): Map<String, String> = HashMap<String, String>()
    .apply {
        put("reference", reference)
        put("merchant_id", merchantId)
        put("payment_method", paymentMethod)
        put("cust_name", customerName)
        put("status", status)
        put("amount", amount)
    }

data class SalesOrder(
    @Expose var reference: String,
    @Expose var status: String,
    @Expose @SerializedName("product_count")
    var productCount: String,
    @Expose @SerializedName("total_cost")
    var totalCost: String,
    @Expose @SerializedName("payment_details")
    var paymentDetails: String,
    @Expose @SerializedName("product_id")
    var productId: String,
    @Expose @SerializedName("seller_id")
    var sellerId: String,
    @Expose(deserialize = true, serialize = false)
    @SerializedName("product_details")
    var productDetails: Inventory? = null,
    @Expose(deserialize = true, serialize = true)
    var id: String? = null,
    @Expose
    @SerializedName("delivery_status")
    var deliveryStatus: String? = null,
    @Expose(deserialize = true, serialize = false)
    @SerializedName("store_name")
    var storeName: String? = null,
    @Expose
    @SerializedName("merchant_id")
    var buyerId: String? = null
)

fun SalesOrder.toFieldMap() = HashMap<String, String>()
    .apply {
        id?.let {
            put("id", it)
        }
        put("reference", reference)
        put("status", status)
        put("product_count", productCount)
        put("total_cost", totalCost)
        put("payment_details", paymentDetails)
        put("product_id", productId)
        put("seller_id", sellerId)
        put("delivery_status", deliveryStatus ?: "pending")
    }

data class SessionCode(@Expose var success: Boolean, @Expose var sessionCode: String)

data class AgentTransactions(
    @Expose var count: Int,
    @Expose var page: Int,
    @Expose var transactions: List<Transactions>
)

data class PaymentResponseFromnNetpluspay(
    var result: String?,
    var code: String?,
    var storm: String?,
    var stormId: String,
    var amount: String,
    var status: String,
    var transactionReference: String,
    var description: String
)

data class MarketplaceOrder(var id: Int)