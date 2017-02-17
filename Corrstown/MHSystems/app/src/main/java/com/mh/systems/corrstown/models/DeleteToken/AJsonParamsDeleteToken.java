
package com.mh.systems.corrstown.models.DeleteToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsDeleteToken {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("DeviceId")
    @Expose
    private String DeviceId;

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
     * @return The DeviceId
     */
    public String getDeviceId() {
        return DeviceId;
    }

    /**
     * @param DeviceId The DeviceId
     */
    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }

}
