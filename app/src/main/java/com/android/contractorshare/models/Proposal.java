package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Proposal {

    @SerializedName("Active")
    @Expose
    private Boolean Active;
    @SerializedName("AproxDuration")
    @Expose
    private Double AproxDuration;
    @SerializedName("Created")
    @Expose
    private String Created;
    @SerializedName("FromUserId")
    @Expose
    private Integer FromUserId;
    @SerializedName("FromUsername")
    @Expose
    private String FromUsername;
    @SerializedName("JobId")
    @Expose
    private Integer JobId;
    @SerializedName("JobName")
    @Expose
    private String JobName;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("ProposalId")
    @Expose
    private Integer ProposalId;
    @SerializedName("ProposedPrice")
    @Expose
    private Double ProposedPrice;
    @SerializedName("ProposedTime")
    @Expose
    private String ProposedTime;
    @SerializedName("StatusId")
    @Expose
    private Integer StatusId;
    @SerializedName("ToUserId")
    @Expose
    private Integer ToUserId;
    @SerializedName("UpdatedByUserId")
    @Expose
    private Integer UpdatedByUserId;

    /**
     *
     * @return
     * The Active
     */
    public Boolean getActive() {
        return Active;
    }

    /**
     *
     * @param Active
     * The Active
     */
    public void setActive(Boolean Active) {
        this.Active = Active;
    }

    /**
     *
     * @return
     * The AproxDuration
     */
    public Double getAproxDuration() {
        return AproxDuration;
    }

    /**
     *
     * @param AproxDuration
     * The AproxDuration
     */
    public void setAproxDuration(Double AproxDuration) {
        this.AproxDuration = AproxDuration;
    }

    /**
     *
     * @return
     * The Created
     */
    public String getCreated() {
        return Created;
    }

    /**
     *
     * @param Created
     * The Created
     */
    public void setCreated(String Created) {
        this.Created = Created;
    }

    /**
     *
     * @return
     * The FromUserId
     */
    public Integer getFromUserId() {
        return FromUserId;
    }

    /**
     *
     * @param FromUserId
     * The FromUserId
     */
    public void setFromUserId(Integer FromUserId) {
        this.FromUserId = FromUserId;
    }

    /**
     *
     * @return
     * The FromUsername
     */
    public String getFromUsername() {
        return FromUsername;
    }

    /**
     * @param FromUsername The FromUsername
     */
    public void setFromUsername(String FromUsername) {
        this.FromUsername = FromUsername;
    }

    /**
     * @return The JobId
     */
    public Integer getJobId() {
        return JobId;
    }

    /**
     *
     * @param JobId
     * The JobId
     */
    public void setJobId(Integer JobId) {
        this.JobId = JobId;
    }

    /**
     *
     * @return
     * The JobName
     */
    public String getJobName() {
        return JobName;
    }

    /**
     * @param JobName The JobName
     */
    public void setJobName(String JobName) {
        this.JobName = JobName;
    }

    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     *
     * @param Message
     * The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     *
     * @return
     * The ProposalId
     */
    public Integer getProposalId() {
        return ProposalId;
    }

    /**
     *
     * @param ProposalId
     * The ProposalId
     */
    public void setProposalId(Integer ProposalId) {
        this.ProposalId = ProposalId;
    }

    /**
     *
     * @return
     * The ProposedPrice
     */
    public Double getProposedPrice() {
        return ProposedPrice;
    }

    /**
     *
     * @param ProposedPrice
     * The ProposedPrice
     */
    public void setProposedPrice(Double ProposedPrice) {
        this.ProposedPrice = ProposedPrice;
    }

    /**
     *
     * @return
     * The ProposedTime
     */
    public String getProposedTime() {
        return ProposedTime;
    }

    /**
     *
     * @param ProposedTime
     * The ProposedTime
     */
    public void setProposedTime(String ProposedTime) {
        this.ProposedTime = ProposedTime;
    }

    /**
     *
     * @return
     * The StatusId
     */
    public Integer getStatusId() {
        return StatusId;
    }

    /**
     *
     * @param StatusId
     * The StatusId
     */
    public void setStatusId(Integer StatusId) {
        this.StatusId = StatusId;
    }

    /**
     *
     * @return
     * The ToUserId
     */
    public Integer getToUserId() {
        return ToUserId;
    }

    /**
     *
     * @param ToUserId
     * The ToUserId
     */
    public void setToUserId(Integer ToUserId) {
        this.ToUserId = ToUserId;
    }

    /**
     *
     * @return
     * The UpdatedByUserId
     */
    public Integer getUpdatedByUserId() {
        return UpdatedByUserId;
    }

    /**
     *
     * @param UpdatedByUserId
     * The UpdatedByUserId
     */
    public void setUpdatedByUserId(Integer UpdatedByUserId) {
        this.UpdatedByUserId = UpdatedByUserId;
    }

}


