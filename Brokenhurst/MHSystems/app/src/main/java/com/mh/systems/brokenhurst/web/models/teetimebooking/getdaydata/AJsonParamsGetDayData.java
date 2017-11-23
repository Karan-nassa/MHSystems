
package com.mh.systems.brokenhurst.web.models.teetimebooking.getdaydata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsGetDayData {

    @SerializedName("Version")
    @Expose
    private int version;
    @SerializedName("MemberId")
    @Expose
    private String memberId;
    @SerializedName("datefrom")
    @Expose
    private String datefrom;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsGetDayData() {
    }

    /**
     * 
     * @param datefrom
     * @param memberId
     * @param version
     */
    public AJsonParamsGetDayData(int version, String memberId, String datefrom) {
        super();
        this.version = version;
        this.memberId = memberId;
        this.datefrom = datefrom;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

}
