
package com.mh.systems.teesside.web.models.pursebalance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsPurseApi {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private String memberId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsPurseApi() {
    }

    /**
     * 
     * @param callid
     * @param memberId
     * @param version
     */
    public AJsonParamsPurseApi(Integer version, String callid, String memberId) {
        super();
        this.version = version;
        this.callid = callid;
        this.memberId = memberId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}
