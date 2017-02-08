
package com.mh.systems.sunningdale.models.registerToken;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsRegisterToken {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private String memberId;
    @SerializedName("DeviceState")
    @Expose
    private int deviceState;
    @SerializedName("DeviceType")
    @Expose
    private int deviceType;
    @SerializedName("DeviceId")
    @Expose
    private String deviceId;

    /**
     * @return The version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(int version) {
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
     * @return The memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId The MemberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return The deviceState
     */
    public int getDeviceState() {
        return deviceState;
    }

    /**
     * @param deviceState The DeviceState
     */
    public void setDeviceState(int deviceState) {
        this.deviceState = deviceState;
    }

    /**
     * @return The deviceType
     */
    public int getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType The DeviceType
     */
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return The deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId The DeviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
