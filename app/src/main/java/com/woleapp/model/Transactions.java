package com.woleapp.model;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "transactions", indices = {@Index(value = {"external_reference", "our_reference"}, unique = false)})
public class Transactions {

    @PrimaryKey(autoGenerate = true)
    private int tId;

    @SerializedName(value = "user_id", alternate = {"stormId"})
    @Expose
    private String id;

    @ColumnInfo(name = "external_reference")
    @SerializedName("externalReference")
    @Expose
    private String transaction_id;

    @ColumnInfo(name = "our_reference")
    @SerializedName(value = "reference_no", alternate = {"ourReference"})
    @Expose
    private String reference_no_Etranzact;

    @SerializedName(value = "transaction_type", alternate = {"transactionType"})
    @Expose
    private String transaction_type;

    @SerializedName(value = "transactionTime", alternate = {"dateCreated"})
    @Expose
    private String transaction_date;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName(value = "beneficiary_name", alternate = {"beneficiaryName"})
    @Expose
    private String beneficiary_name;

    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName(value = "status", alternate = {"statusDescription", "transactionStatus"})
    @Expose
    private String status;

    @SerializedName("destinationAccount")
    @Expose
    private String destination_account;

    public int getTId() {
        return tId;
    }

    public void setTId(int tId) {
        this.tId = tId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransaction_id() {
        return TextUtils.isEmpty(transaction_id) ? "N.A." : transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getReference_no_Etranzact() {
        //return reference_no_Etranzact;
        return TextUtils.isEmpty(reference_no_Etranzact) ? getTransaction_id() : reference_no_Etranzact;
    }

    public void setReference_no_Etranzact(String reference_no_Etranzact) {
        this.reference_no_Etranzact = reference_no_Etranzact;
    }

    public String getTransaction_type() {
        return TextUtils.isEmpty(transaction_type) ? "N.A." : transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_date() {
        return TextUtils.isEmpty(transaction_date) ? "" : transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeneficiary_name() {
        return TextUtils.isEmpty(beneficiary_name) ? "N.A." : beneficiary_name;
    }

    public void setBeneficiary_name(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }

    public Double getAmount() {
        return amount == null ? 0.0 : amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return TextUtils.isEmpty(status) ? "N.A." : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDestination_account() {
        return TextUtils.isEmpty(destination_account) ? "N.A." : destination_account;
    }

    public void setDestination_account(String destination_account) {
        this.destination_account = destination_account;
    }

}
