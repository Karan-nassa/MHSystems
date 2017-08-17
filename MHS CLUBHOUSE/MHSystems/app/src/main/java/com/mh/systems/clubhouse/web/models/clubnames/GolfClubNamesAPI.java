
package com.mh.systems.clubhouse.web.models.clubnames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GolfClubNamesAPI {

    @SerializedName("aClientId")
    @Expose
    private int aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsGolfClubNames aJsonParams;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    public GolfClubNamesAPI(int aClientId, String aCommand, AJsonParamsGolfClubNames aJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    public int getAClientId() {
        return aClientId;
    }

    public void setAClientId(int aClientId) {
        this.aClientId = aClientId;
    }

    public String getACommand() {
        return aCommand;
    }

    public void setACommand(String aCommand) {
        this.aCommand = aCommand;
    }

    public AJsonParamsGolfClubNames getAJsonParams() {
        return aJsonParams;
    }

    public void setAJsonParams(AJsonParamsGolfClubNames aJsonParams) {
        this.aJsonParams = aJsonParams;
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
