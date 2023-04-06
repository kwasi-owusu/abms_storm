package com.woleapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Branch {

    @SerializedName("branchCode")
    @Expose
    private String branchCode;

    @SerializedName("branchName")
    @Expose
    private String branchName;

    @SerializedName("branchStatus")
    @Expose
    private String branchStatus;

    @SerializedName("branchKey")
    @Expose
    private String branchKey;

    @SerializedName("branch_ID")
    @Expose
    private String branch_ID;

    public void setBranchKey(String branchKey) {
        this.branchKey = branchKey;
    }

    public String getBranchKey() {
        return branchKey;
    }

    public void setBranchCode (String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchName (String branchName) {
        this.branchName = branchName;
    }

    public String getBranchName () {
        return branchName;
    }

    public void setBranchStatus(String branchStatus) {
        this.branchStatus = branchStatus;
    }

    public String getBranchStatus () {
        return branchStatus;
    }

}
