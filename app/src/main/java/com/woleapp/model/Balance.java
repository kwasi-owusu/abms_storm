package com.woleapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {
    @SerializedName("balance")
    @Expose
    private String balance;

    @SerializedName("token")
    @Expose
    private String token;

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
