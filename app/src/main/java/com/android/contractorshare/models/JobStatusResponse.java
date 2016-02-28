package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobStatusResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private Integer resultCode;

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The resultCode
     */
    public Integer getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode The resultCode
     */
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

}