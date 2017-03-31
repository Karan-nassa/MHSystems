
package com.mh.systems.guildford.web.models.featuresflag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureFlagsAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsFeaturesFlag aJsonParamsFeaturesFlag;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    public FeatureFlagsAPI(String aClientId, String aCommand, AJsonParamsFeaturesFlag aJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsFeaturesFlag = aJsonParams;
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

    public AJsonParamsFeaturesFlag getAJsonParams() {
        return aJsonParamsFeaturesFlag;
    }

    public void setAJsonParams(AJsonParamsFeaturesFlag aJsonParamsFeaturesFlag) {
        this.aJsonParamsFeaturesFlag = aJsonParamsFeaturesFlag;
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
