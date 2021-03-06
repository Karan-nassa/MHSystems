
package com.mh.systems.teesside.web.models.compfiltersettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompFilterSettings {

    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsCompFilterSettings aJsonParamsCompFilterSettings;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CompFilterSettings() {
    }

    /**
     * 
     * @param aJsonParamsCompFilterSettings
     * @param aModuleId
     * @param aClientId
     * @param aCommand
     */
    public CompFilterSettings(String aModuleId, String aClientId, String aCommand, AJsonParamsCompFilterSettings aJsonParamsCompFilterSettings) {
        super();
        this.aModuleId = aModuleId;
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsCompFilterSettings = aJsonParamsCompFilterSettings;
    }

    public String getAModuleId() {
        return aModuleId;
    }

    public void setAModuleId(String aModuleId) {
        this.aModuleId = aModuleId;
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

    public AJsonParamsCompFilterSettings getAJsonParams() {
        return aJsonParamsCompFilterSettings;
    }

    public void setAJsonParams(AJsonParamsCompFilterSettings aJsonParamsCompFilterSettings) {
        this.aJsonParamsCompFilterSettings = aJsonParamsCompFilterSettings;
    }

}
