package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Message {

    @SerializedName("Created")
    @Expose
    private String Created;
    @SerializedName("FromUserId")
    @Expose
    private Integer FromUserId;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("MessageId")
    @Expose
    private Integer MessageId;
    @SerializedName("ProposalId")
    @Expose
    private Integer ProposalId;
    @SerializedName("ToUserId")
    @Expose
    private Integer ToUserId;

    /**
     * @return The Created
     */
    public String getCreated() {
        return Created;
    }

    /**
     * @param Created The Created
     */
    public void setCreated(String Created) {
        this.Created = Created;
    }

    /**
     * @return The FromUserId
     */
    public Integer getFromUserId() {
        return FromUserId;
    }

    /**
     * @param FromUserId The FromUserId
     */
    public void setFromUserId(Integer FromUserId) {
        this.FromUserId = FromUserId;
    }

    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return The MessageId
     */
    public Integer getMessageId() {
        return MessageId;
    }

    /**
     * @param MessageId The MessageId
     */
    public void setMessageId(Integer MessageId) {
        this.MessageId = MessageId;
    }

    /**
     * @return The ProposalId
     */
    public Integer getProposalId() {
        return ProposalId;
    }

    /**
     * @param ProposalId The ProposalId
     */
    public void setProposalId(Integer ProposalId) {
        this.ProposalId = ProposalId;
    }

    /**
     * @return The ToUserId
     */
    public Integer getToUserId() {
        return ToUserId;
    }

    /**
     * @param ToUserId The ToUserId
     */
    public void setToUserId(Integer ToUserId) {
        this.ToUserId = ToUserId;
    }

}
