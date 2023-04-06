package com.woleapp.model;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "agency-transactions")

public class AgencyTransactions {

    @SerializedName("transID")
    @Expose
    private String transID;

    @SerializedName("transCat")
    @Expose
    private String transCat;

    @SerializedName("productCat")
    @Expose
    private String productCat;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("branchName")
    @Expose
    private String branchName;

    @SerializedName("officer")
    @Expose
    private String officer;

    @SerializedName("transactionAmount")
    @Expose
    private String transactionAmount;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;

    @SerializedName("idType")
    @Expose
    private String idType;

    @SerializedName("idNumber")
    @Expose
    private String idNumber;

    @SerializedName("transDate")
    @Expose
    private String transDate;

    @SerializedName("depositorPayee")
    @Expose
    private String depositorPayee;

    @SerializedName("agencyName")
    @Expose
    private String agencyName;

    public AgencyTransactions(String transID, String transCat, String productCat, String productName, String branchName, String officer, String transactionAmount, String customerName, String accountNumber, String idType, String idNumber, String transDate, String depositorPayee, String agencyName) {
        this.transID = transID;
        this.transCat = transCat;
        this.productCat = productCat;
        this.productName = productName;
        this.branchName = branchName;
        this.officer = officer;
        this.transactionAmount = transactionAmount;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.idType = idType;
        this.idNumber = idNumber;
        this.transDate = transDate;
        this.depositorPayee = depositorPayee;
        this.agencyName = agencyName;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransCat(String transCat) {
        this.transCat = transCat;
    }

    public String getTransCat() {
        return transCat;
    }

    public void setProductCat(String productCat) {
        this.productCat = productCat;
    }

    public String getProductCat() {
        return productCat;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getOfficer() {
        return officer;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setDepositorPayee(String depositorPayee) {
        this.depositorPayee = depositorPayee;
    }

    public String getDepositorPayee() {
        return depositorPayee;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyName() {
        return agencyName;
    }
}
