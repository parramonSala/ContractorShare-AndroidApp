package com.android.contractorshare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("CategoryID")
    @Expose
    private Integer CategoryID;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("ClientID")
    @Expose
    private Integer ClientID;
    @SerializedName("ContractorID")
    @Expose
    private Integer ContractorID;
    @SerializedName("CoordX")
    @Expose
    private Double CoordX;
    @SerializedName("CoordY")
    @Expose
    private Double CoordY;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("PostalCode")
    @Expose
    private String PostalCode;
    @SerializedName("PostedDate")
    @Expose
    private String PostedDate;
    @SerializedName("StatusID")
    @Expose
    private Integer StatusID;

    /**
     * @return The Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address The Address
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * @return The CategoryID
     */
    public Integer getCategoryID() {
        return CategoryID;
    }

    /**
     * @param CategoryID The CategoryID
     */
    public void setCategoryID(Integer CategoryID) {
        this.CategoryID = CategoryID;
    }

    /**
     * @return The City
     */
    public String getCity() {
        return City;
    }

    /**
     * @param City The City
     */
    public void setCity(String City) {
        this.City = City;
    }

    /**
     * @return The ClientID
     */
    public Integer getClientID() {
        return ClientID;
    }

    /**
     * @param ClientID The ClientID
     */
    public void setClientID(Integer ClientID) {
        this.ClientID = ClientID;
    }

    /**
     * @return The ContractorID
     */
    public Integer getContractorID() {
        return ContractorID;
    }

    /**
     * @param ContractorID The ContractorID
     */
    public void setContractorID(Integer ContractorID) {
        this.ContractorID = ContractorID;
    }

    /**
     * @return The CoordX
     */
    public Double getCoordX() {
        return CoordX;
    }

    /**
     * @param CoordX The CoordX
     */
    public void setCoordX(Double CoordX) {
        this.CoordX = CoordX;
    }

    /**
     * @return The CoordY
     */
    public Double getCoordY() {
        return CoordY;
    }

    /**
     * @param CoordY The CoordY
     */
    public void setCoordY(Double CoordY) {
        this.CoordY = CoordY;
    }

    /**
     * @return The Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     * @param Country The Country
     */
    public void setCountry(String Country) {
        this.Country = Country;
    }

    /**
     * @return The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return The Id
     */
    public Integer getId() {
        return Id;
    }

    /**
     * @param Id The Id
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * @return The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return The PostalCode
     */
    public String getPostalCode() {
        return PostalCode;
    }

    /**
     * @param PostalCode The PostalCode
     */
    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    /**
     * @return The PostedDate
     */
    public String getPostedDate() {
        return PostedDate;
    }

    /**
     * @param PostedDate The PostedDate
     */
    public void setPostedDate(String PostedDate) {
        this.PostedDate = PostedDate;
    }

    /**
     * @return The StatusID
     */
    public Integer getStatusID() {
        return StatusID;
    }

    /**
     * @param StatusID The StatusID
     */
    public void setStatusID(Integer StatusID) {
        this.StatusID = StatusID;
    }

}