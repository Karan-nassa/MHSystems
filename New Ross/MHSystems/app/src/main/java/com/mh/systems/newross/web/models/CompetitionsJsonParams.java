
package com.mh.systems.newross.web.models;


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
    private String MemberId;
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
    private boolean AscendingDateOrder;
    @SerializedName("MyEventsOnly")
    @Expose
    private boolean MyEventsOnly;
    @SerializedName("datefrom")
    @Expose
    private String datefrom;
    @SerializedName("dateto")
    @Expose
    private String dateto;
    @SerializedName("pageNo")
    @Expose
    private String pageNo;
    @SerializedName("pageSize")
    @Expose
    private String pageSize;
    @SerializedName("GenderFilter")
    @Expose
    private int GenderFilter;

    /**
     * @return The IncludeFutureEvents
     */
    public boolean isIncludeFutureEvents() {
        return IncludeFutureEvents;
    }

    /**
     * @return The IncludeCurrentEvents
     */
    public boolean isIncludeCurrentEvents() {
        return IncludeCurrentEvents;
    }

    /**
     * @return The IncludeCompletedEvents
     */
    public boolean isIncludeCompletedEvents() {
        return IncludeCompletedEvents;
    }

    /**
     * @return The MyEventsOnly
     */
    public boolean isMyEventsOnly() {
        return MyEventsOnly;
    }

    /**
     * @return The datefrom
     */
    public String getDatefrom() {
        return datefrom;
    }

    /**
     * @param datefrom The datefrom
     */
    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    /**
     * @return The dateto
     */
    public String getDateto() {
        return dateto;
    }

    /**
     * @param dateto The dateto
     */
    public void setDateto(String dateto) {
        this.dateto = dateto;
    }

    /**
     * @return The pageNo
     */
    public String getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo The pageNo
     */
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return The version
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize The pageSize
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

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

    /**
     * @return The IncludeFutureEvents
     */
    public boolean getIncludeFutureEvents() {
        return IncludeFutureEvents;
    }

    /**
     * @param IncludeFutureEvents The IncludeFutureEvents
     */
    public void setIncludeFutureEvents(boolean IncludeFutureEvents) {
        this.IncludeFutureEvents = IncludeFutureEvents;
    }

    /**
     * @return The IncludeCurrentEvents
     */
    public boolean getIncludeCurrentEvents() {
        return IncludeCurrentEvents;
    }

    /**
     * @param IncludeCurrentEvents The IncludeCurrentEvents
     */
    public void setIncludeCurrentEvents(boolean IncludeCurrentEvents) {
        this.IncludeCurrentEvents = IncludeCurrentEvents;
    }

    /**
     * @return The IncludeCompletedEvents
     */
    public boolean getIncludeCompletedEvents() {
        return IncludeCompletedEvents;
    }

    /**
     * @param IncludeCompletedEvents The IncludeCompletedEvents
     */
    public void setIncludeCompletedEvents(boolean IncludeCompletedEvents) {
        this.IncludeCompletedEvents = IncludeCompletedEvents;
    }

    /**
     * @return The AscendingDateOrder
     */
    public boolean getAscendingDateOrder() {
        return AscendingDateOrder;
    }

    /**
     * @param AscendingDateOrder The AscendingDateOrder
     */
    public void setAscendingDateOrder(boolean AscendingDateOrder) {
        this.AscendingDateOrder = AscendingDateOrder;
    }

    /**
     * @return The MyEventsOnly
     */
    public boolean getMyEventsOnly() {
        return MyEventsOnly;
    }

    /**
     * @param MyEventsOnly The MyEventsOnly
     */
    public void setMyEventsOnly(boolean MyEventsOnly) {
        this.MyEventsOnly = MyEventsOnly;
    }

    public int getGenderFilter() {
        return GenderFilter;
    }

    public void setGenderFilter(int genderFilter) {
        GenderFilter = genderFilter;
    }
}
