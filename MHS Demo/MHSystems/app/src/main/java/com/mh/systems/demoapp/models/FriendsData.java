
package com.mh.systems.demoapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FriendsData {

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
    private com.mh.systems.demoapp.models.NameRecord NameRecord;
    @SerializedName("MembershipStatus")
    @Expose
    private String MembershipStatus;
    @SerializedName("AgeOrAdult")
    @Expose
    private Integer AgeOrAdult;
    @SerializedName("ContactDetails")
    @Expose
    private com.mh.systems.demoapp.models.ContactDetails ContactDetails;
    @SerializedName("HCapTypeStr")
    @Expose
    private String HCapTypeStr;
    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;
    @SerializedName("HCapPlayStr")
    @Expose
    private String HCapPlayStr;
    @SerializedName("LastJoiningDate")
    @Expose
    private String LastJoiningDate;

    /**
     * @return The ClubID
     */
    public Integer getClubID() {
        return ClubID;
    }

    /**
     * @param ClubID The ClubID
     */
    public void setClubID(Integer ClubID) {
        this.ClubID = ClubID;
    }

    /**
     * @return The MemberID
     */
    public Integer getMemberID() {
        return MemberID;
    }

    /**
     * @param MemberID The MemberID
     */
    public void setMemberID(Integer MemberID) {
        this.MemberID = MemberID;
    }

    /**
     * @return The UserLoginID
     */
    public String getUserLoginID() {
        return UserLoginID;
    }

    /**
     * @param UserLoginID The UserLoginID
     */
    public void setUserLoginID(String UserLoginID) {
        this.UserLoginID = UserLoginID;
    }

    /**
     * @return The NameRecord
     */
    public com.mh.systems.demoapp.models.NameRecord getNameRecord() {
        return NameRecord;
    }

    /**
     * @param NameRecord The NameRecord
     */
    public void setNameRecord(com.mh.systems.demoapp.models.NameRecord NameRecord) {
        this.NameRecord = NameRecord;
    }

    /**
     * @return The MembershipStatus
     */
    public String getMembershipStatus() {
        return MembershipStatus;
    }

    /**
     * @param MembershipStatus The MembershipStatus
     */
    public void setMembershipStatus(String MembershipStatus) {
        this.MembershipStatus = MembershipStatus;
    }

    /**
     * @return The AgeOrAdult
     */
    public Integer getAgeOrAdult() {
        return AgeOrAdult;
    }

    /**
     * @param AgeOrAdult The AgeOrAdult
     */
    public void setAgeOrAdult(Integer AgeOrAdult) {
        this.AgeOrAdult = AgeOrAdult;
    }

    /**
     * @return The ContactDetails
     */
    public com.mh.systems.demoapp.models.ContactDetails getContactDetails() {
        return ContactDetails;
    }

    /**
     * @param ContactDetails The ContactDetails
     */
    public void setContactDetails(com.mh.systems.demoapp.models.ContactDetails ContactDetails) {
        this.ContactDetails = ContactDetails;
    }

    /**
     * @return The HCapTypeStr
     */
    public String getHCapTypeStr() {
        return HCapTypeStr;
    }

    /**
     * @param HCapTypeStr The HCapTypeStr
     */
    public void setHCapTypeStr(String HCapTypeStr) {
        this.HCapTypeStr = HCapTypeStr;
    }

    /**
     * @return The HCapExactStr
     */
    public String getHCapExactStr() {
        return HCapExactStr;
    }

    /**
     * @param HCapExactStr The HCapExactStr
     */
    public void setHCapExactStr(String HCapExactStr) {
        this.HCapExactStr = HCapExactStr;
    }

    /**
     * @return The HCapPlayStr
     */
    public String getHCapPlayStr() {
        return HCapPlayStr;
    }

    /**
     * @param HCapPlayStr The HCapPlayStr
     */
    public void setHCapPlayStr(String HCapPlayStr) {
        this.HCapPlayStr = HCapPlayStr;
    }

    /**
     * @return The LastJoiningDate
     */
    public String getLastJoiningDate() {
        return LastJoiningDate;
    }

    /**
     * @param LastJoiningDate The LastJoiningDate
     */
    public void setLastJoiningDate(String LastJoiningDate) {
        this.LastJoiningDate = LastJoiningDate;
    }

}
