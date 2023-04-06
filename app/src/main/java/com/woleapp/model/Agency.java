package com.woleapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Agency {

    @SerializedName("agencyCode")
    @Expose
    private String agencyCode;

    @SerializedName("agencyName")
    @Expose
    private String agencyName;

    @SerializedName("agencyStatus")
    @Expose
    private String agencyStatus;

    @SerializedName("agentKey")
    @Expose
    private String agentKey;

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyName (String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyName () {
        return agencyName;
    }

    public void setAgencyStatus (String agencyStatus) {
        this.agencyStatus = agencyStatus;
    }

    public String getAgencyStatus () {
        return agencyStatus;
    }

    public void setAgentKey(String agentKey) {
        this.agentKey = agentKey;
    }

    public String getAgentKey() {
        return agentKey;
    }
}
