
package com.ucreate.mhsystems.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyAccountJsonParams {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;


    /**
     * @return The version
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

}
