
package com.mh.systems.littlehampton.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FinanceAJsonParams {

    @SerializedName("DateRange")
    @Expose
    private Integer DateRange;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;


    /**
     * @return The DateRange
     */
    public Integer getDateRange() {
        return DateRange;
    }

    /**
     * 
     * @param DateRange
     *     The DateRange
     */
    public void setDateRange(Integer DateRange) {
        this.DateRange = DateRange;
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

}
