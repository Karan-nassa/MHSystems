
package com.mh.systems.sunningdale.web.models.competitionsentry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsEligiblePlayers {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("EventId")
    @Expose
    private int EventId;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;

    /**
     * @return The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return The callid
     */
    public String getCallid() {
        return callid;
    }

    /**
     * @param callid The callid
     */
    public void setCallid(String callid) {
        this.callid = callid;
    }

    /**
     * @return The EventId
     */
    public int getEventId() {
        return EventId;
    }

    /**
     * @param EventId The EventId
     */
    public void setEventId(int EventId) {
        this.EventId = EventId;
    }

    /**
     * @return The MemberId
     */
    public String getMemberId() {
        return MemberId;
    }

    /**
     * @param MemberId The MemberId
     */
    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

}