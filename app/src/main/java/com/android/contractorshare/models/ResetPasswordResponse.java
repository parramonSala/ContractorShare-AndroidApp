package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordResponse {

    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Result")
    @Expose
    private String Result;

    /**
     * @return The Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email The Email
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return The Result
     */
    public String getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(String Result) {
        this.Result = Result;
    }

}