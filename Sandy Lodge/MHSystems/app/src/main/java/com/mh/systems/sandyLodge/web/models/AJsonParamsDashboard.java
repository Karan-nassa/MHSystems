
package com.mh.systems.sandylodge.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsDashboard {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("Password")
    @Expose
    private String Password;

    /**
     * 
     * @return
     *     The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The callid
     */
    public String getCallid() {
        return callid;
    }

    /**
     * 
     * @param callid
     *     The callid
     */
    public void setCallid(String callid) {
        this.callid = callid;
    }

    /**
     * 
     * @return
     *     The UserID
     */
    public String getUserID() {
        return UserID;
    }

    /**
     * 
     * @param UserID
     *     The UserID
     */
    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    /**
     * 
     * @return
     *     The Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * 
     * @param Password
     *     The Password
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

}
