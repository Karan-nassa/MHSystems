
package com.mh.systems.dunstabledowns.web.models.teetimebooking.getmonthdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMonthDataAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsGetMonthData aJsonParamsGetMonthData;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetMonthDataAPI() {
    }

    /**
     * 
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public GetMonthDataAPI(String aClientId, String aCommand, AJsonParamsGetMonthData aJsonParamsGetMonthData, String aModuleId, String aUserClass) {
        super();
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsGetMonthData = aJsonParamsGetMonthData;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    public String getAClientId() {
        return aClientId;
    }

    public void setAClientId(String aClientId) {
        this.aClientId = aClientId;
    }

    public String getACommand() {
        return aCommand;
    }

    public void setACommand(String aCommand) {
        this.aCommand = aCommand;
    }

    public AJsonParamsGetMonthData getAJsonParams() {
        return aJsonParamsGetMonthData;
    }

    public void setAJsonParams(AJsonParamsGetMonthData aJsonParamsGetMonthData) {
        this.aJsonParamsGetMonthData = aJsonParamsGetMonthData;
    }

    public String getAModuleId() {
        return aModuleId;
    }

    public void setAModuleId(String aModuleId) {
        this.aModuleId = aModuleId;
    }

    public String getAUserClass() {
        return aUserClass;
    }

    public void setAUserClass(String aUserClass) {
        this.aUserClass = aUserClass;
    }

}
