package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email {

    @SerializedName("Email")
    @Expose
    private String Email;

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

}
