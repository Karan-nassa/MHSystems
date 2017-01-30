
package com.mh.systems.demoapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginAPI {

    @SerializedName("aClientId")
    @Expose
    private Integer aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsLogin aJsonParams;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    /**
     * No args constructor for use in serialization
     */
    public LoginAPI() {
    }

    /**
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public LoginAPI(Integer aClientId, String aCommand, AJsonParamsLogin aJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    /**
     * @return The aClientId
     */
    public Integer getAClientId() {
        return aClientId;
    }

    /**
     * @param aClientId The aClientId
     */
    public void setAClientId(Integer aClientId) {
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
     * @return The aJsonParamsMembersDatail
     */
    public AJsonParamsLogin getAJsonParams() {
        return aJsonParams;
    }

    /**
     * @param aJsonParams The aJsonParams
     */
    public void setAJsonParams(AJsonParamsLogin aJsonParams) {
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
