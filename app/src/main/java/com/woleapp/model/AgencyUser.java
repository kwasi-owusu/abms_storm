package com.woleapp.model;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "agency_users")

public class AgencyUser {
    @SerializedName("merchID")
    @Expose
    private String merchID;

    @SerializedName("merchantName")
    @Expose
    private String merchantName;

    @SerializedName("apiKey")
    @Expose
    private String apiKey;

    @SerializedName("merchToken")
    @Expose
    private String merchToken;
    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("user_level")
    @Expose
    private Integer user_level;

    @SerializedName("user_branch")
    @Expose
    private Integer user_branch;

    @SerializedName("agency_id")
    @Expose
    private Integer agency_id;

    @SerializedName("officerID")
    @Expose
    private String officerID;

    @SerializedName("availableBalance")
    @Expose
    private String availableBalance;

    @SerializedName("token")
    @Expose
    private String token;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserLevel(Integer user_level) {
        this.user_level = user_level;
    }

    public Integer getUser_level() {
        return user_level;
    }

    public void setUserBranch(Integer user_branch) {
        this.user_branch = user_branch;
    }

    public Integer getUserBranch() {
        return user_branch;
    }

    public void setAgencyID(Integer agency_id) {
        this.agency_id = agency_id;
    }

    public Integer getAgencyID() {
        return agency_id;
    }

    public void setOfficerID(String officerID) {
        this.officerID = officerID;
    }

    public String getOfficerID() {
        return officerID;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getMerchToken() {
        return merchToken;
    }

    public void setMerchToken(String merchToken) {
        this.merchToken = merchToken;
    }
    public String getMerchID(){
        return merchID;
    }
    public void setMerchID(String merchID){
        this.merchID = merchID;
    }
    public String getApiKey(){
        return apiKey;
    }
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
