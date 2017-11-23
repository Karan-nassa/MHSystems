
package com.mh.systems.littlehampton.web.models.teetimebooking.getbookingdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsGetBookingData {

    @SerializedName("Version")
    @Expose
    private int version;
    @SerializedName("MemberId")
    @Expose
    private String memberId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsGetBookingData() {
    }

    /**
     * 
     * @param memberId
     * @param version
     */
    public AJsonParamsGetBookingData(int version, String memberId) {
        super();
        this.version = version;
        this.memberId = memberId;
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

}
