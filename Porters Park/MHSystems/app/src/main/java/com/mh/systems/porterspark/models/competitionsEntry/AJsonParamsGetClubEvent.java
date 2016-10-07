
package com.mh.systems.porterspark.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsGetClubEvent {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("EventId")
    @Expose
    private String EventId;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;
    @SerializedName("LoginMemberId")
    @Expose
    private String LoginMemberId;

    /**
     * 
     * @return
     *     The version
     */
    public int getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(int version) {
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
     *     The EventId
     */
    public String getEventId() {
        return EventId;
    }

    /**
     * 
     * @param EventId
     *     The EventId
     */
    public void setEventId(String EventId) {
        this.EventId = EventId;
    }

    /**
     *
     * @return
     *     The MemberId
     */
    public String getMemberId() {
        return MemberId;
    }

    /**
     *
     * @param MemberId
     *     The MemberId
     */
    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    /**
     *
     * @param LoginMemberId
     *     The LoginMemberId
     */
    public String getLoginMemberId() {
        return LoginMemberId;
    }

    /**
     *
     * @return
     *     The LoginMemberId
     */
    public void setLoginMemberId(String LoginMemberId) {
        this.LoginMemberId = LoginMemberId;
    }
}
