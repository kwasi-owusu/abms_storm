package com.woleapp.model;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "agency-banks")

public class BankList {
    @SerializedName("partner_ID")
    @Expose
    private String partner_ID;

    @SerializedName("partner_name")
    @Expose
    private String partner_name;

    @SerializedName("short_name")
    @Expose
    private String short_name;

    @SerializedName("partner_code")
    @Expose
    private String partner_code;

    public BankList(String partner_ID, String partner_name, String short_name, String partner_code) {
        this.partner_ID = partner_ID;
        this.partner_name = partner_name;
        this.short_name = short_name;
       // this.partner_code = partner_code;
    }

    public void setPartnerID(String partner_ID) {
        this.partner_ID = partner_ID;
    }

    public String getPartnerID() {
        return partner_ID;
    }

    public void setPartnerName(String partner_name) { this.partner_name = partner_name; }

    public String getPartnerName() { return partner_name; }

    public void setShortName(String short_name) { this.short_name = short_name; }

    public String getShortName() { return short_name; }

    public void setPartnerCode(String partner_code) { this.partner_code = partner_code; }

    public String getPartnerCode() { return partner_code; }
}
