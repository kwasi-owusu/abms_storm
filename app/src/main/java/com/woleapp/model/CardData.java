package com.woleapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardData {
    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("expiry")
    @Expose
    private String expiry;

    @SerializedName("cvv")
    @Expose
    private String cvv;

    @SerializedName("card_expiry_date")
    @Expose
    private String cardExpiryDate;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public void setCardNumber(String number) {
        this.number = number;
    }

    public String getCardNumber() {
        return number;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getExpiry() {
        return expiry;
    }
}
