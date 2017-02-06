
package com.mh.systems.corrstown.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsAddMember {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("memberid")
    @Expose
    private String memberid;
    @SerializedName("friendid")
    @Expose
    private Integer friendid;

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
    public String getMemberid() {
        return memberid;
    }

    /**
     *
     * @param memberid
     *     The memberid
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    /**
     *
     * @return
     *     The friendid
     */
    public Integer getFriendid() {
        return friendid;
    }

    /**
     *
     * @param friendid
     *     The friendid
     */
    public void setFriendid(Integer friendid) {
        this.friendid = friendid;
    }

}
