package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("UserId")
    @Expose
    private Integer UserId;
    @SerializedName("UserType")
    @Expose
    private Integer UserType;
    @SerializedName("error")
    @Expose
    private String error;

    /**
     * @return The UserId
     */
    public Integer getUserId() {
        return UserId;
    }

    /**
     * @param UserId The UserId
     */
    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    /**
     * @return The UserType
     */
    public Integer getUserType() {
        return UserType;
    }

    /**
     * @param UserType The UserType
     */
    public void setUserType(Integer UserType) {
        this.UserType = UserType;
    }

    /**
     * @return The error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(String error) {
        this.error = error;
    }
}