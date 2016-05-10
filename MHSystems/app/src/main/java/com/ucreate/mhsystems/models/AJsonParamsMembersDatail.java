
package com.ucreate.mhsystems.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsMembersDatail {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("memberid")
    @Expose
    private Integer memberid;
    @SerializedName("LoginMemberId")
    @Expose
    private Integer LoginMemberId;


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
     *     The memberid
     */
    public Integer getMemberid() {
        return memberid;
    }

    /**
     * 
     * @param memberid
     *     The memberid
     */
    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    /**
     *
     * @return
     * The LoginMemberId
     */
    public Integer getLoginMemberId() {
        return LoginMemberId;
    }

    /**
     *
     * @param LoginMemberId
     * The LoginMemberId
     */
    public void setLoginMemberId(Integer LoginMemberId) {
        this.LoginMemberId = LoginMemberId;
    }

}
