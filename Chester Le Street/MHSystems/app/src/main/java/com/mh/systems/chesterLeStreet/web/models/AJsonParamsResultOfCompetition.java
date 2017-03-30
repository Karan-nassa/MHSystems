
package com.mh.systems.chesterLeStreet.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsResultOfCompetition {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("EventId")
    @Expose
    private String eventId;
    @SerializedName("MemberId")
    @Expose
    private String memberId;

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
     *     The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * 
     * @param eventId
     *     The EventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * 
     * @return
     *     The memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 
     * @param memberId
     *     The MemberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}
