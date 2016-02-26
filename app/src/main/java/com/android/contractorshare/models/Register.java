package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("UserType")
    @Expose
    private Integer UserType;

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
     * @return The Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password The Password
     */
    public void setPassword(String Password) {
        this.Password = Password;
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

}
