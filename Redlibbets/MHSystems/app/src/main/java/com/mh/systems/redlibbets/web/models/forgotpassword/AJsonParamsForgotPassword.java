
package com.mh.systems.redlibbets.web.models.forgotpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsForgotPassword {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("UserId")
    @Expose
    private String UserId;

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
     *     The UserId
     */
    public String getUserId() {
        return UserId;
    }

    /**
     * 
     * @param UserId
     *     The UserId
     */
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

}
