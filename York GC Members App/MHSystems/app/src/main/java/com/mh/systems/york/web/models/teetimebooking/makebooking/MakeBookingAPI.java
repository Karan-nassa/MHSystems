
package com.mh.systems.york.web.models.teetimebooking.makebooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeBookingAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsMakeBookingAPI aJsonParamsMakeBookingAPI;
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
    public MakeBookingAPI() {
    }

    /**
     * 
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public MakeBookingAPI(String aClientId, String aCommand, AJsonParamsMakeBookingAPI aJsonParamsMakeBookingAPI, String aModuleId, String aUserClass) {
        super();
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsMakeBookingAPI = aJsonParamsMakeBookingAPI;
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

    public AJsonParamsMakeBookingAPI getAJsonParams() {
        return aJsonParamsMakeBookingAPI;
    }

    public void setAJsonParams(AJsonParamsMakeBookingAPI aJsonParams) {
        this.aJsonParamsMakeBookingAPI = aJsonParamsMakeBookingAPI;
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
