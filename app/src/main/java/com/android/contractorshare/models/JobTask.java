package com.android.contractorshare.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JobTask implements Parcelable {

    @SerializedName("Created")
    @Expose
    private String created;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ServiceId")
    @Expose
    private Integer serviceId;
    @SerializedName("StatusId")
    @Expose
    private Integer statusId;
    @SerializedName("TaskId")
    @Expose
    private Integer taskId;

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The Created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The serviceId
     */
    public Integer getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId The ServiceId
     */
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return The statusId
     */
    public Integer getStatusId() {
        return statusId;
    }

    /**
     * @param statusId The StatusId
     */
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    /**
     * @return The taskId
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @param taskId The TaskId
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
