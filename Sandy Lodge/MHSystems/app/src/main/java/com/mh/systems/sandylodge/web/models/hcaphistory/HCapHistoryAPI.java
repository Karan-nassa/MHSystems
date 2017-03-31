
package com.mh.systems.sandylodge.web.models.hcaphistory;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HCapHistoryAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsHcapHistory aJsonParams;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    /**
     * No args constructor for use in serialization
     */
    public HCapHistoryAPI() {
    }

    /**
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public HCapHistoryAPI(String aClientId, String aCommand, AJsonParamsHcapHistory aJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    /**
     * @return The aClientId
     */
    public String getAClientId() {
        return aClientId;
    }

    /**
     * @param aClientId The aClientId
     */
    public void setAClientId(String aClientId) {
        this.aClientId = aClientId;
    }

    /**
     * @return The aCommand
     */
    public String getACommand() {
        return aCommand;
    }

    /**
     * @param aCommand The aCommand
     */
    public void setACommand(String aCommand) {
        this.aCommand = aCommand;
    }

    /**
     * @return The aJsonParams
     */
    public AJsonParamsHcapHistory getAJsonParams() {
        return aJsonParams;
    }

    /**
     * @param aJsonParams The aJsonParams
     */
    public void setAJsonParams(AJsonParamsHcapHistory aJsonParams) {
        this.aJsonParams = aJsonParams;
    }

    /**
     * @return The aModuleId
     */
    public String getAModuleId() {
        return aModuleId;
    }

    /**
     * @param aModuleId The aModuleId
     */
    public void setAModuleId(String aModuleId) {
        this.aModuleId = aModuleId;
    }

    /**
     * @return The aUserClass
     */
    public String getAUserClass() {
        return aUserClass;
    }

    /**
     * @param aUserClass The aUserClass
     */
    public void setAUserClass(String aUserClass) {
        this.aUserClass = aUserClass;
    }

}