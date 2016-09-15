
package com.mh.systems.demoapp.models.competitionsEntry;


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

}
