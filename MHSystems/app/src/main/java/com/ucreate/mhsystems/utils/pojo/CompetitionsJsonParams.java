
package com.ucreate.mhsystems.utils.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionsJsonParams {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("IncludeFutureEvents")
    @Expose
    private boolean IncludeFutureEvents;
    @SerializedName("IncludeCurrentEvents")
    @Expose
    private boolean IncludeCurrentEvents;
    @SerializedName("IncludeCompletedEvents")
    @Expose
    private boolean IncludeCompletedEvents;
    @SerializedName("AscendingDateOrder")
    @Expose
    private String AscendingDateOrder;
    @SerializedName("MyEventsOnly")
    @Expose
    private boolean MyEventsOnly;

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
     *     The IncludeFutureEvents
     */
    public boolean getIncludeFutureEvents() {
        return IncludeFutureEvents;
    }

    /**
     * 
     * @param IncludeFutureEvents
     *     The IncludeFutureEvents
     */
    public void setIncludeFutureEvents(boolean IncludeFutureEvents) {
        this.IncludeFutureEvents = IncludeFutureEvents;
    }

    /**
     * 
     * @return
     *     The IncludeCurrentEvents
     */
    public boolean getIncludeCurrentEvents() {
        return IncludeCurrentEvents;
    }

    /**
     * 
     * @param IncludeCurrentEvents
     *     The IncludeCurrentEvents
     */
    public void setIncludeCurrentEvents(boolean IncludeCurrentEvents) {
        this.IncludeCurrentEvents = IncludeCurrentEvents;
    }

    /**
     * 
     * @return
     *     The IncludeCompletedEvents
     */
    public boolean getIncludeCompletedEvents() {
        return IncludeCompletedEvents;
    }

    /**
     * 
     * @param IncludeCompletedEvents
     *     The IncludeCompletedEvents
     */
    public void setIncludeCompletedEvents(boolean IncludeCompletedEvents) {
        this.IncludeCompletedEvents = IncludeCompletedEvents;
    }

    /**
     * 
     * @return
     *     The AscendingDateOrder
     */
    public String getAscendingDateOrder() {
        return AscendingDateOrder;
    }

    /**
     * 
     * @param AscendingDateOrder
     *     The AscendingDateOrder
     */
    public void setAscendingDateOrder(String AscendingDateOrder) {
        this.AscendingDateOrder = AscendingDateOrder;
    }

    /**
     * 
     * @return
     *     The MyEventsOnly
     */
    public boolean getMyEventsOnly() {
        return MyEventsOnly;
    }

    /**
     * 
     * @param MyEventsOnly
     *     The MyEventsOnly
     */
    public void setMyEventsOnly(boolean MyEventsOnly) {
        this.MyEventsOnly = MyEventsOnly;
    }

}
