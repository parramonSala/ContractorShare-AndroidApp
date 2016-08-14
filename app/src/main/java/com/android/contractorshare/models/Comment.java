package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("CommentId")
    @Expose
    private Integer commentId;
    @SerializedName("Created")
    @Expose
    private String created;
    @SerializedName("CreatedByUserId")
    @Expose
    private Integer createdByUserId;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("JobId")
    @Expose
    private Integer jobId;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("TaskId")
    @Expose
    private Integer taskId;
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * @return The commentId
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * @param commentId The CommentId
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

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
     * @return The createdByUserId
     */
    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    /**
     * @param createdByUserId The CreatedByUserId
     */
    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The Image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The jobId
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * @param jobId The JobId
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The Message
     */
    public void setMessage(String message) {
        this.message = message;
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

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}