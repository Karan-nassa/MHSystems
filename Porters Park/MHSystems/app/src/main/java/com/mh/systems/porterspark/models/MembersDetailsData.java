
package com.mh.systems.porterspark.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MembersDetailsData {

    @SerializedName("ClubID")
    @Expose
    private Integer ClubID;
    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("UserLoginID")
    @Expose
    private String UserLoginID;
    @SerializedName("NameRecord")
    @Expose
    private NameRecord NameRecord;
    @SerializedName("MembershipStatus")
    @Expose
    private String MembershipStatus;
    @SerializedName("AgeOrAdult")
    @Expose
    private Integer AgeOrAdult;
    @SerializedName("ContactDetails")
    @Expose
    private ContactDetails ContactDetails;
    @SerializedName("HCapTypeStr")
    @Expose
    private String HCapTypeStr;
    @SerializedName("HCapType")
    @Expose
    private Integer HCapType;
    @SerializedName("HCapCat")
    @Expose
    private Integer HCapCat;
    @SerializedName("HCapCatStr")
    @Expose
    private String HCapCatStr;
    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;
    @SerializedName("HCapPlay")
    @Expose
    private Integer HCapPlay;
    @SerializedName("HCapPlayStr")
    @Expose
    private String HCapPlayStr;
    @SerializedName("HCapStatus")
    @Expose
    private String HCapStatus;
    @SerializedName("HCapClub")
    @Expose
    private String HCapClub;
    @SerializedName("strLastJoiningDate")
    @Expose
    private String strLastJoiningDate;
    @SerializedName("HCapList")
    @Expose
    private List<Object> HCapList = new ArrayList<Object>();
    @SerializedName("Isfriend")
    @Expose
    private boolean Isfriend;

    /**
     *
     * @return
     *     The userLoginID
     */
    public String getUserLoginID() {
        return UserLoginID;
    }

    /**
     *
     * @param userLoginID
     *     The userLoginID
     */
    public void setUserLoginID(String userLoginID) {
        UserLoginID = userLoginID;
    }

    /**
     *
     * @return
     *     The strLastJoiningDate
     */
    public String getStrLastJoiningDate() {
        return strLastJoiningDate;
    }

    /**
     *
     * @param strLastJoiningDate
     *     The strLastJoiningDate
     */
    public void setStrLastJoiningDate(String strLastJoiningDate) {
        strLastJoiningDate = strLastJoiningDate;
    }

    /**
     * 
     * @return
     *     The ClubID
     */
    public Integer getClubID() {
        return ClubID;
    }

    /**
     * 
     * @param ClubID
     *     The ClubID
     */
    public void setClubID(Integer ClubID) {
        this.ClubID = ClubID;
    }

    /**
     * 
     * @return
     *     The MemberID
     */
    public Integer getMemberID() {
        return MemberID;
    }

    /**
     * 
     * @param MemberID
     *     The MemberID
     */
    public void setMemberID(Integer MemberID) {
        this.MemberID = MemberID;
    }

    /**
     * 
     * @return
     *     The NameRecord
     */
    public NameRecord getNameRecord() {
        return NameRecord;
    }

    /**
     * 
     * @param NameRecord
     *     The NameRecord
     */
    public void setNameRecord(NameRecord NameRecord) {
        this.NameRecord = NameRecord;
    }

    /**
     * 
     * @return
     *     The MembershipStatus
     */
    public String getMembershipStatus() {
        return MembershipStatus;
    }

    /**
     * 
     * @param MembershipStatus
     *     The MembershipStatus
     */
    public void setMembershipStatus(String MembershipStatus) {
        this.MembershipStatus = MembershipStatus;
    }

    /**
     * 
     * @return
     *     The AgeOrAdult
     */
    public Integer getAgeOrAdult() {
        return AgeOrAdult;
    }

    /**
     * 
     * @param AgeOrAdult
     *     The AgeOrAdult
     */
    public void setAgeOrAdult(Integer AgeOrAdult) {
        this.AgeOrAdult = AgeOrAdult;
    }

    /**
     * 
     * @return
     *     The ContactDetails
     */
    public ContactDetails getContactDetails() {
        return ContactDetails;
    }

    /**
     * 
     * @param ContactDetails
     *     The ContactDetails
     */
    public void setContactDetails(ContactDetails ContactDetails) {
        this.ContactDetails = ContactDetails;
    }

    /**
     * 
     * @return
     *     The HCapTypeStr
     */
    public String getHCapTypeStr() {
        return HCapTypeStr;
    }

    /**
     * 
     * @param HCapTypeStr
     *     The HCapTypeStr
     */
    public void setHCapTypeStr(String HCapTypeStr) {
        this.HCapTypeStr = HCapTypeStr;
    }

    /**
     * 
     * @return
     *     The HCapType
     */
    public Integer getHCapType() {
        return HCapType;
    }

    /**
     * 
     * @param HCapType
     *     The HCapType
     */
    public void setHCapType(Integer HCapType) {
        this.HCapType = HCapType;
    }

    /**
     * 
     * @return
     *     The HCapCat
     */
    public Integer getHCapCat() {
        return HCapCat;
    }

    /**
     * 
     * @param HCapCat
     *     The HCapCat
     */
    public void setHCapCat(Integer HCapCat) {
        this.HCapCat = HCapCat;
    }

    /**
     * 
     * @return
     *     The HCapCatStr
     */
    public String getHCapCatStr() {
        return HCapCatStr;
    }

    /**
     * 
     * @param HCapCatStr
     *     The HCapCatStr
     */
    public void setHCapCatStr(String HCapCatStr) {
        this.HCapCatStr = HCapCatStr;
    }

    /**
     * 
     * @return
     *     The HCapExactStr
     */
    public String getHCapExactStr() {
        return HCapExactStr;
    }

    /**
     * 
     * @param HCapExactStr
     *     The HCapExactStr
     */
    public void setHCapExactStr(String HCapExactStr) {
        this.HCapExactStr = HCapExactStr;
    }

    /**
     * 
     * @return
     *     The HCapPlay
     */
    public Integer getHCapPlay() {
        return HCapPlay;
    }

    /**
     * 
     * @param HCapPlay
     *     The HCapPlay
     */
    public void setHCapPlay(Integer HCapPlay) {
        this.HCapPlay = HCapPlay;
    }

    /**
     * 
     * @return
     *     The HCapPlayStr
     */
    public String getHCapPlayStr() {
        return HCapPlayStr;
    }

    /**
     * 
     * @param HCapPlayStr
     *     The HCapPlayStr
     */
    public void setHCapPlayStr(String HCapPlayStr) {
        this.HCapPlayStr = HCapPlayStr;
    }

    /**
     * 
     * @return
     *     The HCapStatus
     */
    public String getHCapStatus() {
        return HCapStatus;
    }

    /**
     * 
     * @param HCapStatus
     *     The HCapStatus
     */
    public void setHCapStatus(String HCapStatus) {
        this.HCapStatus = HCapStatus;
    }

    /**
     * 
     * @return
     *     The HCapClub
     */
    public String getHCapClub() {
        return HCapClub;
    }

    /**
     * 
     * @param HCapClub
     *     The HCapClub
     */
    public void setHCapClub(String HCapClub) {
        this.HCapClub = HCapClub;
    }

    /**
     * 
     * @return
     *     The HCapList
     */
    public List<Object> getHCapList() {
        return HCapList;
    }

    /**
     * 
     * @param HCapList
     *     The HCapList
     */
    public void setHCapList(List<Object> HCapList) {
        this.HCapList = HCapList;
    }

    /**
     *
     * @return
     * The Isfriend
     */
    public Boolean getIsfriend() {
        return Isfriend;
    }

    /**
     *
     * @param Isfriend
     * The Isfriend
     */
    public void setIsfriend(Boolean Isfriend) {
        this.Isfriend = Isfriend;
    }
}
