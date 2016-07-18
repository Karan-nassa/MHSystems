
package com.mh.systems.york.models;

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


}
