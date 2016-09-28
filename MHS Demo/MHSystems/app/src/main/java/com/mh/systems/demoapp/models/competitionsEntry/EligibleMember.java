
package com.mh.systems.demoapp.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EligibleMember implements Serializable {

    @SerializedName("ClubID")
    @Expose
    private Integer ClubID;
    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("UserLoginID")
    @Expose
    private String UserLoginID;
    @SerializedName("IsReadOnly")
    @Expose
    private Boolean IsReadOnly;
    @SerializedName("IsRegistered")
    @Expose
    private Boolean IsRegistered;
    @SerializedName("DisplayName")
    @Expose
    private String DisplayName;
    @SerializedName("FullName")
    @Expose
    private String FullName;
    @SerializedName("PlayHCapStr")
    @Expose
    private String PlayHCapStr;
    @SerializedName("Gender")
    @Expose
    private Integer Gender;
    @SerializedName("GenderStr")
    @Expose
    private String GenderStr;
    @SerializedName("HCapTypeStr")
    @Expose
    private String HCapTypeStr;

    boolean isMemberSelected;

    public EligibleMember(int MemberID, String FullName ){
        this.MemberID = MemberID;
        this.FullName = FullName;
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
    public void setClubID(Integer clubID) {
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
    public void setMemberID(Integer memberID) {
        this.MemberID = MemberID;
    }

    /**
     * 
     * @return
     *     The UserLoginID
     */
    public String getUserLoginID() {
        return UserLoginID;
    }

    /**
     * 
     * @param UserLoginID
     *     The UserLoginID
     */
    public void setUserLoginID(String UserLoginID) {
        this.UserLoginID = UserLoginID;
    }

    /**
     * 
     * @return
     *     The IsReadOnly
     */
    public Boolean getIsReadOnly() {
        return IsReadOnly;
    }

    /**
     * 
     * @param IsReadOnly
     *     The IsReadOnly
     */
    public void setIsReadOnly(Boolean IsReadOnly) {
        this.IsReadOnly = IsReadOnly;
    }

    /**
     * 
     * @return
     *     The IsRegistered
     */
    public Boolean getIsRegistered() {
        return IsRegistered;
    }

    /**
     * 
     * @param IsRegistered
     *     The IsRegistered
     */
    public void setIsRegistered(Boolean IsRegistered) {
        this.IsRegistered = IsRegistered;
    }

    /**
     * 
     * @return
     *     The DisplayName
     */
    public String getDisplayName() {
        return DisplayName;
    }

    /**
     * 
     * @param DisplayName
     *     The DisplayName
     */
    public void setDisplayName(String displayName) {
        this.DisplayName = DisplayName;
    }

    /**
     * 
     * @return
     *     The FullName
     */
    public String getFullName() {
        return FullName;
    }

    /**
     * 
     * @param FullName
     *     The FullName
     */
    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    /**
     * 
     * @return
     *     The PlayHCapStr
     */
    public String getPlayHCapStr() {
        return PlayHCapStr;
    }

    /**
     * 
     * @param PlayHCapStr
     *     The PlayHCapStr
     */
    public void setPlayHCapStr(String PlayHCapStr) {
        this.PlayHCapStr = PlayHCapStr;
    }

    /**
     * 
     * @return
     *     The Gender
     */
    public Integer getGender() {
        return Gender;
    }

    /**
     * 
     * @param Gender
     *     The Gender
     */
    public void setGender(Integer gender) {
        this.Gender = Gender;
    }

    /**
     * 
     * @return
     *     The GenderStr
     */
    public String getGenderStr() {
        return GenderStr;
    }

    /**
     * 
     * @param GenderStr
     *     The GenderStr
     */
    public void setGenderStr(String genderStr) {
        this.GenderStr = GenderStr;
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
     *     The isMemberSelected
     */
    public boolean getIsMemberSelected() {
        return isMemberSelected;
    }

    /**
     *
     * @param isMemberSelected
     *     The isMemberSelected
     */
    public void setIsMemberSelected(boolean isMemberSelected) {
        this.isMemberSelected = isMemberSelected;
    }
}
