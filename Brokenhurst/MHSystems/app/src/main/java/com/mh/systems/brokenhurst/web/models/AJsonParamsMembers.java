
package com.mh.systems.brokenhurst.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsMembers {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("Memberid")
    @Expose
    private String Memberid;
    @SerializedName("GenderFilter")
    @Expose
    private int GenderFilter;

    /**
     * @return The GenderFilter
     */
    public int getGenderFilter() {
        return GenderFilter;
    }

    /**
     *
     * @param genderFilter
     *     The GenderFilter
     */
    public void setGenderFilter(int genderFilter) {
        GenderFilter = genderFilter;
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
     * @return The Memberid
     */
    public String getMemberid() {
        return Memberid;
    }

    /**
     * @param Memberid The Memberid
     */
    public void setMemberid(String Memberid) {
        this.Memberid = Memberid;
    }

}
