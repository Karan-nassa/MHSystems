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
    private String SortName;
    @SerializedName("HCapStr")
    @Expose
    private String HCapStr;
    @SerializedName("IsPayee")
    @Expose
    private Boolean IsPayee;
    @SerializedName("RecordID")
    @Expose
    private Integer RecordID;

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
     * The SortName
     */
    public String getSortName() {
        return SortName;
    }

    /**
     *
     * @param SortName
     * The SortName
     */
    public void setSortName(String SortName) {
        this.SortName = SortName;
    }

    /**
     *
     * @return
     * The HCapStr
     */
    public String getHCapStr() {
        return HCapStr;
    }

    /**
     *
     * @param HCapStr
     * The HCapStr
     */
    public void setHCapStr(String hCapStr) {
        this.HCapStr = HCapStr;
    }

    /**
     *
     * @return
     * The IsPayee
     */
    public Boolean getIsPayee() {
        return IsPayee;
    }

    /**
     *
     * @param IsPayee
     * The IsPayee
     */
    public void setIsPayee(Boolean isPayee) {
        this.IsPayee = IsPayee;
    }

    /**
     *
     * @return
     * The RecordID
     */
    public Integer getRecordID() {
        return RecordID;
    }

    /**
     *
     * @param RecordID
     * The RecordID
     */
    public void setRecordID(Integer recordID) {
        this.RecordID = RecordID;
    }

}