
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewAJsonCompEntry {

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

    /**
     * No args constructor for use in serialization
     * 
     */
    public NewAJsonCompEntry() {
    }

    /**
     * 
     * @param eventId
     * @param callid
     * @param memberId
     * @param version
     */
    public NewAJsonCompEntry(int version, String callid, String EventId, String MemberId) {
        super();
        this.version = version;
        this.callid = callid;
        this.EventId = EventId;
        this.MemberId = MemberId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String EventId) {
        this.EventId = EventId;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

}
