
package com.mh.systems.chesterLeStreet.web.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginData {

    @SerializedName("ClubID")
    @Expose
    private Integer ClubID;
    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("UserLoginID")
    @Expose
    private String UserLoginID;
    @SerializedName("HCapTypeStr")
    @Expose
    private String HCapTypeStr;
    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;
    @SerializedName("FirstTimeLogin")
    @Expose
    private boolean FirstTimeLogin;

    /**
     * For make dashboard dynamically.
     */
    @SerializedName("CourseDiaryFeatures")
    @Expose
    private boolean CourseDiaryFeatures;
    @SerializedName("CompetitionsFeature")
    @Expose
    private boolean CompetitionsFeature;
    @SerializedName("HandicapFeature")
    @Expose
    private boolean HandicapFeature;
    @SerializedName("ClubNewsFeature")
    @Expose
    private boolean ClubNewsFeature;
    @SerializedName("MembersFeature")
    @Expose
    private boolean MembersFeature;
    @SerializedName("YourAccountFeature")
    @Expose
    private boolean YourAccountFeature;

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
     * @return The FirstTimeLogin
     */
    public boolean getFirstTimeLogin() {
        return FirstTimeLogin;
    }

    /**
     * @param FirstTimeLogin The FirstTimeLogin
     */
    public void setFirstTimeLogin(boolean firstTimeLogin) {
        FirstTimeLogin = firstTimeLogin;
    }

    /**
     * @return The HandicapFeature
     */
    public boolean isHandicapFeature() {
        return HandicapFeature;
    }

    /**
     * @param HandicapFeature The HandicapFeature
     */
    public void setHandicapFeature(boolean HandicapFeature) {
        this.HandicapFeature = HandicapFeature;
    }

    /**
     * @return The CourseDiaryFeatures
     */
    public boolean isCourseDiaryFeatures() {
        return CourseDiaryFeatures;
    }

    /**
     * @param CourseDiaryFeatures The CourseDiaryFeatures
     */
    public void setCourseDiaryFeatures(boolean CourseDiaryFeatures) {
        this.CourseDiaryFeatures = CourseDiaryFeatures;
    }

    /**
     * @return The CompetitionsFeature
     */
    public boolean isCompetitionsFeature() {
        return CompetitionsFeature;
    }

    /**
     * @param CompetitionsFeature The CompetitionsFeature
     */
    public void setCompetitionsFeature(boolean CompetitionsFeature) {
        this.CompetitionsFeature = CompetitionsFeature;
    }

    /**
     * @return The YourAccountFeature
     */
    public boolean isYourAccountFeature() {
        return YourAccountFeature;
    }

    /**
     * @param courses The YourAccountFeature
     */
    public void setYourAccountFeature(boolean yourAccountFeature) {
        this.YourAccountFeature = yourAccountFeature;
    }

    /**
     * @return The ClubNewsFeature
     */
    public boolean isClubNewsFeature() {
        return ClubNewsFeature;
    }

    /**
     * @param ClubNewsFeature The ClubNewsFeature
     */
    public void setClubNewsFeature(boolean clubNewsFeature) {
        this.ClubNewsFeature = clubNewsFeature;
    }

    /**
     * @return The MembersFeature
     */
    public boolean isMembersFeature() {
        return MembersFeature;
    }

    /**
     * @param MembersFeature The MembersFeature
     */
    public void setMembersFeature(boolean membersFeature) {
        this.MembersFeature = membersFeature;
    }
}
