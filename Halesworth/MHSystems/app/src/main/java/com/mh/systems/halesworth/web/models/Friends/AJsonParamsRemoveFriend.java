
package com.mh.systems.halesworth.web.models.friends;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsRemoveFriend {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("friendid")
    @Expose
    private Integer friendid;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsRemoveFriend() {
    }

    /**
     * 
     * @param friendid
     * @param callid
     * @param MemberId
     * @param version
     */
    public AJsonParamsRemoveFriend(Integer version, String callid, Integer MemberId, Integer friendid) {
        this.version = version;
        this.callid = callid;
        this.MemberId = MemberId;
        this.friendid = friendid;
    }

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
     *     The MemberId
     */
    public Integer getMemberId() {
        return MemberId;
    }

    /**
     * 
     * @param MemberId
     *     The MemberId
     */
    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
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
