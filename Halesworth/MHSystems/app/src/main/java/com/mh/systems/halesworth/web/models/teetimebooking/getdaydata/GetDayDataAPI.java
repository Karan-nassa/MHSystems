
package com.mh.systems.halesworth.web.models.teetimebooking.getdaydata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDayDataAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsGetDayData aJsonParamsGetDayData;
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
    public GetDayDataAPI() {
    }

    /**
     * 
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public GetDayDataAPI(String aClientId, String aCommand, AJsonParamsGetDayData aJsonParamsGetDayData, String aModuleId, String aUserClass) {
        super();
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsGetDayData = aJsonParamsGetDayData;
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

    public AJsonParamsGetDayData getAJsonParams() {
        return aJsonParamsGetDayData;
    }

    public void setAJsonParams(AJsonParamsGetDayData aJsonParamsGetDayData) {
        this.aJsonParamsGetDayData = aJsonParamsGetDayData;
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
