
package com.mh.systems.hartsbourne.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


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
    @SerializedName("Courses")
    @Expose
    private List<CoursesData> Courses = new ArrayList<CoursesData>();
    @SerializedName("FirstTimeLogin")
    @Expose
    private boolean FirstTimeLogin;

    /**
     * @return The courses
     */
    public List<CoursesData> getCourses() {
        return Courses;
    }

    /**
     * @param courses The courses
     */
    public void setCourses(List<CoursesData> courses) {
        Courses = courses;
    }

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
}
