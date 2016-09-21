package com.mh.systems.demoapp.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Players {

    @SerializedName("PlayerNo")
    @Expose
    private Integer PlayerNo;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("SortName")
    @Expose
    private String sortName;
    @SerializedName("HCapStr")
    @Expose
    private String hCapStr;
    @SerializedName("IsPayee")
    @Expose
    private Boolean isPayee;
    @SerializedName("RecordID")
    @Expose
    private Integer recordID;

    /**
     *
     * @return
     * The PlayerNo
     */
    public Integer getPlayerNo() {
        return PlayerNo;
    }

    /**
     *
     * @param PlayerNo
     * The PlayerNo
     */
    public void setPlayerNo(Integer PlayerNo) {
        this.PlayerNo = PlayerNo;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The sortName
     */
    public String getSortName() {
        return sortName;
    }

    /**
     *
     * @param sortName
     * The SortName
     */
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     *
     * @return
     * The hCapStr
     */
    public String getHCapStr() {
        return hCapStr;
    }

    /**
     *
     * @param hCapStr
     * The HCapStr
     */
    public void setHCapStr(String hCapStr) {
        this.hCapStr = hCapStr;
    }

    /**
     *
     * @return
     * The isPayee
     */
    public Boolean getIsPayee() {
        return isPayee;
    }

    /**
     *
     * @param isPayee
     * The IsPayee
     */
    public void setIsPayee(Boolean isPayee) {
        this.isPayee = isPayee;
    }

    /**
     *
     * @return
     * The recordID
     */
    public Integer getRecordID() {
        return recordID;
    }

    /**
     *
     * @param recordID
     * The RecordID
     */
    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

}