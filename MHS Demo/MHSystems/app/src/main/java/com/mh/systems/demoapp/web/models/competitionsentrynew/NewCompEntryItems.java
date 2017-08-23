
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCompEntryItems {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private NewAJsonCompEntry aJsonParams;
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
    public NewCompEntryItems() {
    }

    /**
     * 
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public NewCompEntryItems(String aClientId, String aCommand, NewAJsonCompEntry aJsonParams, String aModuleId, String aUserClass) {
        super();
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
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

    public NewAJsonCompEntry getAJsonParams() {
        return aJsonParams;
    }

    public void setAJsonParams(NewAJsonCompEntry aJsonParams) {
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
